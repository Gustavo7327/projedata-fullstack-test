import { createI18n } from 'vue-i18n'
import { en } from './en'
import { pt } from './pt'

type MessageSchema = typeof en

const i18n = createI18n<{ message: MessageSchema }, 'pt' | 'en'>({
  legacy: false,
  locale: 'pt',
  fallbackLocale: 'en',
  messages: {
    en,
    pt
  }
})

export default i18n
export type { MessageSchema }
