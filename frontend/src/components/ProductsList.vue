<template>
    <table class="product-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Quantity</th>
                <th class="actions-column">Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="product in products" :key="product.id">
                <td>{{ product.id }}</td>
                <td>{{ product.name }}</td>
                <td>{{ product.price }}</td>
                <td :class="getQuantityClass(product.quantity)">{{ product.quantity }}</td>
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
import auth from '../api/auth.js'
import api from '../api/axios.js'

const props = defineProps({
    refreshKey: Number
})

const products = ref([])

async function fetchProducts() {
    if (!auth.isLoggedIn()) {
        products.value = [];
        return
    }
    const token = auth.token
    const res = await api.get("/api/products", {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    });
    if (res.status != 200) {
        products.value = [];
        return
    }
    products.value = await res.data
}

async function deleteProduct(id) {
    if (!auth.isLoggedIn()) {
        products.value = [];
        return
    }
    const token = auth.token
    await api.delete(`/api/products/${id}`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    })
    await fetchProducts()
}

onMounted(fetchProducts)

// Function to determine CSS class for quantity display
function getQuantityClass(quantity) {
    if (quantity === 0) return 'quantity-out'
    if (quantity <= 5) return 'quantity-low'
    return 'quantity-normal'
}

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

.quantity-normal {
    color: #4CAF50;
    font-weight: 500;
}

.quantity-low {
    color: #FF9800;
    font-weight: bold;
}

.quantity-out {
    color: #F44336;
    font-weight: bold;
}
</style>
