<template>
    <div class="products-container">
        <div v-if="products.length === 0" class="empty-state">
            <div class="empty-icon">
                <svg width="64" height="64" viewBox="0 0 24 24" fill="none">
                    <path d="M20 7L12 3L4 7L12 11L20 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M4 7V17L12 21L20 17V7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </div>
            <p class="empty-text">Nenhum produto encontrado</p>
            <p class="empty-subtitle">Comece criando seu primeiro produto</p>
        </div>

        <div v-else class="table-container">
            <div class="table-wrapper">
                <table class="products-table">
                    <thead>
                        <tr class="table-header">
                            <th class="th-id">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M7 20L17 12L7 4V20Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    ID
                                </div>
                            </th>
                            <th class="th-image">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M23 19C23 19.5304 22.7893 20.0391 22.4142 20.4142C22.0391 20.7893 21.5304 21 21 21H3C2.46957 21 1.96086 20.7893 1.58579 20.4142C1.21071 20.0391 1 19.5304 1 19V8C1 7.46957 1.21071 6.96086 1.58579 6.58579C1.96086 6.21071 2.46957 6 3 6H7L9 3H15L17 6H21C21.5304 6 22.0391 6.21071 22.4142 6.58579C22.7893 6.96086 23 7.46957 23 8V19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <circle cx="12" cy="13" r="4" stroke="currentColor" stroke-width="2"/>
                                    </svg>
                                    Imagem
                                </div>
                            </th>
                            <th class="th-name">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M20 7L12 3L4 7L12 11L20 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path d="M4 7V17L12 21L20 17V7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    Nome do Produto
                                </div>
                            </th>
                            <th class="th-category">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                    </svg>
                                    Categoria
                                </div>
                            </th>
                            <th class="th-price">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M12 1V23" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path d="M17 5H9.5C8.57174 5 7.6815 5.36875 7.02513 6.02513C6.36875 6.6815 6 7.57174 6 8.5C6 9.42826 6.36875 10.3185 7.02513 10.9749C7.6815 11.6312 8.57174 12 9.5 12H14.5C15.4283 12 16.3185 12.3687 16.9749 13.0251C17.6312 13.6815 18 14.5717 18 15.5C18 16.4283 17.6312 17.3185 16.9749 17.9749C16.3185 18.6312 15.4283 19 14.5 19H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    Preço
                                </div>
                            </th>
                            <th class="th-quantity">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M3 9L12 2L21 9V20C21 20.5304 20.5304 21 20 21H4C3.44772 21 3 20.5304 3 20V9Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path d="M9 22V12H15V22" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    Estoque
                                </div>
                            </th>
                            <th class="th-actions">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M12 20H21M3 20H6.5M6.5 20C7.32843 20 8 19.3284 8 18.5C8 17.6716 7.32843 17 6.5 17C5.67157 17 5 17.6716 5 18.5C5 19.3284 5.67157 20 6.5 20ZM16 4H19M16 4C16 5.1046 16.8954 6 18 6C19.1046 6 20 5.1046 20 4C20 2.8954 19.1046 2 18 2C16.8954 2 16 2.8954 16 4ZM16 4H3M8 12H21M3 12H5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    Ações
                                </div>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr 
                            v-for="(product, index) in products" 
                            :key="product.id" 
                            class="table-row"
                            :class="{ 'row-even': index % 2 === 0, 'row-odd': index % 2 === 1 }"
                        >
                            <td class="td-id">
                                <span class="product-id">#{{ product.id }}</span>
                            </td>
                            <td class="td-image">
                                <div class="image-wrapper">
                                    <img 
                                        v-if="product.imageUrl" 
                                        :src="`http://localhost:8080${product.imageUrl}`" 
                                        :alt="product.name" 
                                        class="product-image" 
                                        loading="lazy"
                                    />
                                    <div v-else class="no-image-placeholder">
                                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                            <path d="M23 19C23 19.5304 22.7893 20.0391 22.4142 20.4142C22.0391 20.7893 21.5304 21 21 21H3C2.46957 21 1.96086 20.7893 1.58579 20.4142C1.21071 20.0391 1 19.5304 1 19V8C1 7.46957 1.21071 6.96086 1.58579 6.58579C1.96086 6.21071 2.46957 6 3 6H7L9 3H15L17 6H21C21.5304 6 22.0391 6.21071 22.4142 6.58579C22.7893 6.96086 23 7.46957 23 8V19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                            <circle cx="12" cy="13" r="4" stroke="currentColor" stroke-width="2"/>
                                        </svg>
                                        <span class="no-image-text">Sem imagem</span>
                                    </div>
                                </div>
                            </td>
                            <td class="td-name">
                                <div class="product-name-wrapper">
                                    <span class="product-name">{{ product.name }}</span>
                                    <span v-if="product.description" class="product-description">{{ product.description }}</span>
                                </div>
                            </td>
                            <td class="td-category">
                                <div class="category-wrapper">
                                    <span v-if="getCategoryName(product.categoryId)" class="category-badge">
                                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none">
                                            <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        </svg>
                                        {{ getCategoryName(product.categoryId) }}
                                    </span>
                                    <span v-else class="no-category">
                                        <svg width="12" height="12" viewBox="0 0 24 24" fill="none">
                                            <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                        Sem categoria
                                    </span>
                                </div>
                            </td>
                            <td class="td-price">
                                <div class="price-wrapper">
                                    <span class="price-currency">R$</span>
                                    <span class="price-value">{{ formatPrice(product.price) }}</span>
                                </div>
                            </td>
                            <td class="td-quantity">
                                <span class="quantity-badge" :class="getQuantityClass(product.quantity)">
                                    <svg width="14" height="14" viewBox="0 0 24 24" fill="none">
                                        <path v-if="product.quantity === 0" d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path v-else-if="product.quantity <= 5" d="M12 9V13M12 17H12.01M21 12C21 16.9706 16.9706 21 12 21C7.02944 21 3 16.9706 3 12C3 7.02944 7.02944 3 12 3C16.9706 3 21 7.02944 21 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path v-else d="M20 6L9 17L4 12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    {{ product.quantity }}
                                </span>
                            </td>
                            <td class="td-actions">
                                <div class="action-buttons">
                                    <button 
                                        @click="editProduct(product.id)" 
                                        class="action-btn edit-btn"
                                        aria-label="Editar produto"
                                        title="Editar produto"
                                    >
                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                            <path d="M11 4H4C3.46957 4 2.96086 4.21071 2.58579 4.58579C2.21071 4.96086 2 5.46957 2 6V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V13" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                            <path d="M18.5 2.49998C18.8978 2.10216 19.4374 1.87866 20 1.87866C20.5626 1.87866 21.1022 2.10216 21.5 2.49998C21.8978 2.89781 22.1213 3.43737 22.1213 3.99998C22.1213 4.56259 21.8978 5.10216 21.5 5.49998L12 15L8 16L9 12L18.5 2.49998Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                    </button>
                                    
                                    <button 
                                        @click="confirmDelete(product)" 
                                        class="action-btn delete-btn"
                                        aria-label="Excluir produto"
                                        title="Excluir produto"
                                    >
                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                            <path d="M3 6H5H21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                            <path d="M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6H19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Delete confirmation modal -->
        <div v-if="showDeleteModal" class="modal-overlay" @click="cancelDelete">
            <div class="delete-modal" @click.stop>
                <div class="modal-header">
                    <h3 class="modal-title">Confirmar Exclusão</h3>
                </div>
                <div class="modal-body">
                    <p class="modal-text">
                        Tem certeza que deseja excluir o produto 
                        <strong>"{{ productToDelete?.name }}"</strong>?
                    </p>
                    <p class="modal-warning">Esta ação não pode ser desfeita.</p>
                </div>
                <div class="modal-actions">
                    <button @click="cancelDelete" class="modal-btn cancel-btn">
                        Cancelar
                    </button>
                    <button @click="executeDelete" class="modal-btn confirm-btn">
                        Excluir
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import auth from '../api/auth.js'
import api from '../api/axios.js'

