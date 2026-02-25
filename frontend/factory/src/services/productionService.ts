import api from './api'
import type { ProductionSuggestionResponse } from '@/types'

export const productionService = {
  async getSuggestion(): Promise<ProductionSuggestionResponse> {
    const response = await api.get<ProductionSuggestionResponse>('/production/suggest')
    return response.data
  }
}
