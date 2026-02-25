export interface RawMaterialResponse {
  code: number
  name: string
  stockQuantity: number
}

export interface RawMaterialRequest {
  name: string
  stockQuantity: number
}

export interface RawMaterialUpdate {
  name: string
  stockQuantity: number
}

export interface ProductCompositionResponse {
  id: number
  rawMaterial: RawMaterialResponse
  quantityRequired: number
}

export interface ProductCompositionRequest {
  rawMaterialCode: number
  quantityRequired: number
}

export interface ProductResponse {
  code: number
  name: string
  value: number
  compositions: ProductCompositionResponse[]
}

export interface ProductRequest {
  name: string
  value: number
  compositions: ProductCompositionRequest[]
}

export interface ProductUpdate {
  name: string
  value: number
}

export interface ProductionItem {
  productCode: number
  productName: string
  quantityToProduce: number
  unitValue: number
  totalValue: number
}

export interface ProductionSuggestionResponse {
  suggestions: ProductionItem[]
  totalValue: number
  message: string
}

export interface ErrorResponse {
  status: string
  code: number
  errors: string[]
  timestamp: string
}
