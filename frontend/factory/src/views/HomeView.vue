<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useI18n } from 'vue-i18n'

const router = useRouter()
const { t } = useI18n()

const navigateTo = (route: string) => {
  router.push(route)
}
</script>

<template>
  <div class="home-container">

    <main class="main-content">
      <section class="info-section">
        <div class="info-container">
          <h2>{{ t('home.systemInfo') }}</h2>
          <p class="system-description">
            {{ t('production.algorithmExplanation') }}
          </p>
        </div>
      </section>

      <section class="features-section">
        <h2>{{ t('home.features') }}</h2>

        <div class="features-grid">
          <div class="feature-card">
            <div class="feature-icon">
              <img src="../assets/package.svg" alt="" class="icon-svg">
            </div>
            <h3>{{ t('navigation.products') }}</h3>
            <p>{{ t('home.productsDescription') }}</p>
            <button class="btn btn-feature" @click="navigateTo('/products')">
              {{ t('home.startExploring') }} →
            </button>
          </div>

          <div class="feature-card">
            <div class="feature-icon materials-icon">
              <img src="../assets/material.svg" alt="" class="icon-svg">
            </div>
            <h3>{{ t('navigation.rawMaterials') }}</h3>
            <p>{{ t('home.rawMaterialsDescription') }}</p>
            <button class="btn btn-feature" @click="navigateTo('/raw-materials')">
              {{ t('home.startExploring') }} →
            </button>
          </div>

          <div class="feature-card">
            <div class="feature-icon production-icon">
              <img src="../assets/production.svg" alt="" class="icon-svg">
            </div>
            <h3>{{ t('navigation.production') }}</h3>
            <p>{{ t('home.productionDescription') }}</p>
            <button class="btn btn-feature" @click="navigateTo('/production')">
              {{ t('home.startExploring') }} →
            </button>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<style scoped>
.home-container {
  background-color: white;
  display: flex;
  flex-direction: column;
  flex: 1;
}

.main-content {
  flex: 1;
  padding: 20px 20px;
  background: white;
}

.info-section {
  max-width: 1000px;
  margin: 0 auto 24px;
  padding: 40px;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  border-radius: 12px;
  text-align: center;
}

.info-section h2 {
  color: #333;
  font-size: 2rem;
  margin: 0 0 20px 0;
}

.system-description {
  color: #555;
  font-size: 1.1rem;
  line-height: 1.8;
  margin: 0;
}

.features-section {
  max-width: 1200px;
  margin: 0 auto;
}

.features-section h2 {
  text-align: center;
  color: #333;
  font-size: 2.5rem;
  margin: 0 0 24px 0;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
  gap: 30px;
  margin-bottom: 40px;
}

.feature-card {
  background: white;
  border-radius: 12px;
  padding: 40px 30px;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  border: 2px solid transparent;
}

.feature-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.15);
  border-color: #2b8de2;
}

.feature-icon {
  width: 80px;
  height: 80px;
  margin: 0 auto 20px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 40px;
}

.icon-svg {
  width: 64px;
  height: 64px;
}

.feature-icon svg {
  width: 45px;
  height: 45px;
}

.feature-card h3 {
  color: #333;
  font-size: 1.5rem;
  margin: 0 0 15px 0;
}

.feature-card p {
  color: #666;
  font-size: 1rem;
  line-height: 1.6;
  margin: 0 0 25px 0;
}

.btn {
  padding: 12px 24px;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  text-decoration: none;
  display: inline-block;
}

.btn-feature {
  background: #4facfe;
  color: white;
}

.btn-feature:hover {
  transform: scale(1.05);
  box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
}

.btn-feature:active {
  transform: scale(0.98);
}

@media (max-width: 768px) {
  .hero-section {
    padding: 40px 20px;
  }

  .main-content {
    padding: 40px 20px;
  }

  .info-section {
    padding: 30px 20px;
    margin-bottom: 40px;
  }

  .features-section h2 {
    font-size: 1.8rem;
    margin-bottom: 30px;
  }

  .features-grid {
    gap: 20px;
  }

  .feature-card {
    padding: 25px 20px;
  }

  .feature-icon {
    width: 60px;
    height: 60px;
  }

  .feature-icon svg {
    width: 35px;
    height: 35px;
  }

  .feature-card h3 {
    font-size: 1.3rem;
  }

  .feature-card p {
    font-size: 0.95rem;
  }
}

@media (max-width: 480px) {
  .hero-title {
    font-size: 1.8rem;
  }

  .hero-subtitle {
    font-size: 1.1rem;
  }

  .info-section h2 {
    font-size: 1.5rem;
  }

  .features-section h2 {
    font-size: 1.5rem;
  }

  .feature-card {
    padding: 20px 15px;
  }
}
</style>