const emit = defineEmits(['edit'])
const props = defineProps({
    refreshKey: Number
})

const products = ref([])
const categories = ref([])
const showDeleteModal = ref(false)
const productToDelete = ref(null)

async function fetchCategories() {
    if (!auth.isLoggedIn()) {
        categories.value = [];
        return
    }
    try {
        const token = auth.token
        const res = await api.get("/categories", {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + token,
            },
        });
        if (res.status === 200) {
            categories.value = res.data
        }
    } catch (error) {
        console.error('Error fetching categories:', error)
        categories.value = []
    }
}

async function fetchProducts() {
    if (!auth.isLoggedIn()) {
        products.value = [];
        return
    }
    const token = auth.token
    const res = await api.get("/products", {
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

function editProduct(id = null){
  console.log(id)
  emit('edit', id)
}

function confirmDelete(product) {
    productToDelete.value = product
    showDeleteModal.value = true
}

function cancelDelete() {
    showDeleteModal.value = false
    productToDelete.value = null
}

async function executeDelete() {
    if (!productToDelete.value || !auth.isLoggedIn()) {
        return
    }
    
    const token = auth.token
    await api.delete(`/products/${productToDelete.value.id}`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    })
    
    await fetchProducts()
    cancelDelete()
}

function formatPrice(price) {
    return new Intl.NumberFormat('pt-BR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(price)
}

onMounted(async () => {
    await fetchCategories()
    await fetchProducts()
})

// Function to get category name by ID
function getCategoryName(categoryId) {
    if (!categoryId) return null
    const category = categories.value.find(cat => cat.id === categoryId)
    return category ? category.name : null
}

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
.products-container {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: var(--spacing-xl);
}

/* Empty State */
.empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: var(--spacing-2xl) var(--spacing-xl);
    text-align: center;
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(10px);
}

.empty-icon {
    color: var(--text-muted);
    opacity: 0.6;
    margin-bottom: var(--spacing-lg);
}

.empty-text {
    font-size: 1.125rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0 0 var(--spacing-sm) 0;
}

.empty-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
}

/* Table Container */
.table-container {
    background: rgba(115, 115, 115, 0.02);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    overflow: hidden;
    backdrop-filter: blur(10px);
    transition: all var(--transition-normal);
}

.table-container:hover {
    background: rgba(115, 115, 115, 0.04);
    border-color: var(--border-medium);
}

.table-wrapper {
    overflow-x: auto;
}

/* Modern Table */
.products-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 0.875rem;
}

