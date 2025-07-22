<template>
    <div class="products-container">
        <!-- Filter Bar -->
        <div class="filter-bar">
            <div class="filter-section">
                <div class="filter-group">
                    <label class="filter-label">
                        <svg class="filter-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                        </svg>
                        Categoria
                    </label>
                    <select v-model="selectedCategory" class="filter-select" @change="filterProducts">
                        <option value="">Todas as categorias</option>
                        <option v-for="category in categories" :key="category.id" :value="category.id">
                            {{ category.name }}
                        </option>
                    </select>
                </div>
                
                <div class="filter-results">
                    <span class="results-count">{{ filteredProducts.length }} produto{{ filteredProducts.length !== 1 ? 's' : '' }}</span>
                </div>
            </div>
        </div>
        
        <!-- Products Grid -->
        <div class="product-grid">
        <article class="product-card fade-in" v-for="product in filteredProducts" :key="product.id">
            <div class="product-image-container">
                <img 
                    v-if="product.imageUrl" 
                    :src="`http://localhost:8080${product.imageUrl}`" 
                    :alt="product.name" 
                    class="product-image" 
                    loading="lazy"
                />
                <div v-else class="no-image-placeholder">
                    <div class="no-image-icon">
                        <svg width="64" height="64" viewBox="0 0 24 24" fill="none">
                            <path d="M23 19C23 19.5304 22.7893 20.0391 22.4142 20.4142C22.0391 20.7893 21.5304 21 21 21H3C2.46957 21 1.96086 20.7893 1.58579 20.4142C1.21071 20.0391 1 19.5304 1 19V8C1 7.46957 1.21071 6.96086 1.58579 6.58579C1.96086 6.21071 2.46957 6 3 6H7L9 3H15L17 6H21C21.5304 6 22.0391 6.21071 22.4142 6.58579C22.7893 6.96086 23 7.46957 23 8V19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <circle cx="12" cy="13" r="4" stroke="currentColor" stroke-width="2"/>
                        </svg>
                    </div>
                    <span class="no-image-text">Sem Imagem</span>
                </div>
                
                <!-- Floating overlay with stock and price -->
                <div class="product-overlay">
                    <div class="overlay-top">
                        <span class="stock-badge" :class="getStockStatusClass(product.quantity)">
                            {{ getStockStatusText(product.quantity) }}
                        </span>
                    </div>
                    <div class="overlay-bottom">
                        <div class="price-tag">
                            <span class="price-currency">R$</span>
                            <span class="price-value">{{ formatPrice(product.price) }}</span>
                        </div>
                    </div>
                </div>

                <!-- Hover info panel -->
                <div class="hover-info">
                    <h3 class="product-title">{{ product.name }}</h3>
                    <div class="product-actions">
                        <div class="cart-controls" v-if="product.quantity > 0">
                            <div class="quantity-controls" v-if="isInCart(product.id)">
                                <button 
                                    @click="decreaseQuantity(product.id)" 
                                    class="quantity-btn decrease-btn"
                                    aria-label="Diminuir quantidade"
                                >
                                    <svg width="12" height="12" viewBox="0 0 24 24" fill="none">
                                        <path d="M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                    </svg>
                                </button>
                                <span class="quantity-display">{{ getQuantityInCart(product.id) }}</span>
                                <button 
                                    @click="increaseQuantity(product)" 
                                    class="quantity-btn increase-btn"
                                    aria-label="Aumentar quantidade"
                                >
                                    <svg width="12" height="12" viewBox="0 0 24 24" fill="none">
                                        <path d="M12 5V19" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                    </svg>
                                </button>
                            </div>
                            <button 
                                v-else 
                                @click="addToCart(product)" 
                                class="add-to-cart-btn"
                            >
                                <svg class="btn-icon" width="18" height="18" viewBox="0 0 24 24" fill="none">
                                    <path d="M9 22C9.55228 22 10 21.5523 10 21C10 20.4477 9.55228 20 9 20C8.44772 20 8 20.4477 8 21C8 21.5523 8.44772 22 9 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M20 22C20.5523 22 21 21.5523 21 21C21 20.4477 20.5523 20 20 20C19.4477 20 19 20.4477 19 21C19 21.5523 19.4477 22 20 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M1 1H5L7.68 14.39C7.77144 14.8504 8.02191 15.264 8.38755 15.5583C8.75318 15.8526 9.2107 16.009 9.68 16H19.4C19.8693 16.009 20.3268 15.8526 20.6925 15.5583C21.0581 15.264 21.3086 14.8504 21.4 14.39L23 6H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                                <span class="btn-text">Adicionar ao Carrinho</span>
                            </button>
                        </div>
                        
                        <div v-else class="out-of-stock-container">
                            <div class="out-of-stock-msg">
                                <svg class="out-of-stock-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                    <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                                    <path d="M15 9L9 15" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                    <path d="M9 9L15 15" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                </svg>
                                <span>Produto Esgotado</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </article>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import api from '@/api/axios.js'
