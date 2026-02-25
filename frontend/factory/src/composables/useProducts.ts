import { ref, computed } from 'vue'
import { productService } from '@/services/productService'
import type { ProductResponse, ProductRequest, ProductUpdate } from '@/types'


export function useProducts() {
  const products = ref<ProductResponse[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)
  const selectedProduct = ref<ProductResponse | null>(null)

  const hasProducts = computed(() => products.value.length > 0)
  const productCount = computed(() => products.value.length)

  const loadProducts = async () => {
    loading.value = true
    error.value = null
    try {
      products.value = await productService.getAllProducts()
    } catch (err) {
      error.value = 'Erro ao carregar produtos'
      console.error('loadProducts error:', err)
    } finally {
      loading.value = false
    }
  }

  const loadProductById = async (code: number) => {
    loading.value = true
    error.value = null
    try {
      selectedProduct.value = await productService.getProductByCode(code)
    } catch (err) {
      error.value = 'Erro ao carregar produto'
      console.error('loadProductById error:', err)
    } finally {
      loading.value = false
    }
  }

  const createProduct = async (product: ProductRequest) => {
    loading.value = true
    try {
      const newProduct = await productService.createProduct(product)
      products.value.push(newProduct)
      return newProduct
    } catch (err) {
      console.error('createProduct error:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateProduct = async (code: number, product: ProductUpdate) => {
    loading.value = true
    try {
      const updated = await productService.updateProduct(code, product)
      const index = products.value.findIndex(p => p.code === code)
      if (index !== -1) {
        products.value[index] = updated
      }
      if (selectedProduct.value?.code === code) {
        selectedProduct.value = updated
      }
      return updated
    } catch (err) {
      console.error('updateProduct error:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteProduct = async (code: number) => {
    loading.value = true
    try {
      await productService.deleteProduct(code)
      products.value = products.value.filter(p => p.code !== code)
      if (selectedProduct.value?.code === code) {
        selectedProduct.value = null
      }
    } catch (err) {
      console.error('deleteProduct error:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const clearError = () => {
    error.value = null
  }

  const clearSelection = () => {
    selectedProduct.value = null
  }

  return {
    products,
    loading,
    error,
    selectedProduct,

    hasProducts,
    productCount,

    loadProducts,
    loadProductById,
    createProduct,
    updateProduct,
    deleteProduct,
    clearError,
    clearSelection
  }
}
