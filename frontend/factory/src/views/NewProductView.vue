<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useProducts } from '@/composables/useProducts'
import { useRawMaterials } from '@/composables/useRawMaterials'
import { useAlert } from '@/composables/useAlert'
import { getErrorMessageFromHttpError } from '@/utils/handleHttpError'
import type { ProductRequest, ProductCompositionRequest } from '@/types'

const router = useRouter()
const { t } = useI18n()
const { createProduct, loading: creatingProduct, clearError } = useProducts()
const { showAlert } = useAlert()
const { rawMaterials, loading: loadingMaterials, loadMaterials } = useRawMaterials()

const formData = ref<ProductRequest>({
  name: '',
  value: 0,
  compositions: []
})

const selectedRawMaterialCode = ref<number | null>(null)
const quantityRequired = ref<number>(0)
const compositionError = ref<string | null>(null)

const availableRawMaterials = computed(() => {
  return rawMaterials.value.filter(material => 
    !formData.value.compositions.some(comp => comp.rawMaterialCode === material.code)
  )
})

const getRawMaterialName = (code: number): string => {
  const material = rawMaterials.value.find(m => m.code === code)
  return material?.name || `#${code}`
}

const addComposition = () => {
  compositionError.value = null

  if (selectedRawMaterialCode.value === null) {
    compositionError.value = t('forms.validation.required')
    return
  }

  if (quantityRequired.value <= 0) {
    compositionError.value = t('forms.validation.positiveNumber')
    return
  }

  const composition: ProductCompositionRequest = {
    rawMaterialCode: selectedRawMaterialCode.value,
    quantityRequired: quantityRequired.value
  }

  formData.value.compositions.push(composition)
  selectedRawMaterialCode.value = null
  quantityRequired.value = 0
}

const removeComposition = (index: number) => {
  formData.value.compositions.splice(index, 1)
}

const handleSubmit = async () => {
  compositionError.value = null

  if (!formData.value.name.trim()) {
    showAlert(t('forms.validation.required'), t('common.warning'), 'warning')
    return
  }

  if (formData.value.value <= 0) {
    showAlert(t('forms.validation.positiveNumber'), t('common.warning'), 'warning')
    return
  }

  if (formData.value.compositions.length === 0) {
    showAlert(t('products.selectCompositions'), t('common.warning'), 'warning')
    return
  }

  try {
    await createProduct(formData.value)
    showAlert(t('products.productCreated'), '', 'success')
    router.push({ name: 'products' })
  } catch (err) {
    const message = getErrorMessageFromHttpError(err, (k: string) => t(k))
    showAlert(message, t('common.error'), 'error')
    clearError()
  }
}

const goBack = () => {
  router.push({ name: 'products' })
}

const formatCurrency = (value: number): string => {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  }).format(value)
}

onMounted(loadMaterials)
</script>

<template>
  <div class="new-product-page">
    <button class="btn-back" @click="goBack">← {{ t('common.back') }}</button>

    <div class="form-container">
      <div class="form-header">
        <h1>{{ t('products.createProduct') }}</h1>
        <p class="subtitle">{{ t('products.description') }}</p>
      </div>

      <form @submit.prevent="handleSubmit" class="form">
        <div class="section">
          <h2 class="section-title">{{ t('common.create') }}</h2>

          <div class="form-group">
            <label for="name">{{ t('common.name') }}:</label>
            <input
              id="name"
              v-model="formData.name"
              type="text"
              :placeholder="t('forms.validation.validName')"
              required
              :disabled="creatingProduct"
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
              :disabled="creatingProduct"
            />
          </div>
        </div>

        <div class="section">
          <h2 class="section-title">{{ t('products.composition') }}</h2>

          <div v-if="compositionError" class="error-message">
            <p>{{ compositionError }}</p>
          </div>

          <div class="composition-input">
            <div class="form-group">
              <label for="rawMaterial">{{ t('products.rawMaterial') }}:</label>
              <select
                id="rawMaterial"
                v-model.number="selectedRawMaterialCode"
                :disabled="loadingMaterials || creatingProduct"
              >
                <option :value="null">{{ t('products.selectRawMaterial') }}</option>
                <option
                  v-for="material in availableRawMaterials"
                  :key="material.code"
                  :value="material.code"
                >
                  {{ material.name }} ({{ t('rawMaterials.currentStock') }}: {{ material.stockQuantity }})
                </option>
              </select>
            </div>

            <div class="form-group">
              <label for="quantity">{{ t('products.quantity') }}:</label>
              <input
                id="quantity"
                v-model.number="quantityRequired"
                type="number"
                step="1"
                min="0"
                :disabled="creatingProduct"
              />
            </div>

            <button
              type="button"
              class="btn btn-sm btn-secondary"
              @click="addComposition"
              :disabled="creatingProduct || loadingMaterials"
            >
              {{ t('products.addComposition') }}
            </button>
          </div>

          <div v-if="formData.compositions.length > 0" class="compositions-list">
            <h3>{{ t('products.compositions') }} ({{ formData.compositions.length }})</h3>
            <table class="compositions-table">
              <thead>
                <tr>
                  <th>{{ t('products.rawMaterial') }}</th>
                  <th>{{ t('products.quantity') }}</th>
                  <th>{{ t('common.actions') }}</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(composition, index) in formData.compositions" :key="index">
                  <td>{{ getRawMaterialName(composition.rawMaterialCode) }}</td>
                  <td>{{ composition.quantityRequired }}</td>
                  <td>
                    <button
                      type="button"
                      class="btn btn-sm btn-danger"
                      @click="removeComposition(index)"
                      :disabled="creatingProduct"
                    >
                      {{ t('products.removeComposition') }}
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div v-else class="empty-compositions">
            <p>{{ t('products.noCompositions') }}</p>
          </div>
        </div>

        <div class="form-actions">
          <button
            type="submit"
            class="btn btn-primary"
            :disabled="creatingProduct || !formData.compositions.length"
          >
            {{ creatingProduct ? t('common.loading') : t('common.save') }}
          </button>
          <button
            type="button"
            class="btn btn-secondary"
            @click="goBack"
            :disabled="creatingProduct"
          >
            {{ t('common.cancel') }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.new-product-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  min-height: 100vh;
}

