<template>
    <table class="category-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th class="actions-column">Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="cat in categories" :key="cat.id">
                <td>{{ cat.id }}</td>
                <td>{{ cat.name }}</td>
                <td class="actions-column">
                    <div class="actions">
                        <button @click="$emit('edit', cat.id)">✏️</button>
                        <button @click="deleteCategory(cat.id)">🗑</button>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import auth from "../api/auth.js"
import api from "../api/axios";
const emit = defineEmits(['edit', 'refresh'])

const categories = ref([])

async function fetchCategories() {
    if (!auth.isLoggedIn()) {
        categories.value = [];
        return
    }
    const token = auth.state.token
    const res = await api.get("/api/categories", {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    });
    if (res.status != 200) {
        categories.value = [];
        return
    }
    categories.value = await res.data
}

async function deleteCategory(id) {
    if (!auth.isLoggedIn()) {
        categories.value = [];
        return
    }
    const token = auth.state.token
    await api.delete(`/api/categories/${id}`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    })
    await fetchCategories()
}

onMounted(fetchCategories)


const props = defineProps({
    refreshKey: Number
});
watch(() => props.refreshKey, () => {
    fetchCategories()
});
</script>

<style scoped>
.category-table {
    width: 100%;
    border-collapse: collapse;
    table-layout: auto;
}

.category-table th,
.category-table td {
    padding: 0.5rem 1rem;
    border: 1px solid #ccc;
    text-align: left;
}

.category-table th.actions-column,
.category-table td.actions-column {
    width: 1%;
    /* shrink to fit content */
    text-align: right;
    white-space: nowrap;
}

.actions {
    display: flex;
    justify-content: flex-end;
    gap: 0.5rem;
}

.actions button {
    padding: 0.3rem 0.6rem;
    font-size: 0.9rem;
}
</style>
