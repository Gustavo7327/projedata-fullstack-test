import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },

    {
      path: '/products',
      name: 'products',
      component: () => import('../views/ProductsView.vue'),
    },
    {
      path: '/products/:code',
      name: 'product-detail',
      component: () => import('../views/ProductDetailView.vue'),
    },

    {
      path: '/raw-materials',
      name: 'raw-materials',
      component: () => import('../views/RawMaterialsView.vue'),
    },
    {
      path: '/raw-materials/:code',
      name: 'raw-material-detail',
      component: () => import('../views/RawMaterialDetailView.vue'),
    },

    {
      path: '/production',
      name: 'production',
      component: () => import('../views/ProductionView.vue'),
    },
  ],
})

export default router
