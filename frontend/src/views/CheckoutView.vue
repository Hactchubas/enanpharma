<template>
    <div class="checkout-view">
        <div class="checkout-container">
            <!-- Page Header -->
            <div class="checkout-header">
                <div class="header-content">
                    <div class="title-section">
                        <svg class="page-icon" width="24" height="24" viewBox="0 0 24 24" fill="none">
                            <path d="M5 12l5 5L20 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                        <div class="title-content">
                            <h1 class="checkout-title">Finalizar Compra</h1>
                            <p class="checkout-subtitle">Complete seu pedido em poucos passos</p>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Progress Steps -->
            <div class="progress-steps">
                <div class="steps-container">
                    <div class="step" :class="{ active: checkoutStep >= 1, completed: checkoutStep > 1 }">
                        <div class="step-circle">
                            <svg v-if="checkoutStep > 1" class="step-check" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M5 12l5 5L20 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <span v-else class="step-number">1</span>
                        </div>
                        <div class="step-content">
                            <span class="step-label">Dados Pessoais</span>
                            <span class="step-description">Informações de entrega</span>
                        </div>
                    </div>
                    
                    <div class="step-connector" :class="{ active: checkoutStep > 1 }"></div>
                    
                    <div class="step" :class="{ active: checkoutStep >= 2, completed: checkoutStep > 2 }">
                        <div class="step-circle">
                            <svg v-if="checkoutStep > 2" class="step-check" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M5 12l5 5L20 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <span v-else class="step-number">2</span>
                        </div>
                        <div class="step-content">
                            <span class="step-label">Pagamento</span>
                            <span class="step-description">Forma de pagamento</span>
                        </div>
                    </div>
                    
                    <div class="step-connector" :class="{ active: checkoutStep > 2 }"></div>
                    
                    <div class="step" :class="{ active: checkoutStep >= 3, completed: checkoutStep > 3 }">
                        <div class="step-circle">
                            <svg v-if="checkoutStep > 3" class="step-check" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M5 12l5 5L20 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <span v-else class="step-number">3</span>
                        </div>
                        <div class="step-content">
                            <span class="step-label">Confirmação</span>
                            <span class="step-description">Revisar pedido</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Error Message -->
            <div v-if="orderError" class="error-message">
                <svg class="error-icon" width="20" height="20" viewBox="0 0 24 24" fill="none">
                    <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                    <line x1="15" y1="9" x2="9" y2="15" stroke="currentColor" stroke-width="2"/>
                    <line x1="9" y1="9" x2="15" y2="15" stroke="currentColor" stroke-width="2"/>
                </svg>
                <span>{{ orderError }}</span>
            </div>

            <!-- Success Message -->
            <div v-if="orderSuccess" class="success-container">
                <div class="success-icon">
                    <svg width="64" height="64" viewBox="0 0 24 24" fill="none">
                        <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                        <path d="M9 12l2 2 4-4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </div>
                <h2 class="success-title">Pedido Realizado com Sucesso!</h2>
                <p class="success-text">Seu pedido foi processado e você receberá um e-mail de confirmação em breve.</p>
                <router-link to="/" class="continue-shopping-btn">
                    <div class="btn-icon-wrapper">
                        <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M9 22C9.55228 22 10 21.5523 10 21C10 20.4477 9.55228 20 9 20C8.44772 20 8 20.4477 8 21C8 21.5523 8.44772 22 9 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M20 22C20.5523 22 21 21.5523 21 21C21 20.4477 20.5523 20 20 20C19.4477 20 19 20.4477 19 21C19 21.5523 19.4477 22 20 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M1 1H5L7.68 14.39C7.77144 14.8504 8.02191 15.264 8.38755 15.5583C8.75318 15.8526 9.2107 16.009 9.68 16H19.4C19.8693 16.009 20.3268 15.8526 20.6925 15.5583C21.0581 15.264 21.3086 14.8504 21.4 14.39L23 6H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                    <span class="btn-text">Continuar Comprando</span>
                </router-link>
            </div>
        <!-- Checkout Steps -->
        <div v-else class="checkout-content">
            <!-- Step 1: Customer Information -->
            <div v-if="checkoutStep === 1" class="step-content">
                <h2>Dados Pessoais</h2>
                <form @submit.prevent="nextStep" class="customer-form">
                    <div class="form-group">
                        <label>Nome Completo *</label>
                        <input 
                            v-model="customerInfo.name" 
                            type="text" 
                            required 
                            placeholder="Digite seu nome completo"
                        />
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>Email *</label>
                            <input 
                                v-model="customerInfo.email" 
                                type="email" 
                                required 
                                placeholder="seu@email.com"
                            />
                        </div>
                        <div class="form-group">
                            <label>Telefone *</label>
                            <input 
                                v-model="customerInfo.phone" 
                                type="tel" 
                                required 
                                placeholder="(00) 00000-0000"
                            />
                        </div>
                    </div>
                    
                    <h3>Endereço de Entrega</h3>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>CEP *</label>
                            <input 
                                v-model="customerInfo.address.zipCode" 
                                type="text" 
                                required 
                                placeholder="00000-000"
                            />
                        </div>
                        <div class="form-group">
                            <label>Rua *</label>
                            <input 
                                v-model="customerInfo.address.street" 
                                type="text" 
                                required 
                                placeholder="Nome da rua"
                            />
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>Número *</label>
                            <input 
                                v-model="customerInfo.address.number" 
                                type="text" 
                                required 
                                placeholder="123"
                            />
                        </div>
                        <div class="form-group">
                            <label>Complemento</label>
                            <input 
                                v-model="customerInfo.address.complement" 
                                type="text" 
                                placeholder="Apt, casa, etc"
                            />
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>Bairro *</label>
                            <input 
                                v-model="customerInfo.address.neighborhood" 
                                type="text" 
                                required 
                                placeholder="Nome do bairro"
                            />
                        </div>
                        <div class="form-group">
                            <label>Cidade *</label>
                            <input 
                                v-model="customerInfo.address.city" 
                                type="text" 
                                required 
                                placeholder="Nome da cidade"
                            />
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>Estado *</label>
                        <select v-model="customerInfo.address.state" required>
                            <option value="">Selecione o estado</option>
                            <option value="AC">Acre</option>
                            <option value="AL">Alagoas</option>
                            <option value="AP">Amapá</option>
                            <option value="AM">Amazonas</option>
                            <option value="BA">Bahia</option>
                            <option value="CE">Ceará</option>
                            <option value="DF">Distrito Federal</option>
                            <option value="ES">Espírito Santo</option>
                            <option value="GO">Goiás</option>
                            <option value="MA">Maranhão</option>
                            <option value="MT">Mato Grosso</option>
                            <option value="MS">Mato Grosso do Sul</option>
                            <option value="MG">Minas Gerais</option>
                            <option value="PA">Pará</option>
                            <option value="PB">Paraíba</option>
                            <option value="PR">Paraná</option>
                            <option value="PE">Pernambuco</option>
                            <option value="PI">Piauí</option>
                            <option value="RJ">Rio de Janeiro</option>
                            <option value="RN">Rio Grande do Norte</option>
                            <option value="RS">Rio Grande do Sul</option>
                            <option value="RO">Rondônia</option>
                            <option value="RR">Roraima</option>
                            <option value="SC">Santa Catarina</option>
                            <option value="SP">São Paulo</option>
                            <option value="SE">Sergipe</option>
                            <option value="TO">Tocantins</option>
                        </select>
                    </div>
                    
                    <!-- <div class="form-actions"> -->
                    <!--     <button type="submit" class="btn-primary"> -->
                    <!--         <div class="btn-icon-wrapper"> -->
                    <!--             <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none"> -->
                    <!--                 <path d="M9 18L15 12L9 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/> -->
                    <!--             </svg> -->
                    <!--         </div> -->
                    <!--         <span class="btn-text">Continuar para Pagamento</span> -->
                    <!--     </button> -->
                    <!-- </div> -->
                </form>
            </div>

            <!-- Step 2: Payment Information -->
            <div v-if="checkoutStep === 2" class="step-content">
                <h2>Forma de Pagamento</h2>
                
                <div class="payment-methods">
                    <div 
                        v-for="method in paymentMethods" 
                        :key="method.value"
                        class="payment-method"
                        :class="{ active: paymentInfo.method === method.value }"
                        @click="paymentInfo.method = method.value"
                    >
                        <span class="method-icon">{{ method.icon }}</span>
                        <span class="method-label">{{ method.label }}</span>
                    </div>
                </div>

                <!-- Credit/Debit Card Form -->
                <div v-if="paymentInfo.method === 'credit_card' || paymentInfo.method === 'debit_card'" class="card-form">
                    <div class="form-group">
                        <label>Número do Cartão *</label>
                        <input 
                            v-model="paymentInfo.cardNumber" 
                            type="text" 
                            required 
                            placeholder="0000 0000 0000 0000"
                            maxlength="19"
                        />
                    </div>
                    
                    <div class="form-group">
                        <label>Nome no Cartão *</label>
                        <input 
                            v-model="paymentInfo.cardName" 
                            type="text" 
                            required 
                            placeholder="Nome como está no cartão"
                        />
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>Validade *</label>
                            <input 
                                v-model="paymentInfo.expiryDate" 
                                type="text" 
                                required 
                                placeholder="MM/AA"
                                maxlength="5"
                            />
                        </div>
                        <div class="form-group">
                            <label>CVV *</label>
                            <input 
                                v-model="paymentInfo.cvv" 
                                type="text" 
                                required 
                                placeholder="123"
                                maxlength="4"
                            />
                        </div>
                    </div>
                    
                    <!-- Installments (only for credit card) -->
                    <div v-if="paymentInfo.method === 'credit_card'" class="form-group">
                        <label>Parcelas</label>
                        <select v-model="paymentInfo.installments">
                            <option value="1">1x de R$ {{ totalPrice.toFixed(2) }} sem juros</option>
                            <option value="2">2x de R$ {{ installmentValue.toFixed(2) }} sem juros</option>
                            <option value="3">3x de R$ {{ installmentValue.toFixed(2) }} sem juros</option>
                            <option value="4">4x de R$ {{ installmentValue.toFixed(2) }} sem juros</option>
                            <option value="5">5x de R$ {{ installmentValue.toFixed(2) }} sem juros</option>
                            <option value="6">6x de R$ {{ installmentValue.toFixed(2) }} sem juros</option>
                        </select>
                    </div>
                </div>

                <!-- PIX Instructions -->
                <div v-if="paymentInfo.method === 'pix'" class="pix-info">
                    <p>🔐 Pagamento seguro via PIX</p>
                    <p>Após confirmar o pedido, você receberá um QR Code para pagamento</p>
                </div>

                <!-- Boleto Instructions -->
                <div v-if="paymentInfo.method === 'boleto'" class="boleto-info">
                    <p>📄 Pagamento via Boleto Bancário</p>
                    <p>Após confirmar o pedido, você receberá o boleto por e-mail</p>
                    <p>Prazo de pagamento: 3 dias úteis</p>
                </div>
                
                <div class="form-actions">
                    <button type="button" @click="prevStep" class="btn-secondary">
                        <div class="btn-icon-wrapper">
                            <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <span class="btn-text">Voltar</span>
                    </button>
                    <button type="button" @click="nextStep" class="btn-primary">
                        <div class="btn-icon-wrapper">
                            <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M9 18L15 12L9 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <span class="btn-text">Revisar Pedido</span>
                    </button>
                </div>
            </div>

            <!-- Step 3: Order Confirmation -->
            <div v-if="checkoutStep === 3" class="step-content">
                <h2>Confirmar Pedido</h2>
                
                <div class="order-summary">
                    <div class="summary-section">
                        <h3>Dados de Entrega</h3>
                        <div class="info-box">
                            <p><strong>{{ customerInfo.name }}</strong></p>
                            <p>{{ customerInfo.email }}</p>
                            <p>{{ customerInfo.phone }}</p>
                            <p>
                                {{ customerInfo.address.street }}, {{ customerInfo.address.number }}
                                {{ customerInfo.address.complement ? ', ' + customerInfo.address.complement : '' }}
                            </p>
                            <p>{{ customerInfo.address.neighborhood }} - {{ customerInfo.address.city }}/{{ customerInfo.address.state }}</p>
                            <p>CEP: {{ customerInfo.address.zipCode }}</p>
                        </div>
                    </div>
                    
                    <div class="summary-section">
                        <h3>Forma de Pagamento</h3>
                        <div class="info-box">
                            <p>{{ paymentMethods.find(m => m.value === paymentInfo.method)?.label }}</p>
                            <p v-if="paymentInfo.method === 'credit_card'">
                                {{ paymentInfo.installments }}x de R$ {{ installmentValue.toFixed(2) }}
                            </p>
                        </div>
                    </div>
                    
                    <div class="summary-section">
                        <h3>Itens do Pedido</h3>
                        <div class="order-items">
                            <div v-for="item in cartItems" :key="item.id" class="order-item">
                                <img v-if="item.imageUrl" :src="`http://localhost:8080${item.imageUrl}`" :alt="item.name" />
                                <div class="item-info">
                                    <h4>{{ item.name }}</h4>
                                    <p>Quantidade: {{ item.quantity }}</p>
                                    <p>Valor: R$ {{ item.price.toFixed(2) }}</p>
                                </div>
                                <div class="item-total">
                                    R$ {{ (item.price * item.quantity).toFixed(2) }}
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="final-total">
                        <h3>Total: R$ {{ totalPrice.toFixed(2) }}</h3>
                    </div>
                </div>
                
                <div class="form-actions">
                    <button type="button" @click="prevStep" class="btn-secondary">
                        <div class="btn-icon-wrapper">
                            <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M15 18L9 12L15 6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <span class="btn-text">Voltar</span>
                    </button>
                    <button 
                        type="button" 
                        @click="handleProcessOrder" 
                        :disabled="isProcessing"
                        class="btn-primary"
                    >
                        <div class="btn-icon-wrapper">
                            <svg v-if="isProcessing" class="btn-icon spinner" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2" stroke-dasharray="31.416" stroke-dashoffset="31.416">
                                    <animate attributeName="stroke-dasharray" dur="2s" values="0 31.416;15.708 15.708;0 31.416" repeatCount="indefinite"/>
                                    <animate attributeName="stroke-dashoffset" dur="2s" values="0;-15.708;-31.416" repeatCount="indefinite"/>
                                </circle>
                            </svg>
                            <svg v-else class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M5 12l5 5L20 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <span class="btn-text">{{ isProcessing ? 'Processando...' : 'Finalizar Compra' }}</span>
                    </button>
                </div>
            </div>
        </div>
    </div>

    </div>
