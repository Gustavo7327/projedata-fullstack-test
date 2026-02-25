import { reactive } from 'vue'

type AlertType = 'info' | 'success' | 'error' | 'warning'

const state = reactive({
  visible: false,
  title: '',
  message: '',
  type: 'info' as AlertType
})

export function useAlert() {
  const showAlert = (message: string, title = '', type: AlertType = 'info') => {
    state.message = message
    state.title = title
    state.type = type
    state.visible = true
  }

  const hideAlert = () => {
    state.visible = false
    state.message = ''
    state.title = ''
    state.type = 'info'
  }

  return { state, showAlert, hideAlert }
}
