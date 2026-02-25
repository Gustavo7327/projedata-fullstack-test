import axios, { type AxiosInstance, AxiosError } from 'axios'


const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080'

const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: false 
})

api.interceptors.request.use((config) => {
  console.log(`[API Request] ${config.method?.toUpperCase()} ${config.baseURL}${config.url}`)
  return config
})

api.interceptors.response.use(
  (response) => {
    console.log(`[API Response] ${response.status} ${response.config.url}`)
    return response
  },
  (error: AxiosError) => {
    if (error.response) {
      console.error(`[API Error] Status: ${error.response.status}`, error.response.data)
    } else if (error.request) {
      console.error('[API Error] Nenhuma resposta do servidor:', error.request)
    } else {
      console.error('[API Error] Erro na requisição:', error.message)
    }
    return Promise.reject(error)
  }
)

export default api