</template>

<script setup>
import { useCheckout } from '@/composables/useCheckout.js'
import { useCart } from '@/composables/useCart.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const { cartItems, totalPrice, isEmpty } = useCart()
const {
    checkoutStep,
    isProcessing,
    orderSuccess,
    orderError,
    customerInfo,
    paymentInfo,
    paymentMethods,
    installmentValue,
    nextStep,
    prevStep,
    processOrder,
    resetCheckout
} = useCheckout()

// Redirect to cart if empty
if (isEmpty.value) {
    router.push('/cart')
}

async function handleProcessOrder() {
    try {
        await processOrder()
        // Order success is handled by the template
    } catch (error) {
        console.error('Error processing order:', error)
    }
}

// Reset checkout when component unmounts
import { onUnmounted } from 'vue'
onUnmounted(() => {
    if (!orderSuccess.value) {
        resetCheckout()
    }
})
</script>

<style scoped>
.checkout-view {
    width: 100%;
    min-height: 100vh;
    padding: var(--spacing-lg) 0;
}

.checkout-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 0 var(--spacing-md);
}

/* Page Header */
.checkout-header {
    text-align: center;
    margin-bottom: var(--spacing-lg);
}

.header-content {
    display: flex;
    align-items: center;
    justify-content: center;
}