.btn-back {
  background: none;
  border: none;
  color: #1976d2;
  font-size: 16px;
  cursor: pointer;
  margin-bottom: 30px;
  transition: all 0.3s ease;
  font-weight: 500;
}

.btn-back:hover {
  transform: translateX(-4px);
}

.form-container {
  background: white;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.form-header {
  margin-bottom: 30px;
  border-bottom: 2px solid #f0f0f0;
  padding-bottom: 20px;
}

.form-header h1 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 2rem;
}

.subtitle {
  margin: 0;
  color: #666;
  font-size: 0.95rem;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.section {
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  background: #fafafa;
}

.section-title {
  margin: 0 0 20px 0;
  color: #333;
  font-size: 1.2rem;
  font-weight: 600;
  border-bottom: 2px solid #4facfe;
  padding-bottom: 10px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 16px;
}

.form-group label {
  font-weight: 600;
  color: #555;
  margin-bottom: 8px;
  font-size: 0.95rem;
}

.form-group input,
.form-group select {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  font-family: inherit;
  transition: all 0.3s ease;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #4facfe;
  box-shadow: 0 0 0 3px rgba(79, 172, 254, 0.1);
}

.form-group input:disabled,
.form-group select:disabled {
  background-color: #f5f5f5;
  cursor: not-allowed;
  opacity: 0.6;
}

.error-message {
  background-color: #ffebee;
  border-left: 4px solid #f44336;
  padding: 12px;
  border-radius: 6px;
  color: #c62828;
  margin-bottom: 16px;
  font-size: 0.9rem;
}

.composition-input {
  display: grid;
  grid-template-columns: 1fr 1fr auto;
  gap: 12px;
  align-items: flex-end;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ddd;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-sm {
  padding: 8px 16px;
  font-size: 0.9rem;
}

.btn-primary {
  background: #4facfe;
  color: white;
}

.btn-primary:hover:not(:disabled) {
  background: #3d9dea;
}

.btn-secondary {
  background: #757575;
  color: white;
}

.btn-secondary:hover:not(:disabled) {
  background: #616161;
}

.btn-danger {
  background: #f44336;
  color: white;
}

.btn-danger:hover:not(:disabled) {
  background: #da190b;
}

.compositions-list {
  background: white;
  border-radius: 8px;
  padding: 16px;
  margin-top: 20px;
}

.compositions-list h3 {
  margin: 0 0 16px 0;
  color: #333;
  font-size: 1rem;
}

.compositions-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.95rem;
}

.compositions-table thead {
  background: #f0f0f0;
  border-bottom: 2px solid #ddd;
}

.compositions-table th {
  padding: 12px;
  text-align: left;
  font-weight: 600;
  color: #555;
}

.compositions-table td {
  padding: 12px;
  border-bottom: 1px solid #e0e0e0;
}

.compositions-table tbody tr:hover {
  background-color: #f9f9f9;
}

.empty-compositions {
  text-align: center;
  padding: 40px 20px;
  color: #999;
  font-style: italic;
  background: white;
  border-radius: 8px;
  border: 1px dashed #ddd;
}

.form-actions {
  display: flex;
  gap: 12px;
  margin-top: 20px;
}

.form-actions button {
  flex: 1;
}

@media (max-width: 768px) {
  .new-product-page {
    padding: 15px;
  }

  .form-container {
    padding: 20px;
  }

  .form-header h1 {
    font-size: 1.5rem;
  }

  .composition-input {
    grid-template-columns: 1fr;
  }

  .form-actions {
    flex-direction: column;
  }

  .compositions-table {
    font-size: 0.85rem;
  }

  .compositions-table th,
  .compositions-table td {
    padding: 8px;
  }
}
</style>
