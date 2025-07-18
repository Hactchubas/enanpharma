<template>
    <div class="product-grid">
        <article class="product-card fade-in" v-for="product in products" :key="product.id">
            <div class="product-image-container">
                <img 
                    v-if="product.imageUrl" 
                    :src="`http://localhost:8080${product.imageUrl}`" 
                    :alt="product.name" 
                    class="product-image" 
                    loading="lazy"
                />
                <div v-else class="no-image-placeholder">
                    <div class="no-image-icon">📸</div>
                    <span class="no-image-text">Sem Imagem</span>
                </div>
                
                <div class="product-overlay">
                    <span class="stock-badge" :class="getStockStatusClass(product.quantity)">
                        {{ getStockStatusText(product.quantity) }}
                    </span>
                </div>
            </div>
            
            <div class="product-info">
                <header class="product-header">
                    <h3 class="product-title">{{ product.name }}</h3>
                    <div class="product-price">
                        <span class="price-currency">R$</span>
                        <span class="price-value">{{ formatPrice(product.price) }}</span>
                    </div>
                </header>
                
                <div class="product-actions">
                    <div class="cart-controls" v-if="product.quantity > 0">
                        <div class="quantity-controls" v-if="isInCart(product.id)">
                            <button 
                                @click="decreaseQuantity(product.id)" 
                                class="quantity-btn decrease-btn"
                                aria-label="Diminuir quantidade"
                            >
                                <span>−</span>
                            </button>
                            <span class="quantity-display">{{ getQuantityInCart(product.id) }}</span>
                            <button 
                                @click="increaseQuantity(product)" 
                                class="quantity-btn increase-btn"
                                aria-label="Aumentar quantidade"
                            >
                                <span>+</span>
                            </button>
                        </div>
                        <button 
                            v-else 
                            @click="addToCart(product)" 
                            class="add-to-cart-btn btn btn-success"
                        >
                            <span class="btn-icon">🛒</span>
                            <span class="btn-text">Adicionar</span>
                        </button>
                    </div>
                    
                    <div v-else class="out-of-stock-container">
                        <div class="out-of-stock-msg">
                            <span class="out-of-stock-icon">❌</span>
                            <span>Esgotado</span>
                        </div>
                    </div>
                </div>
            </div>
        </article>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/api/axios.js'
import { auth } from '@/api/auth.js'
import { useCart } from '@/composables/useCart.js'

const products = ref([])
const { addToCart: addProductToCart, isInCart, getQuantityInCart, updateQuantity } = useCart()

async function fetchProducts() {
    const res = await api.get('/api/products')

    products.value = res.data
}

// Funções do carrinho
function addToCart(product) {
    addProductToCart(product, 1)
}

function increaseQuantity(product) {
    const currentQuantity = getQuantityInCart(product.id)
    if (currentQuantity < product.quantity) {
        updateQuantity(product.id, currentQuantity + 1)
    }
}

function decreaseQuantity(productId) {
    const currentQuantity = getQuantityInCart(productId)
    updateQuantity(productId, currentQuantity - 1)
}

// Function to format price
function formatPrice(price) {
    return new Intl.NumberFormat('pt-BR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(price)
}

// Function to determine stock status text
function getStockStatusText(quantity) {
    if (quantity === 0) return 'Esgotado'
    if (quantity <= 5) return 'Últimas unidades'
    return 'Em estoque'
}

// Function to determine CSS class for stock status
function getStockStatusClass(quantity) {
    if (quantity === 0) return 'out-of-stock'
    if (quantity <= 5) return 'low-stock'
    return 'in-stock'
}

onMounted(fetchProducts)
</script>

<style scoped>
.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: var(--spacing-xl);
    padding: var(--spacing-xl);
    max-width: 1400px;
    margin: 0 auto;
}

.product-card {
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    overflow: hidden;
    box-shadow: var(--shadow-sm);
    transition: all var(--transition-normal);
    position: relative;
    animation-delay: calc(var(--index, 0) * 0.1s);
}

.product-card:hover {
    transform: translateY(-8px);
    box-shadow: var(--shadow-xl);
    border-color: var(--border-medium);
}

/* Image Container */
.product-image-container {
    position: relative;
    width: 100%;
    height: 200px;
    overflow: hidden;
    background: var(--tertiary-bg);
}

.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: transform var(--transition-slow);
}

.product-card:hover .product-image {
    transform: scale(1.05);
}

.no-image-placeholder {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, var(--tertiary-bg), var(--surface-bg));
    color: var(--text-muted);
    gap: var(--spacing-sm);
}

