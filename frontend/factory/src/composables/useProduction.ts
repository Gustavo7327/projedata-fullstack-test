import { ref, computed } from 'vue'
import { productionService } from '@/services/productionService'
import type { ProductionSuggestionResponse, ProductionItem } from '@/types'


export function useProduction() {
  const suggestions = ref<ProductionItem[]>([])
  const totalValue = ref(0)
  const message = ref('')
  const loading = ref(false)
  const error = ref<string | null>(null)

  const hasSuggestions = computed(() => suggestions.value.length > 0)
  const suggestionCount = computed(() => suggestions.value.length)

  const calculateSuggestion = async () => {
    loading.value = true
    error.value = null
    try {
      const response: ProductionSuggestionResponse = await productionService.getSuggestion()
      suggestions.value = response.suggestions
      totalValue.value = response.totalValue
    } catch (err) {
      error.value = 'Error calculating production suggestion'
      console.error('calculateSuggestion error:', err)
    } finally {
      loading.value = false
    }
  }

  const clearError = () => {
    error.value = null
  }

  const clearSuggestions = () => {
    suggestions.value = []
    totalValue.value = 0
    message.value = ''
  }

  return {
    suggestions,
    totalValue,
    message,
    loading,
    error,
    hasSuggestions,
    suggestionCount,
    calculateSuggestion,
    clearError,
    clearSuggestions
  }
}
