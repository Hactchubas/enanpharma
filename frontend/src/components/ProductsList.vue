<template>
    <table class="product-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th class="actions-column">Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="product in products" :key="product.id">
                <td>{{ product.id }}</td>
                <td>{{ product.name }}</td>
                <td>{{ product.price }}</td>
                <td class="actions-column">
                    <div class="actions">
                        <button @click="$emit('edit', product.id)">✏️</button>
                        <button @click="deleteProduct(product.id)">🗑</button>
                    </div>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'

const props = defineProps({
    refreshKey: Number
})

const products = ref([])

async function fetchProducts() {
    if (!auth.isLoggedIn()) {
        products.value = [];
        return
    }
    const token = auth.state.token
    const res = await api.get("/api/products", {
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

async function deleteProduct(id) {
    await fetch(`/api/products/delete/${id}`, { method: 'POST' })
    fetchProducts()
}

onMounted(fetchProducts)

watch(() => props.refreshKey, () => {
    fetchProducts()
})
</script>

<style scoped>
.product-table {
    width: 100%;
    border-collapse: collapse;
}

.product-table th,
.product-table td {
    padding: 0.5rem 1rem;
    border: 1px solid #ccc;
    text-align: left;
}

.product-table th.actions-column,
.product-table td.actions-column {
    width: 1%;
    text-align: right;
    white-space: nowrap;
}

.actions {
    display: flex;
    justify-content: flex-end;
    gap: 0.5rem;
}
</style>