.table-header {
    background: rgba(115, 115, 115, 0.08);
    border-bottom: 2px solid var(--border-medium);
}

.table-header th {
    padding: var(--spacing-lg) var(--spacing-md);
    text-align: left;
    font-weight: 600;
    color: var(--text-secondary);
    font-size: 0.8rem;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    border-bottom: none;
}

.header-content {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
}

.header-content svg {
    color: var(--text-muted);
    opacity: 0.7;
}

/* Table Rows */
.table-row {
    border-bottom: 1px solid var(--border-light);
    transition: all var(--transition-fast);
}

.table-row:hover {
    background: rgba(115, 115, 115, 0.06);
    border-bottom-color: var(--border-medium);
}

.table-row.row-even {
    background: rgba(115, 115, 115, 0.015);
}

.table-row.row-odd {
    background: rgba(115, 115, 115, 0.025);
}

.table-row:hover.row-even,
.table-row:hover.row-odd {
    background: rgba(115, 115, 115, 0.06);
}

/* Table Cells */
.table-row td {
    padding: var(--spacing-lg) var(--spacing-md);
    vertical-align: middle;
    border-bottom: none;
}

.td-id {
    width: 80px;
}

.product-id {
    display: inline-block;
    font-size: 0.75rem;
    font-weight: 600;
    color: var(--text-muted);
    background: rgba(115, 115, 115, 0.15);
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-sm);
    border: 1px solid var(--border-light);
    transition: all var(--transition-fast);
}

.table-row:hover .product-id {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.td-image {
    width: 100px;
    text-align: center;
}

.image-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 60px;
    height: 60px;
    margin: 0 auto;
    border-radius: var(--radius-lg);
    background: rgba(115, 115, 115, 0.05);
    border: 1px solid var(--border-light);
    overflow: hidden;
    transition: all var(--transition-fast);
}

.table-row:hover .image-wrapper {
    background: rgba(115, 115, 115, 0.1);
    border-color: var(--border-medium);
    transform: scale(1.05);
}

.product-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: all var(--transition-fast);
}

.no-image-placeholder {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    height: 100%;
    gap: var(--spacing-xs);
    color: var(--text-muted);
    opacity: 0.6;
}

.no-image-text {
    font-size: 0.65rem;
    font-weight: 500;
    text-align: center;
    line-height: 1;
}

.td-name {
    min-width: 180px;
    max-width: 250px;
}

