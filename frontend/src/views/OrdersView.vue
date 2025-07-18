<template>
    <div class="orders-container">
        <h1>Meus Pedidos</h1>
        
        <div v-if="orders.length === 0" class="empty-orders">
            <p>Você ainda não fez nenhum pedido.</p>
            <router-link to="/" class="shop-btn">Começar a Comprar</router-link>
        </div>
        
        <div v-else class="orders-list">
            <div v-for="order in orders" :key="order.id" class="order-card">
                <div class="order-header">
                    <div class="order-info">
                        <h3>Pedido #{{ order.id }}</h3>
                        <p class="order-date">{{ formatDate(order.createdAt) }}</p>
                        <span class="order-status" :class="getStatusClass(order.status)">
                            {{ getStatusText(order.status) }}
                        </span>
                    </div>
                    <div class="order-total">
                        <strong>R$ {{ order.total.toFixed(2) }}</strong>
                    </div>
                </div>
                
                <div class="order-items">
                    <div v-for="item in order.items" :key="item.productId" class="order-item">
                        <div class="item-info">
                            <h4>{{ item.productName }}</h4>
                            <p>Quantidade: {{ item.quantity }}</p>
                            <p>Preço unitário: R$ {{ item.unitPrice.toFixed(2) }}</p>
                        </div>
                        <div class="item-total">
                            R$ {{ item.total.toFixed(2) }}
                        </div>
                    </div>
                </div>
                
                <div class="order-details">
                    <div class="shipping-info">
                        <h4>Entrega</h4>
                        <p>{{ order.customerName }}</p>
                        <p>{{ order.address.street }}, {{ order.address.number }}</p>
                        <p>{{ order.address.neighborhood }} - {{ order.address.city }}/{{ order.address.state }}</p>
                        <p>CEP: {{ order.address.zipCode }}</p>
                    </div>
                    
                    <div class="payment-info">
                        <h4>Pagamento</h4>
                        <p>{{ getPaymentMethodName(order.paymentMethod) }}</p>
                        <p v-if="order.paymentInstallments > 1">
                            {{ order.paymentInstallments }}x de R$ {{ (order.total / order.paymentInstallments).toFixed(2) }}
                        </p>
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
.orders-container {
    max-width: 1000px;
    margin: 0 auto;
    padding: 20px;
    color: white;
}

.orders-container h1 {
    text-align: center;
    margin-bottom: 30px;
}

.empty-orders {
    text-align: center;
    padding: 60px 20px;
    color: #ccc;
}

.empty-orders p {
    font-size: 18px;
    margin-bottom: 20px;
}

.shop-btn {
    background: #4CAF50;
    color: white;
    padding: 12px 24px;
    border-radius: 6px;
    text-decoration: none;
    font-weight: 500;
}

.shop-btn:hover {
    background: #45a049;
}

.orders-list {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.order-card {
    background: #333;
    border-radius: 8px;
    padding: 25px;
    border: 1px solid #555;
}

.order-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    margin-bottom: 20px;
    padding-bottom: 15px;
    border-bottom: 1px solid #555;
}

.order-info h3 {
    margin: 0 0 5px 0;
    color: #4CAF50;
}

.order-date {
    margin: 0 0 8px 0;
    color: #ccc;
    font-size: 14px;
}

.order-status {
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: bold;
    text-transform: uppercase;
}

.status-pending {
    background: #FF9800;
    color: white;
}

.status-confirmed {
    background: #4CAF50;
    color: white;
}

.status-processing {
    background: #2196F3;
    color: white;
}

.status-shipped {
    background: #9C27B0;
    color: white;
}

.status-delivered {
    background: #4CAF50;
    color: white;
}

.status-cancelled {
    background: #f44336;
    color: white;
}

.order-total {
    font-size: 18px;
    color: #4CAF50;
}

.order-items {
    margin-bottom: 20px;
}

.order-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 0;
    border-bottom: 1px solid #444;
}

.order-item:last-child {
    border-bottom: none;
}

.item-info h4 {
    margin: 0 0 5px 0;
}

.item-info p {
    margin: 2px 0;
    color: #ccc;
    font-size: 14px;
}

.item-total {
    font-weight: bold;
    color: #4CAF50;
}

.order-details {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 30px;
    margin-top: 20px;
    padding-top: 20px;
    border-top: 1px solid #555;
}

.shipping-info,
.payment-info {
    background: #444;
    padding: 15px;
    border-radius: 6px;
}

.shipping-info h4,
.payment-info h4 {
    margin: 0 0 10px 0;
    color: #4CAF50;
}

.shipping-info p,
.payment-info p {
    margin: 3px 0;
    color: #ccc;
    font-size: 14px;
}

@media (max-width: 768px) {
    .order-header {
        flex-direction: column;
        gap: 15px;
    }
    
    .order-item {
        flex-direction: column;
        align-items: flex-start;
        gap: 10px;
    }
    
    .order-details {
        grid-template-columns: 1fr;
    }
}
</style>