.title-section {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
}

.page-icon {
    color: var(--text-secondary);
}

.title-content {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.checkout-title {
    font-size: 1.5rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0;
}

.checkout-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
}

/* Progress Steps */
.progress-steps {
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-xl);
    margin-bottom: var(--spacing-xl);
    box-shadow: var(--shadow-sm);
}

.steps-container {
    display: flex;
    align-items: center;
    justify-content: center;
    max-width: 600px;
    margin: 0 auto;
}

.step {
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
    position: relative;
    opacity: 0.5;
    transition: all var(--transition-fast);
}

.step.active,
.step.completed {
    opacity: 1;
}

.step-circle {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    background: rgba(115, 115, 115, 0.1);
    border: 2px solid var(--border-light);
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: var(--spacing-sm);
    transition: all var(--transition-fast);
    position: relative;
    z-index: 2;
}

.step.active .step-circle {
    background: rgba(59, 130, 246, 0.15);
    border-color: rgba(59, 130, 246, 0.5);
    color: rgba(59, 130, 246, 0.9);
}

.step.completed .step-circle {
    background: rgba(16, 185, 129, 0.15);
    border-color: rgba(16, 185, 129, 0.5);
    color: rgba(16, 185, 129, 0.9);
}

.step-number {
    font-weight: 600;
    font-size: 0.875rem;
    color: var(--text-muted);
}

