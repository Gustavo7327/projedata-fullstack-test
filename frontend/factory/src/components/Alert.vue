<script setup lang="ts">
import { computed } from 'vue'
import { useAlert } from '@/composables/useAlert'

const { state, hideAlert } = useAlert()

const typeClass = computed(() => {
  switch (state.type) {
    case 'success': return 'alert-success'
    case 'error': return 'alert-error'
    case 'warning': return 'alert-warning'
    default: return 'alert-info'
  }
})
</script>

<template>
  <div v-if="state.visible" class="alert-overlay">
    <div :class="['alert-box', typeClass]">
      <div class="alert-content">
        <strong v-if="state.title" class="alert-title">{{ state.title }}</strong>
        <p class="alert-message">{{ state.message }}</p>
      </div>
      <div class="alert-actions">
        <button class="btn btn-primary" @click="hideAlert">OK</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.alert-overlay {
  position: fixed;
  inset: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0,0,0,0.3);
  z-index: 1000;
}

.alert-box {
  width: 90%;
  max-width: 520px;
  background: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 8px 30px rgba(0,0,0,0.2);
  display: flex;
  gap: 16px;
  align-items: center;
  justify-content: space-between;
}

.alert-content { flex: 1 }
.alert-title { display: block; margin-bottom: 6px; font-size: 1.05rem }
.alert-message { margin: 0; color: #333 }

.alert-actions { margin-left: 12px }

.alert-success { border-left: 6px solid #4caf50 }
.alert-error { border-left: 6px solid #d32f2f }
.alert-warning { border-left: 6px solid #ff9800 }
.alert-info { border-left: 6px solid #2196f3 }

.btn { padding: 8px 14px; border-radius: 6px; border: none; cursor: pointer }
.btn-primary { background: #1976d2; color: white }
</style>
