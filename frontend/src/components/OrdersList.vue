<template>
    <div class="orders-container">
        <div v-if="orders.length === 0" class="empty-state">
            <div class="empty-icon">
                <svg width="64" height="64" viewBox="0 0 24 24" fill="none">
                    <path d="M9 11H15M9 15H15M17 21H7C5.89543 21 5 20.1046 5 19V5C5 3.89543 5.89543 3 7 3H12.5858C12.851 3 13.1054 3.10536 13.2929 3.29289L19.7071 9.70711C19.8946 9.89464 20 10.149 20 10.4142V19C20 20.1046 19.1046 21 18 21H17Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </div>
            <p class="empty-text">Nenhum pedido encontrado</p>
            <p class="empty-subtitle">Os pedidos dos clientes aparecerão aqui</p>
        </div>

        <div v-else class="table-container">
            <div class="table-wrapper">
                <table class="orders-table">
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
                            <th class="th-customer">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                                    </svg>
                                    Cliente
                                </div>
                            </th>
                            <th class="th-date">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <rect x="3" y="4" width="18" height="18" rx="2" ry="2" stroke="currentColor" stroke-width="2"/>
                                        <line x1="16" y1="2" x2="16" y2="6" stroke="currentColor" stroke-width="2"/>
                                        <line x1="8" y1="2" x2="8" y2="6" stroke="currentColor" stroke-width="2"/>
                                        <line x1="3" y1="10" x2="21" y2="10" stroke="currentColor" stroke-width="2"/>
                                    </svg>
                                    Data do Pedido
                                </div>
                            </th>
                            <th class="th-items">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M9 22C9.55228 22 10 21.5523 10 21C10 20.4477 9.55228 20 9 20C8.44772 20 8 20.4477 8 21C8 21.5523 8.44772 22 9 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path d="M20 22C20.5523 22 21 21.5523 21 21C21 20.4477 20.5523 20 20 20C19.4477 20 19 20.4477 19 21C19 21.5523 19.4477 22 20 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path d="M1 1H5L7.68 14.39C7.77144 14.8504 8.02191 15.264 8.38755 15.5583C8.75318 15.8526 9.2107 16.009 9.68 16H19.4C19.8693 16.009 20.3268 15.8526 20.6925 15.5583C21.0581 15.264 21.3086 14.8504 21.4 14.39L23 6H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    Itens
                                </div>
                            </th>
                            <th class="th-total">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M12 1V23" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path d="M17 5H9.5C8.57174 5 7.6815 5.36875 7.02513 6.02513C6.36875 6.6815 6 7.57174 6 8.5C6 9.42826 6.36875 10.3185 7.02513 10.9749C7.6815 11.6312 8.57174 12 9.5 12H14.5C15.4283 12 16.3185 12.3687 16.9749 13.0251C17.6312 13.6815 18 14.5717 18 15.5C18 16.4283 17.6312 17.3185 16.9749 17.9749C16.3185 18.6312 15.4283 19 14.5 19H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    Total
                                </div>
                            </th>
                            <th class="th-status">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <circle cx="12" cy="12" r="3" stroke="currentColor" stroke-width="2"/>
                                        <path d="M12 1V4" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M12 20V23" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M4.22 4.22L5.64 5.64" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M18.36 18.36L19.78 19.78" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M1 12H4" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M20 12H23" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M4.22 19.78L5.64 18.36" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <path d="M18.36 5.64L19.78 4.22" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                    </svg>
                                    Status
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
                            v-for="(order, index) in orders" 
                            :key="order.id" 
                            class="table-row"
                            :class="{ 'row-even': index % 2 === 0, 'row-odd': index % 2 === 1 }"
                        >
                            <td class="td-id">
                                <span class="order-id">#{{ order.id }}</span>
                            </td>
                            <td class="td-customer">
                                <div class="customer-info">
                                    <div class="customer-avatar">
                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                            <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                            <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                                        </svg>
                                    </div>
                                    <div class="customer-details">
                                        <span class="customer-name">{{ order.user?.username || 'Cliente Desconhecido' }}</span>
                                        <span class="customer-email">{{ order.user?.email || 'Email não disponível' }}</span>
                                    </div>
                                </div>
                            </td>
                            <td class="td-date">
                                <div class="date-wrapper">
                                    <span class="order-date">{{ formatDate(order.createdAt) }}</span>
                                    <span class="order-time">{{ formatTime(order.createdAt) }}</span>
                                </div>
                            </td>
                            <td class="td-items">
                                <div class="items-summary">
                                    <span class="items-count">{{ getTotalItems(order.orderItems) }} item{{ getTotalItems(order.orderItems) !== 1 ? 's' : '' }}</span>
                                    <div class="items-preview" v-if="order.orderItems && order.orderItems.length > 0">
                                        <span class="item-name">{{ order.orderItems[0].product?.name || 'Produto' }}</span>
                                        <span v-if="order.orderItems.length > 1" class="more-items">+{{ order.orderItems.length - 1 }} mais</span>
                                    </div>
                                </div>
                            </td>
                            <td class="td-total">
                                <div class="price-wrapper">
                                    <span class="price-currency">R$</span>
                                    <span class="price-value">{{ formatPrice(order.total) }}</span>
                                </div>
                            </td>
                            <td class="td-status">
                                <span class="status-badge" :class="getStatusClass(order.status)">
                                    <svg width="12" height="12" viewBox="0 0 24 24" fill="none">
                                        <path v-if="order.status === 'PENDING'" d="M12 6V12L16 14" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                        <circle v-if="order.status === 'PENDING'" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                                        <path v-else-if="order.status === 'PROCESSING'" d="M12 2L15.09 8.26L22 9.27L17 14.14L18.18 21.02L12 17.77L5.82 21.02L7 14.14L2 9.27L8.91 8.26L12 2Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path v-else-if="order.status === 'SHIPPED'" d="M1 3H5L7.68 14.39C7.77144 14.8504 8.02191 15.264 8.38755 15.5583C8.75318 15.8526 9.2107 16.009 9.68 16H19.4C19.8693 16.009 20.3268 15.8526 20.6925 15.5583C21.0581 15.264 21.3086 14.8504 21.4 14.39L23 6H6M16 21V3M21 16L16 21L11 16" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path v-else-if="order.status === 'DELIVERED'" d="M20 6L9 17L4 12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path v-else-if="order.status === 'CANCELLED'" d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <circle v-else cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                                    </svg>
                                    {{ getStatusText(order.status) }}
                                </span>
                            </td>
                            <td class="td-actions">
                                <div class="action-buttons">
                                    <div class="status-selector">
                                        <select 
                                            :value="order.status" 
                                            @change="updateOrderStatus(order.id, $event.target.value)"
                                            class="status-select"
                                            :disabled="isUpdating === order.id"
                                        >
                                            <option value="PENDING">Pendente</option>
                                            <option value="PROCESSING">Processando</option>
                                            <option value="SHIPPED">Enviado</option>
                                            <option value="DELIVERED">Entregue</option>
                                            <option value="CANCELLED">Cancelado</option>
                                        </select>
                                        <div v-if="isUpdating === order.id" class="updating-indicator">
                                            <div class="loading-spinner"></div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import auth from '../api/auth.js'
