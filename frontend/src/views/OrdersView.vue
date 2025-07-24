<template>
    <div class="orders-view">
        <div class="orders-container">
            <div class="orders-header">
                <div class="header-content">
                    <div class="title-section">
                        <svg class="page-icon" width="24" height="24" viewBox="0 0 24 24" fill="none">
                            <path d="M14 2H6C5.46957 2 4.96086 2.21071 4.58579 2.58579C4.21071 2.96086 4 3.46957 4 4V20C4 20.5304 4.21071 21.0391 4.58579 21.4142C4.96086 21.7893 5.46957 22 6 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V8L14 2Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M14 2V8H20" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M16 13H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M16 17H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M10 9H9H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                        <div class="title-content">
                            <h1 class="orders-title">Meus Pedidos</h1>
                            <p class="orders-subtitle">Acompanhe o histórico dos seus pedidos</p>
                        </div>
                    </div>
                </div>
            </div>
            
            <div v-if="orders.length === 0" class="empty-orders">
                <div class="empty-orders-icon">📋</div>
                <h2 class="empty-orders-title">Nenhum pedido encontrado</h2>
                <p class="empty-orders-text">Você ainda não fez nenhum pedido. Que tal começar suas compras?</p>
                <router-link to="/" class="continue-shopping-btn">
                    <div class="btn-icon-wrapper">
                        <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M9 22C9.55228 22 10 21.5523 10 21C10 20.4477 9.55228 20 9 20C8.44772 20 8 20.4477 8 21C8 21.5523 8.44772 22 9 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M20 22C20.5523 22 21 21.5523 21 21C21 20.4477 20.5523 20 20 20C19.4477 20 19 20.4477 19 21C19 21.5523 19.4477 22 20 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M1 1H5L7.68 14.39C7.77144 14.8504 8.02191 15.264 8.38755 15.5583C8.75318 15.8526 9.2107 16.009 9.68 16H19.4C19.8693 16.009 20.3268 15.8526 20.6925 15.5583C21.0581 15.264 21.3086 14.8504 21.4 14.39L23 6H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                    <span class="btn-text">Começar a Comprar</span>
                </router-link>
            </div>
            
            <div v-else class="orders-list">
                <div v-for="order in orders" :key="order.id" class="order-card">
                    <div class="order-header">
                        <div class="order-info">
                            <div class="order-title-row">
                                <h3 class="order-number">Pedido #{{ order.id }}</h3>
                                <span class="order-status" :class="getStatusClass(order.status)">
                                    {{ getStatusText(order.status) }}
                                </span>
                            </div>
                            <p class="order-date">{{ formatDate(order.createdAt) }}</p>
                        </div>
                        <div class="order-total">
                            <span class="total-label">Total</span>
                            <span class="total-value">R$ {{ order.total.toFixed(2) }}</span>
                        </div>
                    </div>
                    
                    <div class="order-items">
                        <h4 class="section-title">Itens do Pedido</h4>
                        <div class="items-list">
                            <div v-for="item in order.items" :key="item.productId" class="order-item">
                                <div class="item-info">
                                    <h5 class="item-name">{{ item.productName }}</h5>
                                    <div class="item-details">
                                        <span class="item-quantity">{{ item.quantity }}x</span>
                                        <span class="item-price">R$ {{ item.unitPrice.toFixed(2) }}</span>
                                    </div>
                                </div>
                                <div class="item-total">
                                    <span class="item-total-value">R$ {{ item.total.toFixed(2) }}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="order-details">
                        <div class="info-section shipping-info">
                            <div class="section-header">
                                <svg class="section-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                    <path d="M1 3H3L3.4 5M7 13H17L21 5H5.4M7 13L5.4 5M7 13L4.7 15.3C4.3 15.7 4.6 16.5 5.1 16.5H17M17 13V17C17 18.1 16.1 19 15 19H9C7.9 19 7 18.1 7 17V13M17 13H7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                                <h4 class="section-title">Entrega</h4>
                            </div>
                            <div class="section-content">
                                <p class="customer-name">{{ order.customerName }}</p>
                                <p class="address-line">{{ order.address.street }}, {{ order.address.number }}</p>
                                <p class="address-line">{{ order.address.neighborhood }} - {{ order.address.city }}/{{ order.address.state }}</p>
                                <p class="address-line">CEP: {{ order.address.zipCode }}</p>
                            </div>
                        </div>
                        
                        <div class="info-section payment-info">
                            <div class="section-header">
                                <svg class="section-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                    <rect x="1" y="4" width="22" height="16" rx="2" ry="2" stroke="currentColor" stroke-width="2"/>
                                    <line x1="1" y1="10" x2="23" y2="10" stroke="currentColor" stroke-width="2"/>
                                </svg>
                                <h4 class="section-title">Pagamento</h4>
                            </div>
                            <div class="section-content">
                                <p class="payment-method">{{ getPaymentMethodName(order.paymentMethod) }}</p>
                                <p v-if="order.paymentInstallments > 1" class="payment-installments">
                                    {{ order.paymentInstallments }}x de R$ {{ (order.total / order.paymentInstallments).toFixed(2) }}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserOrders } from '@/api/orders.js'
