<template>
  <div class="form-container">
    <h1>Create New Category</h1>
    <form @submit.prevent="createCategory">
      <div>
        <label>Name:</label>
        <input v-model="name" type="text" required />
      </div>
      <div>
        <label>Description:</label>
        <input v-model="description" type="text" />
      </div>
      <button type="submit">Save</button>
      <p v-if="message" class="success">{{ message }}</p>
      <p v-if="error" class="error">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '../../api/axios'

const router = useRouter()

const name = ref('')
const description = ref('')
const message = ref('')
const error = ref('')

const createCategory = async () => {
  try {
    await api.post('/categories', {
      name: name.value,
      description: description.value
    })
    message.value = 'Category created successfully!'
    setTimeout(() => router.push('/'), 1500)
  } catch (err) {
    error.value = 'Error creating category'
  }
}
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

form div {
  display: flex;
  flex-direction: column;
}

label {
  font-weight: bold;
  margin-bottom: 5px;
}

input {
  padding: 6px;
}

button {
  padding: 8px;
  cursor: pointer;
}

.success {
  color: green;
}

.error {
  color: red;
}
</style>
