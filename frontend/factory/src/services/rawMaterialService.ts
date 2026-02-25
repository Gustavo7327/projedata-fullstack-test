import api from './api'
import type { RawMaterialResponse, RawMaterialRequest, RawMaterialUpdate } from '@/types'


export const rawMaterialService = {

  async getAllRawMaterials(): Promise<RawMaterialResponse[]> {
    const response = await api.get<RawMaterialResponse[]>('/raw-materials')
    return response.data
  },


  async getRawMaterialByCode(code: number): Promise<RawMaterialResponse> {
    const response = await api.get<RawMaterialResponse>(`/raw-materials/${code}`)
    return response.data
  },

  async createRawMaterial(material: RawMaterialRequest): Promise<RawMaterialResponse> {
    const response = await api.post<RawMaterialResponse>('/raw-materials', material)
    return response.data
  },

  async updateRawMaterial(code: number, material: RawMaterialUpdate): Promise<RawMaterialResponse> {
    const response = await api.put<RawMaterialResponse>(`/raw-materials/${code}`, material)
    return response.data
  },

  async deleteRawMaterial(code: number): Promise<void> {
    await api.delete(`/raw-materials/${code}`)
  }
}
