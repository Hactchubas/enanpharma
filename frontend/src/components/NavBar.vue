<script setup>
import auth from "../api/auth.js";
import { ref, onMounted } from "vue";

const info = ref({
  isAdmin: false
})

onMounted(fetchUserInfo)
async function fetchUserInfo() {
  const res = await auth.getUserInfo()
  const roles = res.roles
  if (roles.includes("ADMIN")) {
    info.value.isAdmin = true
  } else {
    info.value.isAdmin = false
  }
}

function logout() {
  auth.clearToken();
  window.location.href = "/";
}
</script>

<template>
  <nav class="navbar">
    <div class="navbar-left">
      <a href="/" class="brand">Enanpharma<span v-if='info.isAdmin'> [ADMIN]</span></a>
    </div>
    <div class="navbar-right">
      <template v-if="auth.isLoggedIn()">
        <a href="/profile">Profile</a>
        <button @click="logout">Logout</button>
      </template>
      <template v-else>
        <a href="/login">Login</a>
        <a href="/signup">Sign Up</a>
      </template>
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #222;
  color: white;
  padding: 10px 20px;
}

.navbar a {
  color: white;
  text-decoration: none;
  margin: 0 10px;
}

.navbar button {
  margin-left: 10px;
  padding: 5px 10px;
  background-color: #444;
  color: white;
  border: none;
  cursor: pointer;
}

.navbar button:hover {
  background-color: #666;
}

.navbar .brand {
  font-weight: bold;
  font-size: 1.2em;
}
</style>
