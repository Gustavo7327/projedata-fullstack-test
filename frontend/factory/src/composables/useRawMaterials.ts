import { ref, computed } from 'vue'
import { rawMaterialService } from '@/services/rawMaterialService'
import type { RawMaterialResponse, RawMaterialRequest, RawMaterialUpdate } from '@/types'


export function useRawMaterials() {
  const rawMaterials = ref<RawMaterialResponse[]>([])
  const loading = ref(false)
  const error = ref<string | null>(null)
  const selectedMaterial = ref<RawMaterialResponse | null>(null)

  const hasMaterials = computed(() => rawMaterials.value.length > 0)
  const materialCount = computed(() => rawMaterials.value.length)

  const loadMaterials = async () => {
    loading.value = true
    error.value = null
    try {
      rawMaterials.value = await rawMaterialService.getAllRawMaterials()
    } catch (err) {
      error.value = 'Error loading raw materials'
      console.error('loadMaterials error:', err)
    } finally {
      loading.value = false
    }
  }

  const loadMaterialById = async (code: number) => {
    loading.value = true
    error.value = null
    try {
      selectedMaterial.value = await rawMaterialService.getRawMaterialByCode(code)
    } catch (err) {
      error.value = 'Error loading raw material'
      console.error('loadMaterialById error:', err)
    } finally {
      loading.value = false
    }
  }

  const createMaterial = async (material: RawMaterialRequest) => {
    loading.value = true
    try {
      const newMaterial = await rawMaterialService.createRawMaterial(material)
      rawMaterials.value.push(newMaterial)
      return newMaterial
    } catch (err) {
      console.error('createMaterial error:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const updateMaterial = async (code: number, material: RawMaterialUpdate) => {
    loading.value = true
    try {
      const updated = await rawMaterialService.updateRawMaterial(code, material)
      const index = rawMaterials.value.findIndex(m => m.code === code)
      if (index !== -1) {
        rawMaterials.value[index] = updated
      }
      if (selectedMaterial.value?.code === code) {
        selectedMaterial.value = updated
      }
      return updated
    } catch (err) {
      console.error('updateMaterial error:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const deleteMaterial = async (code: number) => {
    loading.value = true
    try {
      await rawMaterialService.deleteRawMaterial(code)
      rawMaterials.value = rawMaterials.value.filter(m => m.code !== code)
      if (selectedMaterial.value?.code === code) {
        selectedMaterial.value = null
      }
    } catch (err) {
      console.error('deleteMaterial error:', err)
      throw err
    } finally {
      loading.value = false
    }
  }

  const clearError = () => {
    error.value = null
  }

  const clearSelection = () => {
    selectedMaterial.value = null
  }

  return {
    rawMaterials,
    loading,
    error,
    selectedMaterial,
    hasMaterials,
    materialCount,
    loadMaterials,
    loadMaterialById,
    createMaterial,
    updateMaterial,
    deleteMaterial,
    clearError,
    clearSelection
  }
}
