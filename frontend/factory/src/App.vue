<script setup lang="ts">
import { RouterView } from 'vue-router'
import NavigationHeader from './components/NavigationHeader.vue'
import Alert from './components/Alert.vue'
import { useI18n } from 'vue-i18n'
import { onMounted } from 'vue'

const { locale } = useI18n()

onMounted(() => {
  const savedLocale = localStorage.getItem('locale') as 'pt' | 'en'
  if (savedLocale) {
    locale.value = savedLocale
  }
})
</script>

<template>
  <div id="app">
    <NavigationHeader />
    <main class="main-content">
      <RouterView />
    </main>
    <Alert />
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial,
    'Noto Sans', sans-serif;
  background-color: #fafafa;
  color: #333;
}

#app {
  min-width: 100%;
  display: flex;
  flex-direction: column;
}
</style>