import api from '../api/axios.js'

const props = defineProps({
    statusFilter: {
        type: String,
        default: ''
    },
    sortOrder: {
        type: String,
        default: 'desc'
    }
})

const emit = defineEmits(['orders-loaded'])

const allOrders = ref([])
const isUpdating = ref(null)

// Computed filtered orders
const orders = computed(() => {
    let filtered = [...allOrders.value]
    
    // Filter by status
    if (props.statusFilter) {
        filtered = filtered.filter(order => order.status === props.statusFilter)
    }
    
    // Sort by date
    filtered.sort((a, b) => {
        const dateA = new Date(a.createdAt || 0)
        const dateB = new Date(b.createdAt || 0)
        
        if (props.sortOrder === 'desc') {
            return dateB - dateA
        } else {
            return dateA - dateB
        }
    })
    
    return filtered
})

async function fetchOrders() {
    if (!auth.isLoggedIn()) {
        allOrders.value = []
        emit('orders-loaded', [])
        return
    }
    
    try {
        const token = auth.token
        const res = await api.get('/api/orders/admin/all', {
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token,
            },
        })
        
        if (res.status === 200) {
            allOrders.value = res.data
            emit('orders-loaded', res.data)
        }
    } catch (error) {
        console.error('Error fetching orders:', error)
        allOrders.value = []
        emit('orders-loaded', [])
    }
}