.product-name-wrapper {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.product-name {
    font-size: 0.9rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0;
    line-height: 1.3;
    transition: color var(--transition-fast);
}

.table-row:hover .product-name {
    color: var(--text-primary);
}

.product-description {
    font-size: 0.75rem;
    color: var(--text-muted);
    line-height: 1.3;
    display: -webkit-box;
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    overflow: hidden;
}

.td-category {
    width: 140px;
    text-align: center;
}

.category-wrapper {
    display: flex;
    justify-content: center;
}

.category-badge {
    display: inline-flex;
    align-items: center;
    gap: var(--spacing-xs);
    padding: var(--spacing-xs) var(--spacing-sm);
    background: rgba(59, 130, 246, 0.08);
    border: 1px solid rgba(59, 130, 246, 0.2);
    border-radius: var(--radius-md);
    color: rgba(59, 130, 246, 0.9);
    font-size: 0.75rem;
    font-weight: 600;
    transition: all var(--transition-fast);
    max-width: 120px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.table-row:hover .category-badge {
    background: rgba(59, 130, 246, 0.12);
    border-color: rgba(59, 130, 246, 0.3);
    color: rgba(59, 130, 246, 1);
    transform: scale(1.02);
}

.no-category {
    display: inline-flex;
    align-items: center;
    gap: var(--spacing-xs);
    padding: var(--spacing-xs) var(--spacing-sm);
    background: rgba(115, 115, 115, 0.08);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    color: var(--text-muted);
    font-size: 0.75rem;
    font-weight: 500;
    font-style: italic;
    transition: all var(--transition-fast);
}

.table-row:hover .no-category {
    background: rgba(115, 115, 115, 0.12);
    border-color: var(--border-medium);
    color: var(--text-secondary);
}

.td-price {
    width: 120px;
}

.price-wrapper {
    display: flex;
    align-items: baseline;
    gap: var(--spacing-xs);
}

.price-currency {
    font-size: 0.75rem;
    color: var(--text-muted);
    font-weight: 500;
}

.price-value {
    font-size: 1rem;
    font-weight: 700;
    color: var(--text-primary);
}

.td-quantity {
    width: 100px;
    text-align: center;
}

.quantity-badge {
    display: inline-flex;
    align-items: center;
    gap: var(--spacing-xs);
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-lg);
    font-size: 0.75rem;
    font-weight: 600;
    border: 1px solid;
    transition: all var(--transition-fast);
    min-width: 60px;
    justify-content: center;
}

.quantity-badge.quantity-normal {
    background: rgba(16, 185, 129, 0.15);
    color: rgba(16, 185, 129, 0.9);
    border-color: rgba(16, 185, 129, 0.3);
}

.quantity-badge.quantity-low {
    background: rgba(245, 158, 11, 0.15);
    color: rgba(245, 158, 11, 0.9);
    border-color: rgba(245, 158, 11, 0.3);
}

.quantity-badge.quantity-out {
    background: rgba(239, 68, 68, 0.15);
    color: rgba(239, 68, 68, 0.9);
    border-color: rgba(239, 68, 68, 0.3);
}

.table-row:hover .quantity-badge {
    transform: scale(1.05);
}

.td-actions {
    width: 120px;
    text-align: right;
}

/* Action Buttons */
.action-buttons {
    display: flex;
    gap: var(--spacing-sm);
    justify-content: flex-end;
}

.action-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 36px;
    height: 36px;
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    background: rgba(115, 115, 115, 0.08);
    color: var(--text-secondary);
    cursor: pointer;
    transition: all var(--transition-fast);
    text-decoration: none;
    backdrop-filter: blur(5px);
}

.action-btn:hover {
    background: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
    color: var(--text-primary);
    transform: translateY(-1px) scale(1.05);
    box-shadow: var(--shadow-sm);
}

.action-btn:active {
    transform: translateY(0) scale(1);
}

.edit-btn {
    background: rgba(59, 130, 246, 0.08);
    border-color: rgba(59, 130, 246, 0.2);
    color: rgba(59, 130, 246, 0.8);
}

.edit-btn:hover {
    background: rgba(59, 130, 246, 0.15);
    border-color: rgba(59, 130, 246, 0.4);
    color: rgba(59, 130, 246, 1);
    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
}

.delete-btn {
    background: rgba(239, 68, 68, 0.08);
    border-color: rgba(239, 68, 68, 0.2);
    color: rgba(239, 68, 68, 0.8);
}

.delete-btn:hover {
    background: rgba(239, 68, 68, 0.15);
    border-color: rgba(239, 68, 68, 0.4);
    color: rgba(239, 68, 68, 1);
    box-shadow: 0 4px 12px rgba(239, 68, 68, 0.2);
}

