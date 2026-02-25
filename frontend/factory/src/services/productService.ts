import api from './api'
import type { ProductResponse, ProductRequest, ProductUpdate } from '@/types'


export const productService = {

  async getAllProducts(): Promise<ProductResponse[]> {
    const response = await api.get<ProductResponse[]>('/products')
    return response.data
  },

  async getProductByCode(code: number): Promise<ProductResponse> {
    const response = await api.get<ProductResponse>(`/products/${code}`)
    return response.data
  },

  async createProduct(product: ProductRequest): Promise<ProductResponse> {
    const response = await api.post<ProductResponse>('/products', product)
    return response.data
  },

  async updateProduct(code: number, product: ProductUpdate): Promise<ProductResponse> {
    const response = await api.put<ProductResponse>(`/products/${code}`, product)
    return response.data
  },

  async deleteProduct(code: number): Promise<void> {
    await api.delete(`/products/${code}`)
  }
}