import { auth } from '@/api/auth.js'
import { useCart } from '@/composables/useCart.js'

const products = ref([])
const categories = ref([])
const selectedCategory = ref('')
const { addToCart: addProductToCart, isInCart, getQuantityInCart, updateQuantity } = useCart()

// Computed property for filtered products
const filteredProducts = computed(() => {
    if (!selectedCategory.value) {
        return products.value
    }
    return products.value.filter(product => product.categoryId === selectedCategory.value)
})

async function fetchProducts() {
    const res = await api.get('/products')
    
    // Sort products by priority: low stock (1-5) first, then in stock (>5), then out of stock (0)
    products.value = res.data.sort((a, b) => {
        const getStockPriority = (quantity) => {
            if (quantity === 0) return 3 // Out of stock - lowest priority
            if (quantity <= 5) return 1 // Low stock - highest priority  
            return 2 // In stock - medium priority
        }
        
        const priorityA = getStockPriority(a.quantity)
        const priorityB = getStockPriority(b.quantity)
        
        // If same priority, sort by name alphabetically
        if (priorityA === priorityB) {
            return a.name.localeCompare(b.name)
        }
        
        return priorityA - priorityB
    })
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

async function fetchCategories() {
    try {
        const res = await api.get('/categories')
        categories.value = res.data
    } catch (error) {
        console.error('Error fetching categories:', error)
    }
}


onMounted(async () => {
    await Promise.all([fetchProducts(), fetchCategories()])
})
</script>

<style scoped>
.products-container {
    width: 100%;
    max-width: 1400px;
    margin: 0 auto;
}

/* Filter Bar */
.filter-bar {
    background: rgba(115, 115, 115, 0.03);
    backdrop-filter: blur(10px);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
    margin: var(--spacing-xl) var(--spacing-xl) var(--spacing-lg);
    transition: all var(--transition-fast);
}

.filter-bar:hover {
    background: rgba(115, 115, 115, 0.06);
    border-color: var(--border-medium);
}

.filter-section {
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
    gap: var(--spacing-md);
}

.filter-group {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
}

.filter-label {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    font-size: 0.875rem;
    font-weight: 500;
    color: var(--text-secondary);
    cursor: pointer;
}

.filter-icon {
    color: var(--text-muted);
}

.filter-select {
    appearance: none;
    background: rgba(115, 115, 115, 0.1);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    padding: var(--spacing-sm) var(--spacing-lg) var(--spacing-sm) var(--spacing-md);
    color: var(--text-primary);
    font-size: 0.875rem;
    font-weight: 500;
    cursor: pointer;
    transition: all var(--transition-fast);
    min-width: 200px;
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23737373' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpath d='M6 9l6 6 6-6'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right var(--spacing-sm) center;
    background-size: 16px;
}

.filter-select:hover {
    background-color: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
}

.filter-select:focus {
    outline: none;
    border-color: var(--border-heavy);
    box-shadow: 0 0 0 3px rgba(115, 115, 115, 0.1);
}

.filter-results {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
}

.results-count {
    font-size: 0.875rem;
    color: var(--text-muted);
    font-weight: 500;
    padding: var(--spacing-xs) var(--spacing-md);
    background: rgba(115, 115, 115, 0.08);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
}

.product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
    gap: var(--spacing-lg);
    padding: var(--spacing-xl);
    max-width: 1400px;
    margin: 0 auto;
}

.product-card {
    aspect-ratio: 4/5;
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    overflow: hidden;
    box-shadow: var(--shadow-sm);
    transition: all var(--transition-normal);
    position: relative;
    animation-delay: calc(var(--index, 0) * 0.1s);
    backdrop-filter: blur(10px);
    cursor: pointer;
}

.product-card:hover {
    transform: translateY(-6px) scale(1.02);
    box-shadow: var(--shadow-xl);
    border-color: var(--border-medium);
    background: rgba(115, 115, 115, 0.06);
}