.step.active .step-number {
    color: rgba(59, 130, 246, 0.9);
}

.step-check {
    color: rgba(16, 185, 129, 0.9);
}

.step-content {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.step-label {
    font-size: 0.875rem;
    font-weight: 600;
    color: var(--text-muted);
    transition: color var(--transition-fast);
}

.step.active .step-label,
.step.completed .step-label {
    color: var(--text-secondary);
}

.step-description {
    font-size: 0.75rem;
    color: var(--text-muted);
    opacity: 0.8;
}

.step-connector {
    flex: 1;
    height: 2px;
    background: var(--border-light);
    margin: 0 var(--spacing-lg);
    position: relative;
    top: -12px;
    transition: background-color var(--transition-fast);
}

.step-connector.active {
    background: rgba(16, 185, 129, 0.5);
}

/* Error Message */
.error-message {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    background: rgba(239, 68, 68, 0.1);
    border: 1px solid rgba(239, 68, 68, 0.3);
    color: rgba(239, 68, 68, 0.9);
    padding: var(--spacing-md);
    border-radius: var(--radius-lg);
    margin-bottom: var(--spacing-xl);
    animation: fadeIn 0.3s ease-out;
}

.error-icon {
    color: rgba(239, 68, 68, 0.9);
    flex-shrink: 0;
}

/* Success Container */
.success-container {
    text-align: center;
    padding: var(--spacing-2xl);
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    box-shadow: var(--shadow-lg);
    max-width: 500px;
    margin: 0 auto;
    animation: fadeIn 0.5s ease-out;
}

.success-icon {
    color: rgba(16, 185, 129, 0.9);
    margin-bottom: var(--spacing-lg);
    opacity: 0.9;
}

.success-title {
    color: var(--text-primary);
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: var(--spacing-md);
}

.success-text {
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

.continue-shopping-btn .btn-icon-wrapper {
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

.continue-shopping-btn .btn-icon {
    color: currentColor;
    transition: color var(--transition-fast);
}

.continue-shopping-btn .btn-text {
    color: currentColor;
    font-weight: 500;
}

/* Step Content */
.step-content {
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-2xl);
    box-shadow: var(--shadow-sm);
    animation: fadeIn 0.5s ease-out;
}

.step-content h2 {
    color: var(--text-primary);
    font-size: 1.5rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-xl) 0;
    padding-bottom: var(--spacing-lg);
    border-bottom: 1px solid var(--border-light);
}

.step-content h3 {
    color: var(--text-secondary);
    font-size: 1.125rem;
    font-weight: 600;
    margin: var(--spacing-xl) 0 var(--spacing-lg) 0;
}

/* Forms */
.customer-form {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-lg);
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-sm);
}

