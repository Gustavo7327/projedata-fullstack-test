<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { rawMaterialService } from '@/services/rawMaterialService'
import type { RawMaterialResponse, RawMaterialUpdate } from '@/types'
import { useI18n } from 'vue-i18n'

const {t} = useI18n()
const router = useRouter()
const route = useRoute()

const rawMaterial = ref<RawMaterialResponse | null>(null)
const loading = ref(false)
const error = ref<string | null>(null)
const isEditing = ref(false)

const formData = ref<RawMaterialUpdate>({
  name: '',
  stockQuantity: 0
})

const code = parseInt(route.params.code as string)

const loadRawMaterial = async () => {
  loading.value = true
  error.value = null
  try {
    rawMaterial.value = await rawMaterialService.getRawMaterialByCode(code)
    if (rawMaterial.value) {
      formData.value = {
        name: rawMaterial.value.name,
        stockQuantity: rawMaterial.value.stockQuantity
      }
    }
  } catch (err) {
    error.value = 'Erro ao carregar matéria-prima'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const handleUpdate = async () => {
  loading.value = true
  error.value = null
  try {
    const updated = await rawMaterialService.updateRawMaterial(code, formData.value)
    rawMaterial.value = updated
    isEditing.value = false
    alert('Matéria-prima atualizada com sucesso!')
  } catch (err) {
    error.value = 'Erro ao atualizar matéria-prima'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const handleDelete = async () => {
  if (confirm('Tem certeza que deseja deletar esta matéria-prima?')) {
    loading.value = true
    error.value = null
    try {
      await rawMaterialService.deleteRawMaterial(code)
      alert('Matéria-prima deletada com sucesso!')
      router.push({ name: 'raw-materials' })
    } catch (err) {
      error.value = 'Erro ao deletar matéria-prima'
      console.error(err)
    } finally {
      loading.value = false
    }
  }
}

const goBack = () => {
  router.push({ name: 'raw-materials' })
}

onMounted(() => {
  loadRawMaterial()
})
</script>

<template>
  <div class="raw-material-detail-page">
    <button class="btn-back" @click="goBack">← Voltar</button>

    <div v-if="loading" class="loading">
      <p>Carregando...</p>
    </div>

    <div v-if="error" class="error">
      <p>{{ error }}</p>
      <button class="btn btn-secondary" @click="loadRawMaterial">Tentar Novamente</button>
    </div>

    <div v-if="!loading && rawMaterial" class="detail-container">
      <div class="detail-header">
        <div>
          <h1>{{ rawMaterial.name }}</h1>
          <p class="code">{{ t('common.code') }}: {{ rawMaterial.code }}</p>
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
          <label>{{t('rawMaterials.currentStock')}}</label>
          <p class="value">{{ rawMaterial.stockQuantity }}</p>
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
          <label for="stockQuantity">{{ t('rawMaterials.currentStock') }}:</label>
          <input
            id="stockQuantity"
            v-model.number="formData.stockQuantity"
            type="number"
            step="1"
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
.raw-material-detail-page {
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
  border-color: #4caf50;
  box-shadow: 0 0 0 3px rgba(76, 175, 80, 0.1);
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
