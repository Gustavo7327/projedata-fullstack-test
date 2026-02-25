<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { productService } from '@/services/productService'
import type { ProductResponse, ProductUpdate } from '@/types'
import { useI18n } from 'vue-i18n'
import { useAlert } from '@/composables/useAlert'
import { getErrorMessageFromHttpError } from '@/utils/handleHttpError'

const {t} = useI18n()
const router = useRouter()
const route = useRoute()

const product = ref<ProductResponse | null>(null)
const loading = ref(false)
const error = ref<string | null>(null)
const isEditing = ref(false)

const formData = ref<ProductUpdate>({
  name: '',
  value: 0
})

const code = parseInt(route.params.code as string)

const loadProduct = async () => {
  loading.value = true
  error.value = null
  try {
    product.value = await productService.getProductByCode(code)
    if (product.value) {
      formData.value = {
        name: product.value.name,
        value: product.value.value
      }
    }
  } catch (err) {
    error.value = 'Erro ao carregar produto'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const { showAlert } = useAlert()

const handleUpdate = async () => {
  loading.value = true
  error.value = null
  try {
    const updated = await productService.updateProduct(code, formData.value)
    product.value = updated
    isEditing.value = false
    showAlert(t('products.productUpdated'), '', 'success')
  } catch (err) {
    const message = getErrorMessageFromHttpError(err, (k: string) => t(k))
    showAlert(message, t('common.error'), 'error')
    console.error(err)
  } finally {
    loading.value = false
  }
}

const handleDelete = async () => {
  if (confirm('Tem certeza que deseja deletar este produto?')) {
    loading.value = true
    error.value = null
    try {
      await productService.deleteProduct(code)
      showAlert(t('products.productDeleted'), '', 'success')
      router.push({ name: 'products' })
    } catch (err) {
      const message = getErrorMessageFromHttpError(err, (k: string) => t(k))
      showAlert(message, t('common.error'), 'error')
      console.error(err)
    } finally {
      loading.value = false
    }
  }
}

const goBack = () => {
  router.push({ name: 'products' })
}

onMounted(() => {
  loadProduct()
})
</script>

<template>
  <div class="product-detail-page">
    <button class="btn-back" @click="goBack">← {{ t('common.back') }}</button>

    <div v-if="loading" class="loading">
      <p>Carregando...</p>
    </div>

    <div v-if="error" class="error">
      <p>{{ error }}</p>
      <button class="btn btn-secondary" @click="loadProduct">Tentar Novamente</button>
    </div>

    <div v-if="!loading && product" class="detail-container">
      <div class="detail-header">
        <div>
          <h1>{{ product.name }}</h1>
          <p class="code">{{t('common.code')}}: {{ product.code }}</p>
        </div>
        <div class="header-actions">
          <button
            v-if="!isEditing"
            class="btn btn-primary"
            @click="isEditing = true"
          >
            {{t('common.edit')}}
          </button>
          <button
            class="btn btn-danger"
            @click="handleDelete"
          >
            {{t('common.delete')}}
          </button>
        </div>
      </div>

      <div v-if="!isEditing" class="info-section">
        <div class="info-group">
          <label>{{ t('common.value') }}</label>
          <p class="value">R$ {{ product.value.toFixed(2) }}</p>
        </div>

        <div v-if="product.compositions.length > 0" class="compositions-section">
          <h3>{{t('products.composition')}}</h3>
          <table class="compositions-table">
            <thead>
              <tr>
                <th>{{ t('products.rawMaterial') }}</th>
                <th>{{ t('products.quantity') }}</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(comp, idx) in product.compositions" :key="idx">
                <td>{{ comp.rawMaterial.name }}</td>
                <td>{{ comp.quantityRequired }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <form v-if="isEditing" @submit.prevent="handleUpdate" class="edit-form">
        <div class="form-group">
          <label for="name">{{ t('common.name') }}:</label>
          <input
            id="name"
            v-model="formData.name"
            type="text"
            required
          />
        </div>

        <div class="form-group">
          <label for="value">{{ t('common.value') }}:</label>
          <input
            id="value"
            v-model.number="formData.value"
            type="number"
            step="0.1"
            min="0"
            required
          />
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-success">{{ t('common.save') }}</button>
          <button
            type="button"
            class="btn btn-secondary"
            @click="isEditing = false"
          >
            {{ t('common.cancel') }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.product-detail-page {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.btn-back {
  background: none;
  border: none;
  color: #1976d2;
  font-size: 16px;
  cursor: pointer;
  margin-bottom: 20px;
  transition: all 0.3s ease;
}

.btn-back:hover {
  transform: translateX(-4px);
}

.loading,
.error {
  background-color: #f5f5f5;
  border-radius: 8px;
  padding: 40px;
  text-align: center;
  color: #666;
}

.error {
  background-color: #ffebee;
  color: #c62828;
}

.detail-container {
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.detail-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 30px;
  gap: 20px;
  flex-wrap: wrap;
  border-bottom: 2px solid #f0f0f0;
  padding-bottom: 20px;
}

.detail-header h1 {
  margin: 0;
  color: #333;
  font-size: 2rem;
}

.code {
  margin: 8px 0 0 0;
  color: #777;
  font-size: 0.95rem;
}

.header-actions {
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn:hover {
  opacity: 0.9;
  transform: translateY(-2px);
}

.btn-primary {
  background-color: #1976d2;
  color: white;
}

.btn-secondary {
  background-color: #757575;
  color: white;
}

.btn-success {
  background-color: #2196f3;
  color: white;
}

.btn-danger {
  background-color: #d32f2f;
  color: white;
}

.info-section {
  margin-top: 30px;
}

.info-group {
  margin-bottom: 20px;
}

.info-group label {
  display: block;
  font-weight: 600;
  color: #555;
  margin-bottom: 8px;
}

.value {
  margin: 0;
  font-size: 1.5rem;
  color: #388e3c;
  font-weight: 600;
}

.compositions-section {
  margin-top: 30px;
  padding-top: 30px;
  border-top: 2px solid #f0f0f0;
}

.compositions-section h3 {
  margin: 0 0 20px 0;
  color: #333;
}

.compositions-table {
  width: 100%;
  border-collapse: collapse;
}

.compositions-table thead {
  background-color: #f5f5f5;
  border-bottom: 2px solid #e0e0e0;
}

.compositions-table th {
  padding: 12px;
  text-align: left;
  font-weight: 600;
  color: #333;
}

.compositions-table td {
  padding: 12px;
  border-bottom: 1px solid #e0e0e0;
}

.compositions-table tbody tr:last-child td {
  border-bottom: none;
}

.edit-form {
  margin-top: 30px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-weight: 600;
  color: #555;
  margin-bottom: 8px;
}

.form-group input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  box-sizing: border-box;
}

.form-group input:focus {
  outline: none;
  border-color: #1976d2;
  box-shadow: 0 0 0 3px rgba(25, 118, 210, 0.1);
}

.form-actions {
  display: flex;
  gap: 10px;
  margin-top: 20px;
}

.form-actions button {
  flex: 1;
}

@media (max-width: 768px) {
  .detail-container {
    padding: 20px;
  }

  .detail-header {
    flex-direction: column;
  }

  .header-actions {
    width: 100%;
  }

  .header-actions button {
    flex: 1;
  }

  .form-actions {
    flex-direction: column;
  }
}
</style>
