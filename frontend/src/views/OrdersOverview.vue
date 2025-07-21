<template>
    <div class="orders-overview-page">
        <div class="page-header">
            <div class="header-content">
                <div class="title-section">
                    <svg class="page-icon" width="24" height="24" viewBox="0 0 24 24" fill="none">
                        <path d="M9 11H15M9 15H15M17 21H7C5.89543 21 5 20.1046 5 19V5C5 3.89543 5.89543 3 7 3H12.5858C12.851 3 13.1054 3.10536 13.2929 3.29289L19.7071 9.70711C19.8946 9.89464 20 10.149 20 10.4142V19C20 20.1046 19.1046 21 18 21H17Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <div class="title-content">
                        <h1 class="page-title">Visão Geral de Pedidos</h1>
                        <p class="page-subtitle">Monitore e gerencie todos os pedidos dos clientes</p>
                    </div>
                </div>
                
                <div class="header-stats">
                    <div class="stat-card">
                        <div class="stat-icon pending">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                                <path d="M12 6V12L16 14" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-number">{{ getOrderCountByStatus('PENDING') }}</span>
                            <span class="stat-label">Pendentes</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon processing">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-number">{{ getOrderCountByStatus('PROCESSING') }}</span>
                            <span class="stat-label">Processando</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon shipped">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M1 3H5L7.68 14.39C7.77144 14.8504 8.02191 15.264 8.38755 15.5583C8.75318 15.8526 9.2107 16.009 9.68 16H19.4C19.8693 16.009 20.3268 15.8526 20.6925 15.5583C21.0581 15.264 21.3086 14.8504 21.4 14.39L23 6H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M16 21V3M21 16L16 21L11 16" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-number">{{ getOrderCountByStatus('SHIPPED') }}</span>
                            <span class="stat-label">Enviados</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon delivered">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M20 6L9 17L4 12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-number">{{ getOrderCountByStatus('DELIVERED') }}</span>
                            <span class="stat-label">Entregues</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="page-content">
            <div class="filters-section" v-if="orders.length > 0">
                <div class="filters-content">
                    <div class="filter-group">
                        <label class="filter-label">
                            <svg class="filter-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M22 3H2L10 12.46V19L14 21V12.46L22 3Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            Filtrar por Status
                        </label>
                        <select v-model="statusFilter" class="filter-select">
                            <option value="">All</option>
                            <option value="PENDING">Pendign</option>
                            <option value="PROCESSING">Processing</option>
                            <option value="SHIPPED">Shipped</option>
                            <option value="DELIVERED">Delivered</option>
                            <option value="CANCELLED">Cancelled</option>
                        </select>
                    </div>
                    
                    <div class="filter-group">
                        <label class="filter-label">
                            <svg class="filter-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <rect x="3" y="4" width="18" height="18" rx="2" ry="2" stroke="currentColor" stroke-width="2"/>
                                <line x1="16" y1="2" x2="16" y2="6" stroke="currentColor" stroke-width="2"/>
                                <line x1="8" y1="2" x2="8" y2="6" stroke="currentColor" stroke-width="2"/>
                                <line x1="3" y1="10" x2="21" y2="10" stroke="currentColor" stroke-width="2"/>
                            </svg>
                            Ordenar por Data
                        </label>
                        <select v-model="sortOrder" class="filter-select">
                            <option value="desc"> More recent first</option>
                            <option value="asc">More older first</option>
                        </select>
                    </div>
                    
                    <div class="results-summary">
                        <span class="results-count">
                            {{ filteredOrders.length }} de {{ orders.length }} pedido{{ filteredOrders.length !== 1 ? 's' : '' }}
                        </span>
                    </div>
                </div>
            </div>
            
            <OrdersList 
                ref="ordersListRef" 
                @orders-loaded="handleOrdersLoaded" 
                :status-filter="statusFilter" 
                :sort-order="sortOrder" 
            />
        </div>
    </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import OrdersList from '../components/OrdersList.vue'

const orders = ref([])
const statusFilter = ref('')
const sortOrder = ref('desc')
const ordersListRef = ref(null)

const filteredOrders = computed(() => {
    let filtered = [...orders.value]
    
    // Filter by status
    if (statusFilter.value) {
        filtered = filtered.filter(order => order.status === statusFilter.value)
    }
    
    // Sort by date
    filtered.sort((a, b) => {
        const dateA = new Date(a.createdAt || 0)
        const dateB = new Date(b.createdAt || 0)
        
        if (sortOrder.value === 'desc') {
            return dateB - dateA
        } else {
            return dateA - dateB
        }
    })
    
    return filtered
})

function handleOrdersLoaded(loadedOrders) {
    orders.value = loadedOrders
}

function getOrderCountByStatus(status) {
    return orders.value.filter(order => order.status === status).length
}
</script>

<style scoped>
.orders-overview-page {
    width: 100%;
    max-width: 1600px;
    margin: 0 auto;
    padding: var(--spacing-xl);
}

