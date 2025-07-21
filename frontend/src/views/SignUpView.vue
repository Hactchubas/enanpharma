<template>
  <div class="container">
    <h1>Create Your Account</h1>
    <form @submit.prevent="handleSignUp" class="form-grid">
      <div class="form-group">
        <label>Username</label>
        <input v-model="form.username" required />
      </div>
      <div class="form-group">
        <label>Password</label>
        <input type="password" v-model="form.password" required />
      </div>
      <div class="form-group">
        <label>Email</label>
        <input v-model="form.email" required />
      </div>
      <div class="form-group">
        <label>First Name</label>
        <input v-model="form.firstName" />
      </div>
      <div class="form-group">
        <label>Last Name</label>
        <input v-model="form.lastName" />
      </div>
      <div class="form-group">
        <label>Address</label>
        <input v-model="form.address" />
      </div>
      <div class="form-actions">
        <button type="submit">Sign Up</button>
      </div>
    </form>
    <p v-if="error" class="error">{{ error }}</p>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import api from '../api/axios.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const error = ref('')

const form = reactive({
  id: '',
  username: '',
  password: '',
  email: '',
  firstName: '',
  lastName: '',
  address: '',
  roles: ["USER"],
})

const handleSignUp = async () => {
  try {
    await api.post('/api/auth/signup', form)
    alert("User created!")
    router.push('/login')
  } catch (err) {
    error.value = 'Signup failed. Username or email might already exist.'
  }
}
</script>

<style scoped>
.container {
  max-width: 600px;
  margin: auto;
  padding: 2rem;
  border-radius: 10px;
}

h1 {
  text-align: center;
  margin-bottom: 1.5rem;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
  align-items: start;
}

.form-group label {
  margin-bottom: 0.3rem;
  font-weight: bold;
}

.form-group input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
}

.form-actions {
  grid-column: 1 / -1;
  display: flex;
  justify-content: center;
  margin-top: 1rem;
}

button {
  padding: 0.6rem 1.5rem;
  font-size: 1rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background: #369f73;
}

.error {
  color: red;
  text-align: center;
  margin-top: 1rem;
}
</style>