import auth from '@/api/auth.js'

const orders = ref([])
const loading = ref(false)
const error = ref('')

onMounted(() => {
    loadOrders()
})

async function loadOrders() {
    if (!auth.isLoggedIn()) {
        return
    }

    loading.value = true
    error.value = ''
    
    try {
        orders.value = await getUserOrders()
    } catch (err) {
        console.error('Error loading orders:', err)
        error.value = 'Erro ao carregar pedidos. Tente novamente.'
    } finally {
        loading.value = false
    }
}

function formatDate(dateString) {
    const date = new Date(dateString)
    return date.toLocaleDateString('pt-BR', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    })
}

function getStatusText(status) {
    const statusMap = {
        'PENDING': 'Pendente',
        'CONFIRMED': 'Confirmado',
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
        'CONFIRMED': 'status-confirmed',
        'PROCESSING': 'status-processing',
        'SHIPPED': 'status-shipped',
        'DELIVERED': 'status-delivered',
        'CANCELLED': 'status-cancelled'
    }
    return classMap[status] || 'status-pending'
}

function getPaymentMethodName(method) {
    const methodMap = {
        'CREDIT_CARD': 'Cartão de Crédito',
        'DEBIT_CARD': 'Cartão de Débito',
        'PIX': 'PIX',
        'BOLETO': 'Boleto Bancário'
    }
    return methodMap[method] || method
}
</script>

<style scoped>
.orders-view {
    width: 100%;
    min-height: 100vh;
    padding: var(--spacing-xl) 0;
}

.orders-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 var(--spacing-lg);
}

/* Page Header */
.orders-header {
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-xl);
    margin-bottom: var(--spacing-xl);
    backdrop-filter: blur(10px);
    transition: all var(--transition-normal);
}

.orders-header:hover {
    background: rgba(115, 115, 115, 0.06);
    border-color: var(--border-medium);
}

.header-content {
    display: flex;
    align-items: center;
    justify-content: center;
}

.title-section {
    display: flex;
    align-items: center;
    gap: var(--spacing-lg);
}

.page-icon {
    color: var(--text-muted);
    background: rgba(115, 115, 115, 0.15);
    padding: var(--spacing-md);
    border-radius: var(--radius-lg);
    border: 1px solid var(--border-light);
    transition: all var(--transition-fast);
}

.orders-header:hover .page-icon {
    color: var(--text-secondary);
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.title-content {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.orders-title {
    font-size: 1.75rem;
    font-weight: 700;
    color: var(--text-primary);
    margin: 0;
    line-height: 1.3;
}

.orders-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
    line-height: 1.4;
}

/* Empty State */
.empty-orders {
    text-align: center;
    padding: var(--spacing-2xl);
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    box-shadow: var(--shadow-sm);
    max-width: 500px;
    margin: 0 auto;
    animation: fadeIn 0.5s ease-out;
}

.empty-orders-icon {
    font-size: 4rem;
    margin-bottom: var(--spacing-lg);
    opacity: 0.6;
}

.empty-orders-title {
    color: var(--text-primary);
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: var(--spacing-md);
}

.empty-orders-text {
    color: var(--text-secondary);
    margin-bottom: var(--spacing-xl);
    line-height: 1.6;
}

.continue-shopping-btn {
    position: relative;
    display: inline-flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md) var(--spacing-lg);
    border: 1px solid rgba(16, 185, 129, 0.3);
    border-radius: var(--radius-md);
    background: rgba(16, 185, 129, 0.15);
    color: rgba(16, 185, 129, 0.9);
    text-decoration: none;
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
}

