<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { useProduction } from '@/composables/useProduction'

const { t } = useI18n()
const { suggestions, totalValue, loading, error, calculateSuggestion } = useProduction()

const message = computed(() => {
  if (suggestions.value.length === 0) {
    return t('production.noSuggestions')
  }
  return t('production.suggestionMessage', {
    count: suggestions.value.length,
    value: formatCurrency(totalValue.value)
  })
})

onMounted(calculateSuggestion)

const formatCurrency = (value: number): string => {
  return new Intl.NumberFormat('pt-BR', {
    style: 'currency',
    currency: 'BRL'
  }).format(value)
}

const handleRecalculate = async () => {
  await calculateSuggestion()
}
</script>

<template>
  <div class="production-container">
    <div class="page-header">
      <div class="header-content">
        <h1>{{ t('production.title') }}</h1>
        <p class="subtitle">{{ t('production.description') }}</p>
      </div>
      <button class="btn btn-primary" @click="handleRecalculate">
        {{ t('production.calculateSuggestion') }}
      </button>
    </div>

    <div v-if="loading" class="loading-state">
      <div class="spinner"></div>
      <p>{{ t('production.calculating') }}</p>
    </div>

    <div v-else-if="error" class="error-state">
      <p>{{ error }}</p>
    </div>

    <div v-else-if="suggestions.length > 0" class="success-container">
      <div class="info-card">
        <h2>{{ t('home.systemInfo') }}</h2>
        <p>{{ message }}</p>
      </div>

      <div class="suggestions-section">
        <h2>{{ t('production.suggestions') }}</h2>

        <div class="suggestions-grid">
          <div v-for="(suggestion, index) in suggestions" :key="index" class="suggestion-card">
            <div class="card-header">
              <h3 class="product-name">{{ suggestion.productName }}</h3>
              <span class="product-code">#{{ suggestion.productCode }}</span>
            </div>
            <div class="card-body">
              <div class="info-row">
                <span class="label">{{ t('production.quantity') }}:</span>
                <span class="value">{{ suggestion.quantityToProduce }} und.</span>
              </div>
              <div class="info-row">
                <span class="label">{{ t('production.profitValue') }}:</span>
                <span class="value profit">{{ formatCurrency(suggestion.totalValue) }}</span>
              </div>
            </div>
          </div>
        </div>

        <div class="total-section">
          <div class="total-card">
            <h2>{{ t('production.totalProfit') }}</h2>
            <div class="total-value">{{ formatCurrency(totalValue) }}</div>
          </div>
        </div>
      </div>
    </div>

    <div v-else class="empty-state">
      <svg class="empty-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor">
        <path d="M12 2l10 6v8c0 5.5-4.5 10-10 10S2 21.5 2 16V8l10-6z"></path>
      </svg>
      <h2>{{ t('production.noSuggestions') }}</h2>
      <button class="btn btn-primary" @click="handleRecalculate">
        {{ t('production.calculateSuggestion') }}
      </button>
    </div>
  </div>
</template>

<style scoped>
.production-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 40px;
  gap: 20px;
  flex-wrap: wrap;
}

.header-content {
  flex: 1;
  min-width: 300px;
}

.page-header h1 {
  font-size: 2.5rem;
  color: #333;
  margin: 0 0 10px 0;
}

.subtitle {
  color: #666;
  font-size: 1.1rem;
  margin: 0;
}

.btn {
  padding: 10px 20px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  white-space: nowrap;
}

.btn-primary {
  background: #4facfe;
  color: white;
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(79, 172, 254, 0.3);
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 20px;
  text-align: center;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid #f0f0f0;
  border-top: 5px solid #4facfe;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 20px;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-state {
  background: #ffebee;
  border-left: 4px solid #f44336;
  padding: 20px;
  border-radius: 8px;
  color: #c62828;
}

.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 80px 20px;
  text-align: center;
  color: #999;
}

.empty-icon {
  width: 120px;
  height: 120px;
  margin-bottom: 20px;
  opacity: 0.3;
}

.empty-state h2 {
  color: #555;
  margin: 0 0 20px 0;
}

.success-container {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.info-card {
  background: linear-gradient(135deg, #e0f2f1 0%, #b2dfdb 100%);
  padding: 30px;
  border-radius: 12px;
  border-left: 4px solid #16a085;
}

.info-card h2 {
  color: #16a085;
  margin: 0 0 15px 0;
}

.info-card p {
  color: #555;
  margin: 0;
  line-height: 1.6;
}

.suggestions-section h2 {
  color: #333;
  font-size: 2rem;
  margin: 0 0 30px 0;
}

.suggestions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.suggestion-card {
  background: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: all 0.3s ease;
  border-top: 4px solid #4facfe;
}

.suggestion-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.card-header {
  background:  #4facfe;
  color: white;
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.product-name {
  margin: 0;
  font-size: 1.3rem;
  font-weight: 600;
}

.product-code {
  opacity: 0.8;
  font-size: 0.9rem;
}

.card-body {
  padding: 20px;
}

.info-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.info-row:last-child {
  margin-bottom: 0;
  padding-bottom: 0;
  border-bottom: none;
}

.label {
  font-weight: 600;
  color: #666;
}

.value {
  color: #333;
  font-weight: 600;
}

.profit {
  color: #4CAF50;
  font-size: 1.1rem;
}


.total-section {
  display: flex;
  justify-content: center;
}

.total-card {
  background: linear-gradient(135deg, #4CAF50 0%, #45a049 100%);
  color: white;
  padding: 16px;
  border-radius: 12px;
  text-align: center;
  min-width: 300px;
  box-shadow: 0 8px 24px rgba(76, 175, 80, 0.3);
}

.total-card h2 {
  margin: 0 0 20px 0;
  font-size: 2rem;
  color: white;
}

.total-value {
  font-size: 2.5rem;
  font-weight: 800;
  letter-spacing: -1px;
}

@media (max-width: 768px) {
  .production-container {
    padding: 15px;
  }

  .page-header {
    flex-direction: column;
  }

  .page-header h1 {
    font-size: 2rem;
  }

  .suggestions-grid {
    grid-template-columns: 1fr;
  }

  .total-card {
    min-width: auto;
  }

  .total-value {
    font-size: 2rem;
  }
}
</style>
