<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useI18n } from 'vue-i18n'

const router = useRouter()
const route = useRoute()
const { t, locale } = useI18n()

const mobileMenuOpen = ref(false)
const currentLocale = ref(locale.value)

const toggleMobileMenu = () => {
  mobileMenuOpen.value = !mobileMenuOpen.value
}

const closeMobileMenu = () => {
  mobileMenuOpen.value = false
}

const handleNavigation = (path: string) => {
  router.push(path)
  closeMobileMenu()
}

const changeLanguage = (lang: 'pt' | 'en') => {
  locale.value = lang
  currentLocale.value = lang
  localStorage.setItem('locale', lang)
  closeMobileMenu()
}

const isActive = (path: string) => {
  return route.path === path ? 'active' : ''
}

watch(() => locale.value, (newLocale) => {
  currentLocale.value = newLocale
})
</script>

<template>
  <header class="navbar">
    <div class="nav-container">
      <div class="nav-item">
          <button
            class="nav-link"
            :class="isActive('/')"
            @click="handleNavigation('/')"
          >
            {{ t('common.homeTitle') }}
          </button>
        </div>

      <div class="hamburger" :class="{ active: mobileMenuOpen }" @click="toggleMobileMenu">
        <span></span>
        <span></span>
        <span></span>
      </div>

      <nav class="nav-menu" :class="{ active: mobileMenuOpen }">
        <div class="nav-item">
          <button
            class="nav-link"
            :class="isActive('/products')"
            @click="handleNavigation('/products')"
          >
            {{ t('navigation.products') }}
          </button>
        </div>

        <div class="nav-item">
          <button
            class="nav-link"
            :class="isActive('/raw-materials')"
            @click="handleNavigation('/raw-materials')"
          >
            {{ t('navigation.rawMaterials') }}
          </button>
        </div>

        <div class="nav-item">
          <button
            class="nav-link"
            :class="isActive('/production')"
            @click="handleNavigation('/production')"
          >
            {{ t('navigation.production') }}
          </button>
        </div>

        <div class="nav-divider"></div>
        <div class="nav-item language-switcher">
          <span class="lang-label">{{ t('common.language') }}:</span>
          <div class="lang-buttons">
            <button
              class="lang-btn"
              :class="{ active: currentLocale === 'pt' }"
              @click="changeLanguage('pt')"
            >
              🇧🇷 PT
            </button>
            <button
              class="lang-btn"
              :class="{ active: currentLocale === 'en' }"
              @click="changeLanguage('en')"
            >
              🇺🇸 EN
            </button>
          </div>
        </div>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.navbar {
  background: #2b8de2;
  padding: 1rem 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-container {
  margin: 0 auto;
  padding: 0 1rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.nav-logo {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  cursor: pointer;
  color: white;
  font-weight: bold;
  font-size: 1.25rem;
  transition: transform 0.3s ease;
}

.nav-logo:hover {
  transform: scale(1.05);
}


.logo-text {
  letter-spacing: 0.5px;
}

.hamburger {
  display: none;
  flex-direction: column;
  cursor: pointer;
  gap: 0.35rem;
}

.hamburger span {
  width: 25px;
  height: 3px;
  background-color: white;
  border-radius: 2px;
  transition: all 0.3s ease;
}

.hamburger.active span:nth-child(1) {
  transform: rotate(45deg) translate(6px, 6px);
}

.hamburger.active span:nth-child(2) {
  opacity: 0;
}

.hamburger.active span:nth-child(3) {
  transform: rotate(-45deg) translate(6px, -6px);
}

.nav-menu {
  display: flex;
  align-items: center;
  gap: 2rem;
}

.nav-item {
  display: flex;
  align-items: center;
}

.nav-link {
  background: none;
  border: none;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  transition: all 0.3s ease;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px);
}

.nav-link.active {
  background-color: rgba(255, 255, 255, 0.3);
  border-bottom: 2px solid white;
}

.nav-divider {
  display: none;
  width: 1px;
  height: 30px;
  background-color: rgba(255, 255, 255, 0.3);
}

.language-switcher {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.lang-label {
  color: white;
  font-size: 0.9rem;
  display: none;
}

.lang-buttons {
  display: flex;
  gap: 0.25rem;
}

.lang-btn {
  background: rgba(255, 255, 255, 0.2);
  border: 2px solid transparent;
  color: white;
  padding: 0.4rem 0.75rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.85rem;
  font-weight: 600;
  transition: all 0.3s ease;
}

.lang-btn:hover {
  background: rgba(255, 255, 255, 0.3);
}

.lang-btn.active {
  background: rgba(255, 255, 255, 0.4);
  border-color: white;
}

@media (max-width: 768px) {
  .hamburger {
    display: flex;
  }

  .nav-menu {
    position: absolute;
    top: 100%;
    right: 0;
    left: 0;
    flex-direction: column;
    gap: 0;
    background: #2b8de2;
    padding: 1rem;
    max-height: 0;
    overflow: hidden;
    transition: max-height 0.3s ease;
  }

  .nav-menu.active {
    max-height: 300px;
  }

  .nav-item {
    width: 100%;
    padding: 0.75rem 0;
  }

  .nav-link {
    width: 100%;
    padding: 0.75rem 1rem;
    justify-content: flex-start;
  }

  .nav-divider {
    display: block;
    width: 100%;
    height: 1px;
    margin: 0.5rem 0;
  }

  .language-switcher {
    width: 100%;
    justify-content: space-between;
    padding: 0 1rem;
  }

  .lang-label {
    display: block;
  }

  .nav-container {
    position: relative;
    gap: 16px;
  }
}

@media (max-width: 480px) {
  .nav-logo {
    font-size: 1rem;
  }

  .logo-emoji {
    font-size: 1.5rem;
  }

  .nav-link {
    font-size: 0.9rem;
  }

  .lang-btn {
    padding: 0.3rem 0.5rem;
    font-size: 0.75rem;
  }
}
</style>