.continue-shopping-btn:hover {
    transform: translateY(-1px);
    background: rgba(16, 185, 129, 0.25);
    border-color: rgba(16, 185, 129, 0.5);
    color: rgba(16, 185, 129, 1);
    box-shadow: var(--shadow-sm);
}

.btn-icon-wrapper {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    background: rgba(16, 185, 129, 0.2);
    border: 1px solid rgba(16, 185, 129, 0.3);
    border-radius: var(--radius-md);
    transition: all var(--transition-fast);
}

.continue-shopping-btn:hover .btn-icon-wrapper {
    background: rgba(16, 185, 129, 0.3);
    border-color: rgba(16, 185, 129, 0.5);
}

.btn-icon {
    color: currentColor;
    transition: color var(--transition-fast);
}

.btn-text {
    color: currentColor;
    font-weight: 500;
}

/* Orders List */
.orders-list {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xl);
}

.order-card {
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-2xl);
    box-shadow: var(--shadow-sm);
    transition: all var(--transition-fast);
    animation: fadeIn 0.5s ease-out;
}

.order-card:hover {
    box-shadow: var(--shadow-md);
    transform: translateY(-2px);
    border-color: var(--border-medium);
}

/* Order Header */
.order-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: var(--spacing-xl);
    padding-bottom: var(--spacing-lg);
    border-bottom: 1px solid var(--border-light);
}

.order-info {
    flex: 1;
}

.order-title-row {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
    margin-bottom: var(--spacing-sm);
    flex-wrap: wrap;
}

.order-number {
    color: var(--text-primary);
    font-size: 1.25rem;
    font-weight: 600;
    margin: 0;
}

.order-status {
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-lg);
    font-size: 0.75rem;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    border: 1px solid transparent;
}

.status-pending {
    background: rgba(245, 158, 11, 0.15);
    color: rgba(245, 158, 11, 0.9);
    border-color: rgba(245, 158, 11, 0.3);
}

.status-confirmed {
    background: rgba(16, 185, 129, 0.15);
    color: rgba(16, 185, 129, 0.9);
    border-color: rgba(16, 185, 129, 0.3);
}

.status-processing {
    background: rgba(59, 130, 246, 0.15);
    color: rgba(59, 130, 246, 0.9);
    border-color: rgba(59, 130, 246, 0.3);
}

.status-shipped {
    background: rgba(147, 51, 234, 0.15);
    color: rgba(147, 51, 234, 0.9);
    border-color: rgba(147, 51, 234, 0.3);
}

.status-delivered {
    background: rgba(34, 197, 94, 0.15);
    color: rgba(34, 197, 94, 0.9);
    border-color: rgba(34, 197, 94, 0.3);
}

.status-cancelled {
    background: rgba(239, 68, 68, 0.15);
    color: rgba(239, 68, 68, 0.9);
    border-color: rgba(239, 68, 68, 0.3);
}

.order-date {
    color: var(--text-muted);
    font-size: 0.875rem;
    margin: 0;
}

.order-total {
    text-align: right;
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.total-label {
    color: var(--text-muted);
    font-size: 0.75rem;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    font-weight: 600;
}

.total-value {
    color: var(--text-primary);
    font-size: 1.5rem;
    font-weight: 700;
}

/* Order Items */
.order-items {
    margin-bottom: var(--spacing-xl);
}

.order-items .section-title {
    color: var(--text-secondary);
    font-size: 1rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-lg) 0;
    padding-bottom: var(--spacing-sm);
    border-bottom: 1px solid var(--border-light);
}

