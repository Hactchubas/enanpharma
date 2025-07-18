<template>
  <div v-if="isAdmin" class="admin-panel">
    <h3>Admin Panel</h3>
    <nav>
      <router-link to="/categories">Manage Categories</router-link>
      <router-link to="/products">Manage Products</router-link>
    </nav>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import auth from '../api/auth';

const isAdmin = ref(false);

onMounted(async () => {
  const userInfo = await auth.getUserInfo();
  if (userInfo && userInfo.roles.includes('ADMIN')) {
    isAdmin.value = true;
  }
});
</script>

<style scoped>
.admin-panel {
  background-color: var(--surface-bg);
  padding: 1rem;
  border-radius: var(--radius-lg);
  margin-bottom: 1rem;
}

nav {
  display: flex;
  gap: 1rem;
}

a {
  color: var(--accent-primary);
  text-decoration: none;
}

a:hover {
  text-decoration: underline;
}
</style>
