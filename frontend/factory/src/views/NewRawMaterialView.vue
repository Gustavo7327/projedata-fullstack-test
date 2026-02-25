<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useRawMaterials } from '@/composables/useRawMaterials'
import { useAlert } from '@/composables/useAlert'
import { getErrorMessageFromHttpError } from '@/utils/handleHttpError'
import type { RawMaterialRequest } from '@/types'

const router = useRouter()
const { t } = useI18n()
const { createMaterial, loading, error, clearError } = useRawMaterials()
const { showAlert } = useAlert()

const formData = ref<RawMaterialRequest>({
  name: '',
  stockQuantity: 0
})

const handleSubmit = async () => {
  if (!formData.value.name.trim()) {
    showAlert(t('forms.validation.required'), t('common.warning'), 'warning')
    return
  }

  if (formData.value.stockQuantity < 0) {
    showAlert(t('forms.validation.positiveNumber'), t('common.warning'), 'warning')
    return
  }

  try {
    await createMaterial(formData.value)
    showAlert(t('rawMaterials.materialCreated'), '', 'success')
    router.push({ name: 'raw-materials' })
  } catch (err) {
    const message = getErrorMessageFromHttpError(err, (k: string) => t(k))
    showAlert(message, t('common.error'), 'error')
    clearError()
  }
}

const goBack = () => {
  router.push({ name: 'raw-materials' })
}
</script>

<template>
  <div class="new-raw-material-page">
    <button class="btn-back" @click="goBack">← {{ t('common.back') }}</button>

    <div class="form-container">
      <div class="form-header">
        <h1>{{ t('rawMaterials.createMaterial') }}</h1>
        <p class="subtitle">{{ t('rawMaterials.description') }}</p>
      </div>

      <div v-if="error" class="error-message">
        <p>{{ error }}</p>
      </div>

      <form @submit.prevent="handleSubmit" class="form">
        <div class="form-group">
          <label for="name">{{ t('common.name') }}:</label>
          <input
            id="name"
            v-model="formData.name"
            type="text"
            :placeholder="t('forms.validation.validName')"
            required
            :disabled="loading"
          />
        </div>

        <div class="form-group">
          <label for="stockQuantity">{{ t('rawMaterials.currentStock') }}:</label>
          <input
            id="stockQuantity"
            v-model.number="formData.stockQuantity"
            type="number"
            step="1"
            min="0"
            required
            :disabled="loading"
          />
        </div>

        <div class="form-actions">
          <button
            type="submit"
            class="btn btn-primary"
            :disabled="loading"
          >
            {{ loading ? t('common.loading') : t('common.save') }}
          </button>
          <button
            type="button"
            class="btn btn-secondary"
            @click="goBack"
            :disabled="loading"
          >
            {{ t('common.cancel') }}
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.new-raw-material-page {
  max-width: 600px;
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

.error-message {
  background-color: #ffebee;
  border-left: 4px solid #f44336;
  padding: 15px;
  border-radius: 6px;
  color: #c62828;
  margin-bottom: 20px;
}

.form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 600;
  color: #555;
  margin-bottom: 8px;
  font-size: 0.95rem;
}

.form-group input {
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  font-family: inherit;
  transition: all 0.3s ease;
}

.form-group input:focus {
  outline: none;
  border-color: #4facfe;
  box-shadow: 0 0 0 3px rgba(79, 172, 254, 0.1);
}

.form-group input:disabled {
  background-color: #f5f5f5;
  cursor: not-allowed;
  opacity: 0.6;
}

.form-actions {
  display: flex;
  gap: 12px;
  margin-top: 20px;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 6px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  flex: 1;
}

.btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
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

@media (max-width: 768px) {
  .new-raw-material-page {
    padding: 15px;
  }

  .form-container {
    padding: 20px;
  }

  .form-header h1 {
    font-size: 1.5rem;
  }

  .form-actions {
    flex-direction: column;
  }
}
</style>