/* Modal Styles */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.7);
    backdrop-filter: blur(5px);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    animation: fadeIn 0.3s ease-out;
}

.delete-modal {
    background: rgba(26, 26, 26, 0.98);
    border: 1px solid var(--border-medium);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(20px);
    box-shadow: var(--shadow-xl);
    max-width: 400px;
    width: 90%;
    animation: slideUp 0.3s ease-out;
}

.modal-header {
    padding: var(--spacing-xl) var(--spacing-xl) 0;
}

.modal-title {
    font-size: 1.25rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0;
}

.modal-body {
    padding: var(--spacing-lg) var(--spacing-xl);
}

.modal-text {
    color: var(--text-secondary);
    font-size: 0.875rem;
    line-height: 1.5;
    margin: 0 0 var(--spacing-md) 0;
}

.modal-warning {
    color: var(--text-muted);
    font-size: 0.8rem;
    margin: 0;
    font-style: italic;
}

.modal-actions {
    display: flex;
    gap: var(--spacing-sm);
    padding: 0 var(--spacing-xl) var(--spacing-xl);
}

.modal-btn {
    flex: 1;
    padding: var(--spacing-md) var(--spacing-lg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    font-size: 0.875rem;
    font-weight: 600;
    cursor: pointer;
    transition: all var(--transition-fast);
    text-align: center;
}

.cancel-btn {
    background: rgba(115, 115, 115, 0.1);
    color: var(--text-secondary);
}

.cancel-btn:hover {
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
}

.confirm-btn {
    background: rgba(239, 68, 68, 0.9);
    border-color: rgba(239, 68, 68, 1);
    color: white;
}

.confirm-btn:hover {
    background: rgba(239, 68, 68, 1);
    transform: translateY(-1px);
    box-shadow: var(--shadow-sm);
}

/* Animations */
@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

@keyframes slideUp {
    from {
        opacity: 0;
        transform: translateY(20px) scale(0.95);
    }
    to {
        opacity: 1;
        transform: translateY(0) scale(1);
    }
}

/* Responsive Design */
@media (max-width: 768px) {
    .products-container {
        padding: var(--spacing-md);
    }
    
    .table-header th {
        padding: var(--spacing-md) var(--spacing-sm);
        font-size: 0.75rem;
    }
    
    .table-row td {
        padding: var(--spacing-md) var(--spacing-sm);
    }
    
    .td-id {
        width: 60px;
    }
    
    .td-image {
        width: 80px;
    }
    
    .image-wrapper {
        width: 50px;
        height: 50px;
    }
    
    .product-id {
        font-size: 0.7rem;
        padding: 2px var(--spacing-xs);
    }
    
    .product-name {
        font-size: 0.85rem;
    }
    
    .product-description {
        font-size: 0.7rem;
        -webkit-line-clamp: 1;
    }
    
    .price-value {
        font-size: 0.9rem;
    }
    
    .quantity-badge {
        font-size: 0.7rem;
        min-width: 50px;
    }
    
    .action-btn {
        width: 32px;
        height: 32px;
    }
    
    .action-buttons {
        gap: 4px;
    }
    
    .delete-modal {
        margin: var(--spacing-md);
    }
}

@media (max-width: 768px) {
    .td-category {
        width: 100px;
    }
    
    .category-badge,
    .no-category {
        font-size: 0.7rem;
        max-width: 90px;
    }
}

@media (max-width: 640px) {
    .th-id,
    .td-id,
    .th-image,
    .td-image,
    .th-category,
    .td-category {
        display: none;
    }
    
    .td-actions {
        width: 80px;
    }
    
    .action-btn {
        width: 28px;
        height: 28px;
    }
    
    .action-btn svg {
        width: 14px;
        height: 14px;
    }
}

@media (max-width: 480px) {
    .products-container {
        padding: var(--spacing-sm);
    }
    
    .table-header th {
        padding: var(--spacing-sm);
    }
    
    .table-row td {
        padding: var(--spacing-sm);
    }
    
    .product-name {
        font-size: 0.8rem;
    }
    
    .price-value {
        font-size: 0.85rem;
    }
    
    .quantity-badge {
        font-size: 0.65rem;
        min-width: 45px;
    }
    
    .action-buttons {
        flex-direction: column;
        gap: 2px;
    }
    
    .td-actions {
        width: 60px;
    }
}
</style>
