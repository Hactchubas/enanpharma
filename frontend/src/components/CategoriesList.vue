<template>
  <div>
    <h2>Categories</h2>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="category in categories" :key="category.id">
          <td>{{ category.id }}</td>
          <td>{{ category.name }}</td>
          <td>
            <button @click="editCategory(category)">Update</button>
            <button @click="deleteCategory(category.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import api from '../api/axios'

const categories = ref([])

const state = reactive({
  token: localStorage.getItem("token") || null,
  user: localStorage.getItem("userId") || null,
  roles: localStorage.getItem("roles") || null,
});

const fetchCategories = async () => {
  try {
    const response = await api.get('/categories', {
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + state.token,
      }
    })
    categories.value = response.data
  } catch (error) {
    console.error('Error fetching categories', error)
  }
}

const deleteCategory = async (id) => {
  if (!confirm('Are you sure you want to delete this category?')) return

  try {
    await api.delete(`categories/${id}`)
    categories.value = categories.value.filter(c => c.id !== id)
  } catch (error) {
    console.error('Error deleting category', error)
  }
}

const editCategory = (category) => {
  // Emit event or route to edit page
  alert(`Edit category: ${category.name}`)
}

onMounted(fetchCategories)
</script>

<style scoped>
table {
  border-collapse: collapse;
  width: 100%;
}

th,
td {
  border: 1px solid #999;
  padding: 8px;
  text-align: left;
}

button {
  margin-right: 5px;
  padding: 4px 8px;
  cursor: pointer;
}
</style>