async function updateOrderStatus(orderId, newStatus) {
    if (!auth.isLoggedIn() || isUpdating.value) {
        return
    }
    
    try {
        isUpdating.value = orderId
        const token = auth.token
        
        await api.patch(`/api/orders/admin/${orderId}/status`, 
            { status: newStatus },
            {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + token,
                },
            }
        )
        
        // Update the local order status
        const orderIndex = allOrders.value.findIndex(order => order.id === orderId)
        if (orderIndex !== -1) {
            allOrders.value[orderIndex].status = newStatus
            emit('orders-loaded', allOrders.value)
        }
        
    } catch (error) {
        console.error('Error updating order status:', error)
        // Refresh the orders to get the current state
        await fetchOrders()
    } finally {
        isUpdating.value = null
    }
}

function formatDate(dateString) {
    if (!dateString) return 'Data inválida'
    const date = new Date(dateString)
    return date.toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    })
}

function formatTime(dateString) {
    if (!dateString) return ''
    const date = new Date(dateString)
    return date.toLocaleTimeString('pt-BR', {
        hour: '2-digit',
        minute: '2-digit'
    })
}

function formatPrice(price) {
    if (!price) return '0,00'
    return new Intl.NumberFormat('pt-BR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
    }).format(price)
}

function getTotalItems(orderItems) {
    if (!orderItems || !Array.isArray(orderItems)) return 0
    return orderItems.reduce((total, item) => total + (item.quantity || 0), 0)
}

function getStatusText(status) {
    const statusMap = {
        'PENDING': 'Pendente',
        'PROCESSING': 'Processando', 
        'SHIPPED': 'Enviado',
        'DELIVERED': 'Entregue',
        'CANCELLED': 'Cancelado'
    }
    return statusMap[status] || status
}

function getStatusClass(status) {
    const classMap = {
        'PENDING': 'status-pending',
        'PROCESSING': 'status-processing',
        'SHIPPED': 'status-shipped', 
        'DELIVERED': 'status-delivered',
        'CANCELLED': 'status-cancelled'
    }
    return classMap[status] || 'status-unknown'
}

onMounted(fetchOrders)
</script>

