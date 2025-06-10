<template>
  <form @submit.prevent="login">
    <div>
      <label>Username</label>
      <input v-model="username" type="text" required/>
    </div>
    <div>
      <label>Password</label>
      <input v-model="password" type="password" required/>
    </div>
    <button type="submit">Login</button>
    <p v-if="error" style='color: red'> {{error}} </p>
  </form>
</template>


<script setup>
import {ref} from "vue";
import api from "../api/axios";
import { useRouter } from 'vue-router'

const username = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()


const login = async function login() {
  try {
    const response = await api.post('auth/login', {
      username: username.value,
      password: password.value
    })
    localStorage.setItem('token', response.data.accessToken)
    localStorage.setItem('username', username.value)

    router.push('/')
    
  } catch (err) {
    error.value = 'Invalid credentials'
  }
}

</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  width: 100vh;
  margin: auto;
  gap: 10px;
}
</style>
