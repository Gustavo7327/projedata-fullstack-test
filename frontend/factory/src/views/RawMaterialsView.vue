<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'
import { useRawMaterials } from '@/composables/useRawMaterials'
import { useAlert } from '@/composables/useAlert'
import { getErrorMessageFromHttpError } from '@/utils/handleHttpError'

const router = useRouter()
const { t } = useI18n()
const { rawMaterials, loading, error, loadMaterials, deleteMaterial, clearError } = useRawMaterials()
const { showAlert } = useAlert()

onMounted(loadMaterials)

const navigateToMaterial = (code: number) => {
  router.push(`/raw-materials/${code}`)
}

const handleDelete = async (code: number) => {
  if (confirm(t('common.confirmDelete'))) {
    try {
      await deleteMaterial(code)
      showAlert(t('rawMaterials.materialDeleted'), '', 'success')
    } catch (err) {
      const message = getErrorMessageFromHttpError(err, (k: string) => t(k))
      showAlert(message, t('common.error'), 'error')
      clearError()
    }
  }
}

const formatCurrency = (value: number): string => {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  }).format(value)
}
</script>

<template>
  <div class="materials-container">
    <div class="page-header">
      <div class="header-content">
        <h1>{{ t('rawMaterials.title') }}</h1>
        <p class="subtitle">{{ t('rawMaterials.description') }}</p>
      </div>
      <button class="btn btn-primary" @click="router.push('/raw-materials/new')">
        + {{ t('rawMaterials.addNew') }}
      </button>
    </div>

    <div v-if="loading" class="loading-state">
      <div class="spinner"></div>
      <p>{{ t('common.loading') }}</p>
    </div>

    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
    </div>

    <div v-else-if="rawMaterials.length === 0" class="empty-state">
      <svg class="empty-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path d="M12 2L2 7v10c0 5.55 3.84 10.74 9 12 5.16-1.26 9-6.45 9-12V7l-10-5z"></path>
      </svg>
      <h2>{{ t('rawMaterials.noMaterials') }}</h2>
    </div>

    <div v-else class="table-wrapper">
      <table class="materials-table">
        <thead>
          <tr>
            <th>{{ t('common.code') }}</th>
            <th>{{ t('common.name') }}</th>
            <th>{{ t('rawMaterials.currentStock') }}</th>
            <th>{{ t('common.actions') }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="material in rawMaterials" :key="material.code" class="table-row">
            <td class="code-cell">{{ material.code }}</td>
            <td class="name-cell">{{ material.name }}</td>
            <td class="value-cell">{{ material.stockQuantity }}</td>
            <td class="actions-cell">
              <button class="btn btn-sm btn-info" @click="navigateToMaterial(material.code)">
                {{ t('common.view') }}
              </button>
              <button class="btn btn-sm btn-danger" @click="handleDelete(material.code)">
                {{ t('common.delete') }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
.materials-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 40px;
  gap: 20px;
  flex-wrap: wrap;
}

.header-content {
  flex: 1;
  min-width: 300px;
}

.page-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin: 0 0 10px 0;
}

.subtitle {
  color: #666;
  font-size: 1.1rem;
  margin: 0;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  white-space: nowrap;
}

.btn-primary {
  background: #4facfe;
  color: white;
}

.btn-primary:hover {
  transform: translateY(-2px);
}

.btn-sm {
  padding: 8px 16px;
  font-size: 0.9rem;
}

.btn-info {
  background: #2196F3;
  color: white;
}

.btn-info:hover {
  background: #1976D2;
}

.btn-danger {
  background: #f44336;
  color: white;
}

.btn-danger:hover {
  background: #da190b;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  text-align: center;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid #f0f0f0;
  border-top: 5px solid #f093fb;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-state {
  background: #ffebee;
  border-left: 4px solid #f44336;
  padding: 20px;
  border-radius: 8px;
  color: #c62828;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
  text-align: center;
  color: #999;
}

.empty-icon {
  width: 120px;
  height: 120px;
  margin-bottom: 20px;
  opacity: 0.3;
}

.empty-state h2 {
  color: #555;
  margin: 0;
}

.table-wrapper {
  overflow-x: auto;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.materials-table {
  width: 100%;
  border-collapse: collapse;
  table-layout: auto;
  background: white;
}

.materials-table thead {
  background: #4facfe;
  color: white;
}

.materials-table th {
  padding: 16px;
  text-align: left;
  font-weight: 600;
  font-size: 0.95rem;
}

.materials-table tbody tr {
  border-bottom: 1px solid #e0e0e0;
  transition: background-color 0.2s ease;
}

.materials-table tbody tr:hover {
  background-color: #f5f5f5;
}

.materials-table td {
  padding: 16px;
  color: #555;
}

.code-cell {
  font-weight: 600;
  color: #333;

}

.name-cell {

}

.value-cell {
  font-weight: 600;
  color: #4CAF50;

}

.actions-cell {
  display: flex;
  gap: 8px;
}

@media (max-width: 768px) {
  .materials-container {
    padding: 15px;
  }

  .page-header {
    flex-direction: column;
  }

  .materials-table {
    font-size: 0.9rem;
  }

  .materials-table th,
  .materials-table td {
    padding: 12px;
  }

  .actions-cell {
    flex-direction: column;
  }
}
</style>