<style scoped>
.orders-container {
    width: 100%;
    max-width: 1400px;
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
.orders-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 0.875rem;
    min-width: 1200px;
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

.order-id {
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

.table-row:hover .order-id {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.td-customer {
    min-width: 220px;
}

.customer-info {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
}

.customer-avatar {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 36px;
    height: 36px;
    background: rgba(59, 130, 246, 0.1);
    border: 1px solid rgba(59, 130, 246, 0.2);
    border-radius: var(--radius-md);
    color: rgba(59, 130, 246, 0.8);
    transition: all var(--transition-fast);
}

.table-row:hover .customer-avatar {
    background: rgba(59, 130, 246, 0.15);
    border-color: rgba(59, 130, 246, 0.3);
    transform: scale(1.05);
}

.customer-details {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.customer-name {
    font-size: 0.875rem;
    font-weight: 600;
    color: var(--text-primary);
    line-height: 1.2;
}

.customer-email {
    font-size: 0.75rem;
    color: var(--text-muted);
    line-height: 1.2;
}

.td-date {
    width: 140px;
}

.date-wrapper {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.order-date {
    font-size: 0.875rem;
    font-weight: 600;
    color: var(--text-primary);
}

.order-time {
    font-size: 0.75rem;
    color: var(--text-muted);
}

.td-items {
    min-width: 200px;
}

.items-summary {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.items-count {
    font-size: 0.875rem;
    font-weight: 600;
    color: var(--text-primary);
}

.items-preview {
    display: flex;
    flex-direction: column;
    gap: 2px;
}

.item-name {
    font-size: 0.75rem;
    color: var(--text-secondary);
    line-height: 1.2;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    max-width: 180px;
}

.more-items {
    font-size: 0.7rem;
    color: var(--text-muted);
    font-style: italic;
}

.td-total {
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

.td-status {
    width: 140px;
    text-align: center;
}

.status-badge {
    display: inline-flex;
    align-items: center;
    gap: var(--spacing-xs);
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-md);
    font-size: 0.75rem;
    font-weight: 600;
    border: 1px solid;
    transition: all var(--transition-fast);
    min-width: 100px;
    justify-content: center;
}

.status-badge.status-pending {
    background: rgba(245, 158, 11, 0.1);
    color: rgba(245, 158, 11, 0.9);
    border-color: rgba(245, 158, 11, 0.3);
}

.status-badge.status-processing {
    background: rgba(59, 130, 246, 0.1);
    color: rgba(59, 130, 246, 0.9);
    border-color: rgba(59, 130, 246, 0.3);
}

.status-badge.status-shipped {
    background: rgba(139, 69, 19, 0.1);
    color: rgba(139, 69, 19, 0.9);
    border-color: rgba(139, 69, 19, 0.3);
}

.status-badge.status-delivered {
    background: rgba(16, 185, 129, 0.1);
    color: rgba(16, 185, 129, 0.9);
    border-color: rgba(16, 185, 129, 0.3);
}

.status-badge.status-cancelled {
    background: rgba(239, 68, 68, 0.1);
    color: rgba(239, 68, 68, 0.9);
    border-color: rgba(239, 68, 68, 0.3);
}

.table-row:hover .status-badge {
    transform: scale(1.02);
}

.td-actions {
    width: 160px;
    text-align: center;
}

.action-buttons {
    display: flex;
    justify-content: center;
}

.status-selector {
    position: relative;
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
}

.status-select {
    appearance: none;
    background: rgba(115, 115, 115, 0.1);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    padding: var(--spacing-xs) var(--spacing-sm);
    color: var(--text-primary);
    font-size: 0.75rem;
    font-weight: 500;
    cursor: pointer;
    transition: all var(--transition-fast);
    min-width: 120px;
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23737373' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpath d='M6 9l6 6 6-6'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right var(--spacing-xs) center;
    background-size: 14px;
    padding-right: calc(var(--spacing-sm) + 18px);
}

.status-select:hover {
    background-color: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
}

.status-select:focus {
    outline: none;
    border-color: var(--border-heavy);
    box-shadow: 0 0 0 3px rgba(115, 115, 115, 0.1);
}

.status-select:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.updating-indicator {
    display: flex;
    align-items: center;
    justify-content: center;
}

.loading-spinner {
    width: 16px;
    height: 16px;
    border: 2px solid transparent;
    border-top: 2px solid var(--text-muted);
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

/* Responsive Design */
@media (max-width: 1300px) {
    .orders-table {
        min-width: 1000px;
    }
    
    .td-customer {
        min-width: 180px;
    }
    
    .td-items {
        min-width: 160px;
    }
}

@media (max-width: 768px) {
    .orders-container {
        padding: var(--spacing-md);
    }
    
    .orders-table {
        min-width: 800px;
    }
    
    .table-header th {
        padding: var(--spacing-md) var(--spacing-sm);
        font-size: 0.75rem;
    }
    
    .table-row td {
        padding: var(--spacing-md) var(--spacing-sm);
    }
    
    .customer-info {
        gap: var(--spacing-sm);
    }
    
    .customer-avatar {
        width: 32px;
        height: 32px;
    }
    
    .status-select {
        min-width: 100px;
        font-size: 0.7rem;
    }
}

@media (max-width: 640px) {
    .orders-table {
        min-width: 600px;
    }
    
    .th-id, .td-id,
    .th-date, .td-date {
        display: none;
    }
    
    .td-customer {
        min-width: 140px;
    }
    
    .customer-name {
        font-size: 0.8rem;
    }
    
    .customer-email {
        display: none;
    }
}

@media (max-width: 480px) {
    .orders-container {
        padding: var(--spacing-sm);
    }
    
    .table-header th {
        padding: var(--spacing-sm);
    }
    
    .table-row td {
        padding: var(--spacing-sm);
    }
    
    .status-select {
        min-width: 80px;
        font-size: 0.65rem;
    }
}
</style>