.form-group label {
    color: var(--text-secondary);
    font-size: 0.875rem;
    font-weight: 500;
}

.form-group input,
.form-group select {
    padding: var(--spacing-md);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    background: var(--tertiary-bg);
    color: var(--text-primary);
    font-size: 0.875rem;
    transition: all var(--transition-fast);
}

.form-group input:focus,
.form-group select:focus {
    outline: none;
    border-color: var(--border-heavy);
    background: var(--elevated-bg);
    box-shadow: 0 0 0 3px rgba(115, 115, 115, 0.1);
}

.form-group select {
    cursor: pointer;
}

.form-row {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: var(--spacing-lg);
}

/* Form Actions */
.form-actions {
    display: flex;
    gap: var(--spacing-md);
    justify-content: flex-end;
    margin-top: var(--spacing-xl);
    padding-top: var(--spacing-lg);
    border-top: 1px solid var(--border-light);
}

/* Buttons */
.btn-primary {
    position: relative;
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md) var(--spacing-lg);
    border: 1px solid rgba(16, 185, 129, 0.3);
    border-radius: var(--radius-md);
    background: rgba(16, 185, 129, 0.15);
    color: rgba(16, 185, 129, 0.9);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
}

.btn-primary:hover:not(:disabled) {
    transform: translateY(-1px);
    background: rgba(16, 185, 129, 0.25);
    border-color: rgba(16, 185, 129, 0.5);
    color: rgba(16, 185, 129, 1);
    box-shadow: var(--shadow-sm);
}

