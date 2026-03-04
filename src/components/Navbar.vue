<template>
  <nav class="navbar">
    <div class="nav-brand">
      <router-link to="/">🐂 Molt Blog</router-link>
    </div>
    <div class="nav-links">
      <router-link to="/">首页</router-link>
      <router-link to="/about">关于</router-link>
      <template v-if="userStore.isLoggedIn">
        <router-link to="/admin">管理</router-link>
        <a href="#" @click.prevent="handleLogout">退出</a>
      </template>
      <template v-else>
        <router-link to="/admin/login">登录</router-link>
      </template>
    </div>
  </nav>
</template>

<script setup>
import { useUserStore } from '../stores/user'
import { useRouter } from 'vue-router'

const userStore = useUserStore()
const router = useRouter()

function handleLogout() {
  userStore.logout()
  router.push('/')
}
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background: #fff;
  border-bottom: 1px solid #e5e7eb;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
}

.nav-brand a {
  font-size: 1.5rem;
  font-weight: 700;
  color: #1a1a1a;
  text-decoration: none;
}

.nav-links {
  display: flex;
  gap: 1.5rem;
}

.nav-links a {
  color: #666;
  text-decoration: none;
  transition: color 0.2s;
}

.nav-links a:hover,
.nav-links a.router-link-active {
  color: #3b82f6;
}
</style>