.no-image-icon {
    font-size: 2rem;
    opacity: 0.5;
}

.no-image-text {
    font-size: 0.875rem;
    font-weight: 500;
    opacity: 0.7;
}

/* Product Overlay */
.product-overlay {
    position: absolute;
    top: var(--spacing-sm);
    right: var(--spacing-sm);
    display: flex;
    gap: var(--spacing-sm);
}

.stock-badge {
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-md);
    font-size: 0.75rem;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.1);
}

.stock-badge.in-stock {
    background: rgba(16, 185, 129, 0.9);
    color: white;
}

.stock-badge.low-stock {
    background: rgba(245, 158, 11, 0.9);
    color: white;
}

.stock-badge.out-of-stock {
    background: rgba(239, 68, 68, 0.9);
    color: white;
}

/* Product Info */
.product-info {
    padding: var(--spacing-lg);
    display: flex;
    flex-direction: column;
    gap: var(--spacing-md);
}

.product-header {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-sm);
}

.product-title {
    font-size: 1.125rem;
    font-weight: 600;
    color: var(--text-primary);
    line-height: 1.4;
    margin: 0;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.product-price {
    display: flex;
    align-items: baseline;
    gap: var(--spacing-xs);
}

.price-currency {
    font-size: 0.875rem;
    color: var(--text-secondary);
    font-weight: 500;
}

.price-value {
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--accent-success);
    line-height: 1;
}

/* Product Actions */
.product-actions {
    margin-top: auto;
}

.cart-controls {
    display: flex;
    align-items: center;
    justify-content: center;
}

.quantity-controls {
    display: flex;
    align-items: center;
    background: var(--elevated-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-xs);
    gap: var(--spacing-sm);
    width: 100%;
    max-width: 160px;
}

.quantity-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 32px;
    height: 32px;
    border: none;
    border-radius: var(--radius-md);
    background: var(--surface-bg);
    color: var(--text-primary);
    font-size: 1rem;
    font-weight: 600;
    cursor: pointer;
    transition: all var(--transition-fast);
    border: 1px solid var(--border-light);
}

.quantity-btn:hover {
    background: var(--accent-primary);
    color: white;
    border-color: var(--accent-primary);
    transform: scale(1.05);
}

.decrease-btn {
    color: var(--accent-error);
}

.decrease-btn:hover {
    background: var(--accent-error);
    border-color: var(--accent-error);
}

.increase-btn {
    color: var(--accent-success);
}

.increase-btn:hover {
    background: var(--accent-success);
    border-color: var(--accent-success);
}

.quantity-display {
    color: var(--text-primary);
    font-weight: 600;
    font-size: 1rem;
    min-width: 24px;
    text-align: center;
    user-select: none;
}

.add-to-cart-btn {
    width: 100%;
    justify-content: center;
    font-size: 0.875rem;
    padding: var(--spacing-md) var(--spacing-lg);
    border-radius: var(--radius-lg);
}

.add-to-cart-btn .btn-icon {
    font-size: 1rem;
}

.add-to-cart-btn .btn-text {
    font-weight: 600;
}

/* Out of Stock */
.out-of-stock-container {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: var(--spacing-md);
}

.out-of-stock-msg {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    color: var(--accent-error);
    font-size: 0.875rem;
    font-weight: 600;
    padding: var(--spacing-sm) var(--spacing-md);
    background: rgba(239, 68, 68, 0.1);
    border: 1px solid rgba(239, 68, 68, 0.2);
    border-radius: var(--radius-md);
    text-align: center;
}

.out-of-stock-icon {
    font-size: 1rem;
}

/* Responsive Design */
@media (max-width: 1024px) {
    .product-grid {
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: var(--spacing-lg);
        padding: var(--spacing-lg);
    }
}

@media (max-width: 768px) {
    .product-grid {
        grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
        gap: var(--spacing-md);
        padding: var(--spacing-md);
    }
    
    .product-image-container {
        height: 180px;
    }
    
    .product-title {
        font-size: 1rem;
    }
    
    .price-value {
        font-size: 1.25rem;
    }
}

@media (max-width: 480px) {
    .product-grid {
        grid-template-columns: 1fr;
        padding: var(--spacing-sm);
    }
    
    .product-image-container {
        height: 160px;
    }
    
    .product-info {
        padding: var(--spacing-md);
    }
}

/* Animation for new products */
@keyframes slideInUp {
    from {
        opacity: 0;
        transform: translateY(30px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}

.product-card.fade-in {
    animation: slideInUp 0.6s ease-out forwards;
}
</style>