/* Image Container - Takes up the full card */
.product-image-container {
    position: relative;
    width: 100%;
    height: 100%;
    overflow: hidden;
    background: linear-gradient(135deg, rgba(115, 115, 115, 0.05), rgba(115, 115, 115, 0.08));
}

.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: all var(--transition-slow);
    filter: grayscale(0.05) contrast(1.05) brightness(1.02);
}

.product-card:hover .product-image {
    transform: scale(1.06);
    filter: grayscale(0) contrast(1.15) brightness(1.1);
}

.no-image-placeholder {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: linear-gradient(135deg, rgba(115, 115, 115, 0.08), rgba(115, 115, 115, 0.12));
    color: var(--text-muted);
    gap: var(--spacing-md);
}

.no-image-icon {
    color: var(--text-muted);
    opacity: 0.4;
    transition: all var(--transition-fast);
}

.product-card:hover .no-image-icon {
    opacity: 0.6;
    transform: scale(1.1);
}

.no-image-text {
    font-size: 0.875rem;
    font-weight: 500;
    color: var(--text-muted);
    opacity: 0.6;
    transition: all var(--transition-fast);
}

.product-card:hover .no-image-text {
    opacity: 0.8;
}

/* Floating overlay elements */
.product-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding: var(--spacing-md);
    pointer-events: none;
    z-index: 2;
}

.overlay-top {
    display: flex;
    justify-content: flex-end;
}

.overlay-bottom {
    display: flex;
    justify-content: flex-start;
    align-items: flex-end;
}

.stock-badge {
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-lg);
    font-size: 0.7rem;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    backdrop-filter: blur(15px);
    border: 1px solid rgba(255, 255, 255, 0.15);
    transition: all var(--transition-fast);
}

.product-card:hover .stock-badge {
    transform: scale(1.05);
    backdrop-filter: blur(20px);
}

