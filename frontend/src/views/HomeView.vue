<template>
  <div class="home">
    <div v-if="username">
      <h1 v-if="username">Welcome back, {{ username }}!</h1>
      <router-link to="/register/category"><button>Sign Up</button></router-link>
    </div>




    <div v-else>
      <h1>Welcome to Enanpharma</h1>
      <router-link to="/login"><button @click='$router.push("login")'>Login</button></router-link>
      <router-link to="/signup"><button>Sign Up</button></router-link>
    </div>


    <div v-if="username">
      <button @click="logout">Logout</button>
      <RouterLink v-if="username" to="/profile">Go to Profile</RouterLink>
    </div>
  </div>
</template>

<script setup>
import CategoriesList from "../components/CategoriesList.vue";




import { ref, onMounted } from 'vue'
import api from '../api/axios'
import { useRouter } from 'vue-router'
const router = useRouter()
import { computed } from "vue";


let username = ref(null)


onMounted(async () => {
  const token = localStorage.getItem('token')
  if (token) {
    username.value = localStorage.getItem('username')
  }
})

const logout = () => {
  localStorage.removeItem('username')
  localStorage.removeItem('token')
  username.value = null
  router.push('/')
}
</script>

<style scoped>
button {
  margin: 0 10px;
}
</style>