/* Page Header */
.page-header {
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-xl);
    margin-bottom: var(--spacing-xl);
    backdrop-filter: blur(10px);
    transition: all var(--transition-normal);
}

.page-header:hover {
    background: rgba(115, 115, 115, 0.06);
    border-color: var(--border-medium);
}

.header-content {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: var(--spacing-xl);
}

.title-section {
    display: flex;
    align-items: center;
    gap: var(--spacing-lg);
    flex: 1;
}

.page-icon {
    color: var(--text-muted);
    background: rgba(115, 115, 115, 0.15);
    padding: var(--spacing-md);
    border-radius: var(--radius-lg);
    border: 1px solid var(--border-light);
    transition: all var(--transition-fast);
}

.page-header:hover .page-icon {
    color: var(--text-secondary);
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.title-content {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.page-title {
    font-size: 1.75rem;
    font-weight: 700;
    color: var(--text-primary);
    margin: 0;
    line-height: 1.3;
}

.page-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
    line-height: 1.4;
}

/* Header Stats */
.header-stats {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: var(--spacing-md);
    min-width: 400px;
}

.stat-card {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md);
    background: rgba(115, 115, 115, 0.05);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    transition: all var(--transition-fast);
}

.stat-card:hover {
    background: rgba(115, 115, 115, 0.1);
    border-color: var(--border-medium);
    transform: translateY(-2px);
}

.stat-icon {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 32px;
    height: 32px;
    border-radius: var(--radius-md);
    border: 1px solid;
    transition: all var(--transition-fast);
}

.stat-icon.pending {
    background: rgba(245, 158, 11, 0.1);
    color: rgba(245, 158, 11, 0.9);
    border-color: rgba(245, 158, 11, 0.3);
}

.stat-icon.processing {
    background: rgba(59, 130, 246, 0.1);
    color: rgba(59, 130, 246, 0.9);
    border-color: rgba(59, 130, 246, 0.3);
}

.stat-icon.shipped {
    background: rgba(139, 69, 19, 0.1);
    color: rgba(139, 69, 19, 0.9);
    border-color: rgba(139, 69, 19, 0.3);
}

.stat-icon.delivered {
    background: rgba(16, 185, 129, 0.1);
    color: rgba(16, 185, 129, 0.9);
    border-color: rgba(16, 185, 129, 0.3);
}

.stat-content {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.stat-number {
    font-size: 1.25rem;
    font-weight: 700;
    color: var(--text-primary);
    line-height: 1;
}

.stat-label {
    font-size: 0.75rem;
    color: var(--text-muted);
    font-weight: 500;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

/* Filters Section */
.filters-section {
    background: rgba(115, 115, 115, 0.03);
    backdrop-filter: blur(10px);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
    margin-bottom: var(--spacing-lg);
    transition: all var(--transition-fast);
}

.filters-section:hover {
    background: rgba(115, 115, 115, 0.06);
    border-color: var(--border-medium);
}

.filters-content {
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
    min-width: 180px;
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

.results-summary {
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

/* Page Content */
.page-content {
    width: 100%;
}

/* Responsive Design */
@media (max-width: 1200px) {
    .header-stats {
        grid-template-columns: repeat(2, 1fr);
        min-width: auto;
    }
}

@media (max-width: 768px) {
    .orders-overview-page {
        padding: var(--spacing-md);
    }
    
    .header-content {
        flex-direction: column;
        align-items: stretch;
        gap: var(--spacing-lg);
    }
    
    .title-section {
        gap: var(--spacing-md);
    }
    
    .page-title {
        font-size: 1.5rem;
    }
    
    .header-stats {
        grid-template-columns: repeat(2, 1fr);
        gap: var(--spacing-sm);
    }
    
    .stat-card {
        padding: var(--spacing-sm);
    }
    
    .stat-number {
        font-size: 1.125rem;
    }
    
    .filters-content {
        flex-direction: column;
        align-items: stretch;
        gap: var(--spacing-md);
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
    
    .results-summary {
        justify-content: center;
    }
}

@media (max-width: 640px) {
    .title-section {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }
    
    .page-icon {
        margin-bottom: var(--spacing-sm);
    }
    
    .header-stats {
        grid-template-columns: 1fr;
    }
}

@media (max-width: 480px) {
    .page-header {
        padding: var(--spacing-lg);
    }
    
    .page-title {
        font-size: 1.25rem;
    }
    
    .page-subtitle {
        font-size: 0.8rem;
    }
    
    .stat-card {
        padding: var(--spacing-xs);
    }
    
    .stat-icon {
        width: 28px;
        height: 28px;
    }
    
    .stat-number {
        font-size: 1rem;
    }
    
    .stat-label {
        font-size: 0.7rem;
    }
    
    .filters-section {
        padding: var(--spacing-md);
    }
}
</style>
