import type { AxiosError } from 'axios'

function extractMessageFromData(data: any): string | null {
  if (!data) return null

  if (typeof data === 'string' && data.trim().length > 0) return data
  if (typeof data.message === 'string' && data.message.trim().length > 0) return data.message
  if (typeof data.error === 'string' && data.error.trim().length > 0) return data.error
  if (typeof data.detail === 'string' && data.detail.trim().length > 0) return data.detail
  if (typeof data.title === 'string' && data.title.trim().length > 0) return data.title

  if (Array.isArray(data.errors) && data.errors.length > 0) {
    const first = data.errors[0]
    if (typeof first === 'string') return first
    if (typeof first === 'object') {
      if (typeof first.message === 'string') return first.message
      if (typeof first.error === 'string') return first.error
    }
  }

  if (typeof data.errors === 'object') {
    for (const k of Object.keys(data.errors)) {
      const v = data.errors[k]
      if (Array.isArray(v) && v.length > 0 && typeof v[0] === 'string') return v[0]
    }
  }

  return null
}

export function getErrorMessageFromHttpError(err: unknown, t: (key: string) => string): string {
  try {
    const anyErr = err as AxiosError
    const response = anyErr?.response as any
    if (response) {
      const status: number = response.status
      const data = response.data

      const explicit = extractMessageFromData(data)
      if (explicit) return explicit

      switch (status) {
        case 400:
          return t('forms.errors.savingFailed')
        case 401:
          return t('common.error')
        case 403:
          return t('common.error')
        case 404:
          return t('forms.errors.loadingFailed')
        case 409:
          return t('forms.errors.deletingFailed')
        case 500:
          return t('forms.errors.apiError')
        default:
          return t('forms.errors.apiError')
      }
    }
  } catch (e) {
  }

  return t('forms.errors.apiError')
}
