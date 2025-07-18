<template>
    <div class="checkout-container">
        <h1>Finalizar Compra</h1>
        
        <!-- Progress Steps -->
        <div class="progress-steps">
            <div class="step" :class="{ active: checkoutStep >= 1, completed: checkoutStep > 1 }">
                <span class="step-number">1</span>
                <span class="step-label">Dados Pessoais</span>
            </div>
            <div class="step" :class="{ active: checkoutStep >= 2, completed: checkoutStep > 2 }">
                <span class="step-number">2</span>
                <span class="step-label">Pagamento</span>
            </div>
            <div class="step" :class="{ active: checkoutStep >= 3, completed: checkoutStep > 3 }">
                <span class="step-number">3</span>
                <span class="step-label">Confirmação</span>
            </div>
        </div>

        <!-- Error Message -->
        <div v-if="orderError" class="error-message">
            {{ orderError }}
        </div>

        <!-- Success Message -->
        <div v-if="orderSuccess" class="success-container">
            <div class="success-icon">✅</div>
            <h2>Pedido Realizado com Sucesso!</h2>
            <p>Seu pedido foi processado e você receberá um e-mail de confirmação.</p>
            <router-link to="/" class="continue-btn">Continuar Comprando</router-link>
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
                    
                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Continuar para Pagamento</button>
                    </div>
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
                    <button type="button" @click="prevStep" class="btn-secondary">Voltar</button>
                    <button type="button" @click="nextStep" class="btn-primary">Revisar Pedido</button>
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
                    <button type="button" @click="prevStep" class="btn-secondary">Voltar</button>
                    <button 
                        type="button" 
                        @click="handleProcessOrder" 
                        :disabled="isProcessing"
                        class="btn-primary"
                    >
                        {{ isProcessing ? 'Processando...' : 'Finalizar Compra' }}
                    </button>
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
.checkout-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    color: white;
}

.checkout-container h1 {
    text-align: center;
    margin-bottom: 30px;
}

.progress-steps {
    display: flex;
    justify-content: space-between;
    margin-bottom: 40px;
    position: relative;
}

.progress-steps::before {
    content: '';
    position: absolute;
    top: 20px;
    left: 0;
    right: 0;
    height: 2px;
    background: #555;
    z-index: 1;
}

.step {
    display: flex;
    flex-direction: column;
    align-items: center;
    position: relative;
    z-index: 2;
}

.step-number {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: #555;
    color: white;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    margin-bottom: 8px;
}

.step.active .step-number {
    background: #4CAF50;
}

.step.completed .step-number {
    background: #2196F3;
}

.step-label {
    font-size: 12px;
    text-align: center;
    color: #ccc;
}

.step.active .step-label {
    color: white;
}

.error-message {
    background: #f44336;
    color: white;
    padding: 12px;
    border-radius: 6px;
    margin-bottom: 20px;
    text-align: center;
}

.success-container {
    text-align: center;
    padding: 40px;
    background: #333;
    border-radius: 8px;
}

.success-icon {
    font-size: 48px;
    margin-bottom: 20px;
}

.continue-btn {
    background: #4CAF50;
    color: white;
    padding: 12px 24px;
    border-radius: 6px;
    text-decoration: none;
    font-weight: 500;
    display: inline-block;
    margin-top: 20px;
}

.step-content {
    background: #333;
    padding: 30px;
    border-radius: 8px;
}

.customer-form {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.form-group {
    display: flex;
    flex-direction: column;
}

.form-group label {
    margin-bottom: 5px;
    font-weight: 500;
}

.form-group input,
.form-group select {
    padding: 12px;
    border: 1px solid #555;
    border-radius: 6px;
    background: #444;
    color: white;
}

.form-group input:focus,
.form-group select:focus {
    outline: none;
    border-color: #4CAF50;
}

.form-row {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 20px;
}

.form-actions {
    display: flex;
    gap: 15px;
    justify-content: flex-end;
    margin-top: 30px;
}

.btn-primary {
    background: #4CAF50;
    color: white;
    border: none;
    padding: 12px 24px;
    border-radius: 6px;
    font-weight: 500;
    cursor: pointer;
}

.btn-primary:hover:not(:disabled) {
    background: #45a049;
}

.btn-primary:disabled {
    background: #666;
    cursor: not-allowed;
}

.btn-secondary {
    background: #666;
    color: white;
    border: none;
    padding: 12px 24px;
    border-radius: 6px;
    font-weight: 500;
    cursor: pointer;
}

.btn-secondary:hover {
    background: #777;
}

.payment-methods {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 15px;
    margin-bottom: 30px;
}

.payment-method {
    padding: 20px;
    border: 2px solid #555;
    border-radius: 8px;
    cursor: pointer;
    text-align: center;
    transition: all 0.3s;
}

.payment-method:hover {
    border-color: #4CAF50;
}

.payment-method.active {
    border-color: #4CAF50;
    background: rgba(76, 175, 80, 0.1);
}

.method-icon {
    font-size: 24px;
    display: block;
    margin-bottom: 10px;
}

.card-form {
    background: #444;
    padding: 25px;
    border-radius: 8px;
    margin-bottom: 20px;
}

.pix-info,
.boleto-info {
    background: #444;
    padding: 20px;
    border-radius: 8px;
    margin-bottom: 20px;
}

.pix-info p,
.boleto-info p {
    margin: 5px 0;
}

.order-summary {
    display: flex;
    flex-direction: column;
    gap: 25px;
}

.summary-section h3 {
    margin-bottom: 15px;
    color: #4CAF50;
}

.info-box {
    background: #444;
    padding: 20px;
    border-radius: 8px;
}

.info-box p {
    margin: 5px 0;
}

.order-items {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.order-item {
    display: flex;
    align-items: center;
    gap: 15px;
    padding: 15px;
    background: #444;
    border-radius: 8px;
}

.order-item img {
    width: 60px;
    height: 60px;
    object-fit: cover;
    border-radius: 6px;
}

.item-info {
    flex: 1;
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

.final-total {
    text-align: right;
    padding: 20px;
    background: #444;
    border-radius: 8px;
    border: 2px solid #4CAF50;
}

.final-total h3 {
    margin: 0;
    color: #4CAF50;
    font-size: 24px;
}

@media (max-width: 768px) {
    .form-row {
        grid-template-columns: 1fr;
    }
    
    .progress-steps {
        flex-direction: column;
        gap: 20px;
    }
    
    .progress-steps::before {
        display: none;
    }
    
    .payment-methods {
        grid-template-columns: 1fr;
    }
    
    .order-item {
        flex-direction: column;
        text-align: center;
    }
}
</style>