.stock-badge.in-stock {
    background: rgba(16, 185, 129, 0.2);
    color: white;
    border-color: rgba(16, 185, 129, 0.4);
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.stock-badge.low-stock {
    background: rgba(245, 158, 11, 0.2);
    color: white;
    border-color: rgba(245, 158, 11, 0.4);
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.stock-badge.out-of-stock {
    background: rgba(239, 68, 68, 0.2);
    color: white;
    border-color: rgba(239, 68, 68, 0.4);
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.price-tag {
    display: flex;
    align-items: baseline;
    gap: var(--spacing-xs);
    padding: var(--spacing-sm) var(--spacing-md);
    background: rgba(10, 10, 10, 0.8);
    backdrop-filter: blur(15px);
    border-radius: var(--radius-lg);
    border: 1px solid rgba(255, 255, 255, 0.1);
    transition: all var(--transition-fast);
}

.product-card:hover .price-tag {
    transform: scale(1.05);
    background: rgba(10, 10, 10, 0.9);
    backdrop-filter: blur(20px);
}

.price-currency {
    font-size: 0.75rem;
    color: var(--text-muted);
    font-weight: 500;
}

.price-value {
    font-size: 1.25rem;
    font-weight: 700;
    color: white;
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

/* Hover info panel - appears on hover */
.hover-info {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: linear-gradient(to top, rgba(10, 10, 10, 0.95) 0%, rgba(10, 10, 10, 0.8) 70%, transparent 100%);
    backdrop-filter: blur(20px);
    padding: var(--spacing-xl) var(--spacing-md) var(--spacing-md);
    transform: translateY(100%);
    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 3;
    pointer-events: none;
}

.product-card:hover .hover-info {
    transform: translateY(0);
    pointer-events: all;
}

.product-title {
    font-size: 1.125rem;
    font-weight: 600;
    color: white;
    line-height: 1.3;
    margin: 0 0 var(--spacing-md) 0;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

/* Product Actions in hover panel */
.product-actions {
    width: 100%;
}

.cart-controls {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
}

.quantity-controls {
    display: flex;
    align-items: center;
    background: rgba(255, 255, 255, 0.15);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: var(--radius-lg);
    padding: var(--spacing-xs);
    gap: var(--spacing-sm);
    width: 100%;
    max-width: 140px;
}

.quantity-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    border: none;
    border-radius: var(--radius-md);
    background: rgba(255, 255, 255, 0.2);
    color: white;
    cursor: pointer;
    transition: all var(--transition-fast);
    border: 1px solid rgba(255, 255, 255, 0.3);
}

.quantity-btn:hover {
    background: rgba(255, 255, 255, 0.3);
    border-color: rgba(255, 255, 255, 0.5);
    transform: scale(1.05);
}

.decrease-btn:hover {
    background: rgba(239, 68, 68, 0.8);
    border-color: rgba(239, 68, 68, 1);
    color: white;
}

.increase-btn:hover {
    background: rgba(16, 185, 129, 0.8);
    border-color: rgba(16, 185, 129, 1);
    color: white;
}

.quantity-display {
    color: white;
    font-weight: 600;
    font-size: 0.875rem;
    min-width: 24px;
    text-align: center;
    user-select: none;
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.add-to-cart-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: var(--spacing-sm);
    width: 100%;
    font-size: 0.875rem;
    font-weight: 600;
    padding: var(--spacing-md);
    border-radius: var(--radius-lg);
    background: rgba(16, 185, 129, 0.9);
    border: 1px solid rgba(16, 185, 129, 1);
    color: white;
    cursor: pointer;
    transition: all var(--transition-fast);
    text-decoration: none;
    backdrop-filter: blur(10px);
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
    box-shadow: 0 4px 12px rgba(16, 185, 129, 0.3);
}

.add-to-cart-btn:hover {
    background: rgba(16, 185, 129, 1);
    transform: translateY(-2px) scale(1.02);
    box-shadow: 0 8px 20px rgba(16, 185, 129, 0.4);
}

.add-to-cart-btn .btn-icon {
    color: white;
    drop-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.add-to-cart-btn .btn-text {
    font-weight: 600;
    color: white;
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

/* Out of Stock */
.out-of-stock-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
}

.out-of-stock-msg {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    color: white;
    font-size: 0.875rem;
    font-weight: 600;
    padding: var(--spacing-md);
    background: rgba(239, 68, 68, 0.9);
    border: 1px solid rgba(239, 68, 68, 1);
    border-radius: var(--radius-lg);
    text-align: center;
    width: 100%;
    justify-content: center;
    backdrop-filter: blur(10px);
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
    box-shadow: 0 4px 12px rgba(239, 68, 68, 0.3);
}

.out-of-stock-icon {
    color: white;
}

/* Responsive Design */
@media (max-width: 1200px) {
    .filter-bar {
        margin: var(--spacing-lg) var(--spacing-lg) var(--spacing-md);
    }
    
    .product-grid {
        grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
        gap: var(--spacing-md);
        padding: var(--spacing-lg);
    }
}

@media (max-width: 768px) {
    .filter-bar {
        margin: var(--spacing-md);
        padding: var(--spacing-md);
    }
    
    .filter-section {
        flex-direction: column;
        align-items: stretch;
    }
    
    .filter-group {
        flex-direction: column;
        align-items: stretch;
        gap: var(--spacing-sm);
    }
    
    .filter-select {
        min-width: auto;
        width: 100%;
    }
    
    .filter-results {
        justify-content: center;
    }
    
    .product-grid {
        grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
        gap: var(--spacing-md);
        padding: var(--spacing-md);
    }
    
    .product-card {
        aspect-ratio: 3/4;
    }
    
    .product-title {
        font-size: 1rem;
    }
    
    .price-value {
        font-size: 1.125rem;
    }
    
    .hover-info {
        padding: var(--spacing-lg) var(--spacing-md) var(--spacing-md);
    }
}

@media (max-width: 640px) {
    .product-grid {
        grid-template-columns: repeat(2, 1fr);
        gap: var(--spacing-sm);
        padding: var(--spacing-md);
    }
    
    .product-card {
        aspect-ratio: 3/4;
    }
    
    .product-title {
        font-size: 0.875rem;
        -webkit-line-clamp: 3;
    }
    
    .price-value {
        font-size: 1rem;
    }
    
    .stock-badge {
        font-size: 0.65rem;
        padding: 2px var(--spacing-xs);
    }
}

@media (max-width: 480px) {
    .product-grid {
        grid-template-columns: 1fr;
        padding: var(--spacing-sm);
        gap: var(--spacing-md);
    }
    
    .product-card {
        aspect-ratio: 4/3;
        max-width: 400px;
        margin: 0 auto;
    }
    
    .product-title {
        font-size: 1rem;
        -webkit-line-clamp: 2;
    }
    
    .hover-info {
        padding: var(--spacing-md);
    }
    
    .add-to-cart-btn .btn-text {
        font-size: 0.8rem;
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
