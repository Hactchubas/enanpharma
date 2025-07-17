<template>
    <div class="product-grid">
        <div class="product-card" v-for="product in products" :key="product.id">
            <img v-if="product.image" :src="product.image" alt="Product image" class="product-image" />
            <div class="product-info">
                <h3>{{ product.name }}</h3>
                <p class="price">R$ {{ product.price.toFixed(2) }}</p>
                <p class="stock-status" :class="getStockStatusClass(product.quantity)">{{ getStockStatusText(product.quantity) }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api/axios.js'
import { auth } from '@/api/auth.js'

const products = ref([])

async function fetchProducts() {
    const res = await api.get('/api/products')

    products.value = res.data
}

// Function to determine stock status text
function getStockStatusText(quantity) {
    if (quantity === 0) return 'Out of Stock'
    if (quantity <= 5) return 'Almost Out of Stock'
    return 'In Stock'
}

// Function to determine CSS class for stock status
function getStockStatusClass(quantity) {
    if (quantity === 0) return 'out-of-stock'
    if (quantity <= 5) return 'almost-out'
    return 'in-stock'
}

onMounted(fetchProducts)
</script>

<style scoped>
.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
    gap: 1.5rem;
    padding: 1rem;
}

.product-card {
    border: 1px solid #ccc;
    border-radius: 12px;
    overflow: hidden;
    background: #333;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    transition: transform 0.2s ease;
}

.product-card:hover {
    transform: translateY(-4px);
}

.product-image {
    width: 100%;
    height: 160px;
    object-fit: cover;
}

.product-info {
    padding: 1rem;
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
}

.price {
    font-weight: bold;
    color: whitesmoke;
}

.stock-status {
    font-size: 0.9rem;
    font-weight: 500;
}

.in-stock {
    color: #4CAF50;
}

.almost-out {
    color: #FF9800;
}

.out-of-stock {
    color: #F44336;
}
</style>