.btn-primary:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none;
}

.btn-secondary {
    position: relative;
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md) var(--spacing-lg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    background: rgba(115, 115, 115, 0.1);
    color: var(--text-secondary);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
}

.btn-secondary:hover {
    transform: translateY(-1px);
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
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

.btn-primary:hover .btn-icon-wrapper {
    background: rgba(16, 185, 129, 0.3);
    border-color: rgba(16, 185, 129, 0.5);
}

.btn-secondary .btn-icon-wrapper {
    background: rgba(115, 115, 115, 0.15);
    border: 1px solid var(--border-light);
}

.btn-secondary:hover .btn-icon-wrapper {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.btn-icon {
    color: currentColor;
    transition: color var(--transition-fast);
}

.btn-text {
    color: currentColor;
    font-weight: 500;
}

.spinner {
    animation: spin 1s linear infinite;
}

@keyframes spin {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
}

/* Payment Methods */
.payment-methods {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: var(--spacing-md);
    margin-bottom: var(--spacing-xl);
}

.payment-method {
    padding: var(--spacing-lg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    background: var(--tertiary-bg);
    cursor: pointer;
    text-align: center;
    transition: all var(--transition-fast);
}

.payment-method:hover {
    border-color: var(--border-medium);
    background: var(--elevated-bg);
    transform: translateY(-2px);
    box-shadow: var(--shadow-sm);
}

.payment-method.active {
    border-color: rgba(16, 185, 129, 0.5);
    background: rgba(16, 185, 129, 0.1);
}

.method-icon {
    font-size: 1.5rem;
    display: block;
    margin-bottom: var(--spacing-sm);
}

.method-label {
    color: var(--text-secondary);
    font-weight: 500;
    font-size: 0.875rem;
}

.payment-method.active .method-label {
    color: rgba(16, 185, 129, 0.9);
}

/* Card Form */
.card-form {
    background: var(--tertiary-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-xl);
    margin-bottom: var(--spacing-lg);
    display: flex;
    flex-direction: column;
    gap: var(--spacing-lg);
}

/* Info Sections */
.pix-info,
.boleto-info {
    background: var(--tertiary-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
    margin-bottom: var(--spacing-lg);
}

.pix-info p,
.boleto-info p {
    color: var(--text-secondary);
    margin: var(--spacing-xs) 0;
    line-height: 1.5;
}

/* Order Summary */
.order-summary {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xl);
}

.summary-section h3 {
    color: var(--text-secondary);
    font-size: 1.125rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-lg) 0;
    padding-bottom: var(--spacing-sm);
    border-bottom: 1px solid var(--border-light);
}

.info-box {
    background: var(--tertiary-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
}

.info-box p {
    color: var(--text-secondary);
    margin: var(--spacing-xs) 0;
    line-height: 1.5;
}

.order-items {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-md);
}

.order-item {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
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

.order-item img {
    width: 60px;
    height: 60px;
    object-fit: cover;
    border-radius: var(--radius-md);
    border: 1px solid var(--border-light);
}

.item-info {
    flex: 1;
}

.item-info h4 {
    color: var(--text-primary);
    font-size: 1rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-xs) 0;
}

.item-info p {
    color: var(--text-muted);
    font-size: 0.875rem;
    margin: 2px 0;
    line-height: 1.4;
}

.item-total {
    color: var(--text-primary);
    font-weight: 600;
    font-size: 1rem;
}

.final-total {
    background: rgba(16, 185, 129, 0.1);
    border: 1px solid rgba(16, 185, 129, 0.3);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
    text-align: right;
}

.final-total h3 {
    color: rgba(16, 185, 129, 0.9);
    font-size: 1.5rem;
    font-weight: 700;
    margin: 0;
}

/* Responsive Design */
@media (max-width: 1024px) {
    .checkout-container {
        padding: 0 var(--spacing-md);
    }
}

@media (max-width: 768px) {
    .checkout-view {
        padding: var(--spacing-lg) 0;
    }
    
    .checkout-header {
        padding: var(--spacing-lg);
    }
    
    .title-section {
        gap: var(--spacing-md);
    }
    
    .checkout-title {
        font-size: 1.5rem;
    }
    
    .step-content {
        padding: var(--spacing-xl);
    }
    
    .steps-container {
        flex-direction: column;
        gap: var(--spacing-lg);
        max-width: none;
    }
    
    .step-connector {
        display: none;
    }
    
    .form-row {
        grid-template-columns: 1fr;
    }
    
    .form-actions {
        flex-direction: column-reverse;
        align-items: stretch;
    }
    
    .payment-methods {
        grid-template-columns: 1fr;
    }
    
    .order-item {
        flex-direction: column;
        text-align: center;
        gap: var(--spacing-sm);
    }
    
    .item-info {
        text-align: center;
    }
}

@media (max-width: 640px) {
    .title-section {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }
    
    .success-container {
        padding: var(--spacing-xl);
        margin: 0 var(--spacing-md);
    }
    
    .step-content {
        padding: var(--spacing-lg);
    }
    
    .steps-container {
        gap: var(--spacing-md);
    }
    
    .step {
        min-width: 0;
    }
    
    .step-circle {
        width: 40px;
        height: 40px;
    }
    
    .step-label {
        font-size: 0.8rem;
    }
    
    .step-description {
        display: none;
    }
}

@media (max-width: 480px) {
    .checkout-header {
        padding: var(--spacing-md);
    }
    
    .checkout-title {
        font-size: 1.25rem;
    }
    
    .checkout-subtitle {
        font-size: 0.8rem;
    }
    
    .step-content {
        padding: var(--spacing-md);
    }
    
    .step-content h2 {
        font-size: 1.25rem;
    }
    
    .final-total h3 {
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