.items-list {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-md);
}

.order-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: var(--spacing-md);
    background: var(--tertiary-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    transition: all var(--transition-fast);
}

.order-item:hover {
    background: var(--elevated-bg);
    border-color: var(--border-medium);
}

.item-info {
    flex: 1;
}

.item-name {
    color: var(--text-primary);
    font-size: 1rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-xs) 0;
}

.item-details {
    display: flex;
    gap: var(--spacing-md);
    align-items: center;
}

.item-quantity {
    color: var(--text-muted);
    font-size: 0.875rem;
    font-weight: 500;
}

.item-price {
    color: var(--text-secondary);
    font-size: 0.875rem;
    font-weight: 500;
}

.item-total-value {
    color: var(--text-primary);
    font-size: 1rem;
    font-weight: 600;
}

/* Order Details */
.order-details {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: var(--spacing-xl);
    margin-top: var(--spacing-xl);
    padding-top: var(--spacing-lg);
    border-top: 1px solid var(--border-light);
}

.info-section {
    background: var(--tertiary-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
    transition: all var(--transition-fast);
}

.info-section:hover {
    background: var(--elevated-bg);
    border-color: var(--border-medium);
}

.section-header {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    margin-bottom: var(--spacing-md);
    padding-bottom: var(--spacing-sm);
    border-bottom: 1px solid var(--border-light);
}

.section-icon {
    color: var(--text-muted);
}

.section-header .section-title {
    color: var(--text-secondary);
    font-size: 1rem;
    font-weight: 600;
    margin: 0;
}

.section-content {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.customer-name {
    color: var(--text-primary);
    font-weight: 600;
    font-size: 0.875rem;
    margin: 0;
}

.address-line,
.payment-method,
.payment-installments {
    color: var(--text-muted);
    font-size: 0.875rem;
    margin: 0;
    line-height: 1.4;
}

.payment-method {
    color: var(--text-secondary);
    font-weight: 500;
}

/* Responsive Design */
@media (max-width: 1024px) {
    .orders-container {
        padding: 0 var(--spacing-md);
    }
    
    .order-details {
        grid-template-columns: 1fr;
        gap: var(--spacing-lg);
    }
}

@media (max-width: 768px) {
    .orders-view {
        padding: var(--spacing-lg) 0;
    }
    
    .orders-header {
        padding: var(--spacing-lg);
        margin-bottom: var(--spacing-lg);
    }
    
    .title-section {
        gap: var(--spacing-md);
    }
    
    .orders-title {
        font-size: 1.5rem;
    }
    
    .order-card {
        padding: var(--spacing-xl);
    }
    
    .order-header {
        flex-direction: column;
        gap: var(--spacing-md);
        align-items: stretch;
    }
    
    .order-total {
        text-align: left;
        flex-direction: row;
        align-items: center;
        gap: var(--spacing-md);
    }
    
    .order-title-row {
        flex-direction: column;
        align-items: flex-start;
        gap: var(--spacing-sm);
    }
    
    .order-item {
        flex-direction: column;
        align-items: stretch;
        gap: var(--spacing-sm);
        text-align: left;
    }
    
    .item-details {
        justify-content: space-between;
    }
    
    .item-total {
        text-align: right;
    }
}

@media (max-width: 640px) {
    .title-section {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }
    
    .empty-orders {
        padding: var(--spacing-xl);
        margin: 0 var(--spacing-md);
    }
    
    .order-card {
        padding: var(--spacing-lg);
    }
    
    .orders-list {
        gap: var(--spacing-lg);
    }
}

@media (max-width: 480px) {
    .orders-header {
        padding: var(--spacing-md);
    }
    
    .orders-title {
        font-size: 1.25rem;
    }
    
    .orders-subtitle {
        font-size: 0.8rem;
    }
    
    .order-number {
        font-size: 1.125rem;
    }
    
    .total-value {
        font-size: 1.25rem;
    }
}

@keyframes fadeIn {
    from {
        opacity: 0;
        transform: translateY(20px);
    }
    to {
        opacity: 1;
        transform: translateY(0);
    }
}
</style>