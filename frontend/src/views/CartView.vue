<template>
    <div class="cart-view">
        <div class="cart-container">
            <div class="cart-header">
                <h1 class="cart-title">Carrinho de Compras</h1>
                <p class="cart-subtitle">Revise seus itens antes de finalizar</p>
            </div>
            
            <div v-if="isEmpty" class="empty-cart">
                <div class="empty-cart-icon">🛒</div>
                <h2 class="empty-cart-title">Seu carrinho está vazio</h2>
                <p class="empty-cart-text">Adicione alguns produtos para começar suas compras</p>
                <router-link to="/" class="continue-shopping-btn">
                    Continuar Comprando
                </router-link>
            </div>
            
            <div v-else class="cart-content">
                <div class="cart-items">
                    <div class="cart-item" v-for="item in cartItems" :key="item.id">
                        <div class="item-image">
                            <img v-if="item.imageUrl" :src="`http://localhost:8080${item.imageUrl}`" :alt="item.name" />
                            <div v-else class="no-image">
                                <span>Sem imagem</span>
                            </div>
                        </div>
                        
                        <div class="item-details">
                            <h3 class="item-name">{{ item.name }}</h3>
                            <p class="item-price">R$ {{ item.price.toFixed(2) }}</p>
                            <p class="stock-info">{{ item.maxQuantity }} disponíveis</p>
                        </div>
                        
                        <div class="item-controls">
                            <div class="quantity-controls">
                                <button @click="decreaseQuantity(item.id)" class="quantity-btn" :disabled="item.quantity <= 1">
                                    <span>−</span>
                                </button>
                                <input 
                                    type="number" 
                                    v-model.number="item.quantity" 
                                    @change="validateAndUpdateQuantity(item.id, item.quantity, item.maxQuantity)"
                                    :min="1" 
                                    :max="item.maxQuantity"
                                    class="quantity-input"
                                />
                                <button @click="increaseQuantity(item)" class="quantity-btn" :disabled="item.quantity >= item.maxQuantity">
                                    <span>+</span>
                                </button>
                            </div>
                            
                            <div class="item-total">
                                <span class="total-label">Subtotal:</span>
                                <span class="total-value">R$ {{ (item.price * item.quantity).toFixed(2) }}</span>
                            </div>
                            
                            <button @click="removeFromCart(item.id)" class="remove-btn" title="Remover item">
                                ×
                            </button>
                        </div>
                    </div>
                </div>
                
                <div class="cart-summary">
                    <div class="summary-card">
                        <div class="summary-header">
                            <h3 class="summary-title">Resumo do Pedido</h3>
                        </div>
                        
                        <div class="summary-content">
                            <div class="summary-line">
                                <span class="summary-label">Itens ({{ totalItems }})</span>
                                <span class="summary-value">R$ {{ totalPrice.toFixed(2) }}</span>
                            </div>
                            
                            <div class="summary-divider"></div>
                            
                            <div class="summary-line total">
                                <span class="summary-label">Total</span>
                                <span class="summary-value">R$ {{ totalPrice.toFixed(2) }}</span>
                            </div>
                        </div>
                        
                        <div class="summary-actions">
                            <router-link to="/checkout" class="checkout-btn">
                                Finalizar Compra
                            </router-link>
                            
                            <button @click="clearCart" class="clear-cart-btn">
                                Limpar Carrinho
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useCart } from '@/composables/useCart.js'

const { 
    cartItems, 
    totalItems, 
    totalPrice, 
    isEmpty, 
    removeFromCart, 
    updateQuantity, 
    clearCart 
} = useCart()

function increaseQuantity(item) {
    if (item.quantity < item.maxQuantity) {
        updateQuantity(item.id, item.quantity + 1)
    }
}

function decreaseQuantity(itemId) {
    const item = cartItems.value.find(i => i.id === itemId)
    if (item && item.quantity > 1) {
        updateQuantity(itemId, item.quantity - 1)
    }
}

function validateAndUpdateQuantity(itemId, quantity, maxQuantity) {
    if (quantity < 1) {
        quantity = 1
    } else if (quantity > maxQuantity) {
        quantity = maxQuantity
    }
    updateQuantity(itemId, quantity)
}
</script>

<style scoped>
.cart-view {
    width: 100%;
    min-height: 100vh;
    padding: var(--spacing-xl) 0;
}

.cart-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 var(--spacing-lg);
}

.cart-header {
    text-align: center;
    margin-bottom: var(--spacing-2xl);
    padding-bottom: var(--spacing-lg);
    border-bottom: 1px solid var(--border-light);
}

.cart-title {
    color: var(--text-primary);
    font-size: 2rem;
    font-weight: 600;
    margin-bottom: var(--spacing-sm);
    letter-spacing: -0.025em;
}

.cart-subtitle {
    color: var(--text-secondary);
    font-size: 0.875rem;
    margin-bottom: 0;
}

.empty-cart {
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

.empty-cart-icon {
    font-size: 4rem;
    margin-bottom: var(--spacing-lg);
    opacity: 0.6;
}

.empty-cart-title {
    color: var(--text-primary);
    font-size: 1.5rem;
    font-weight: 600;
    margin-bottom: var(--spacing-md);
}

.empty-cart-text {
    color: var(--text-secondary);
    margin-bottom: var(--spacing-xl);
    line-height: 1.6;
}

.continue-shopping-btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    padding: var(--spacing-sm) var(--spacing-lg);
    background: var(--accent-primary);
    color: white;
    text-decoration: none;
    border-radius: var(--radius-md);
    font-weight: 500;
    font-size: 0.875rem;
    transition: all var(--transition-fast);
    box-shadow: var(--shadow-sm);
}

.continue-shopping-btn:hover {
    background: var(--accent-primary-hover);
    transform: translateY(-1px);
    box-shadow: var(--shadow-md);
}

.cart-content {
    display: grid;
    grid-template-columns: 1fr 350px;
    gap: var(--spacing-2xl);
    align-items: start;
}

.cart-items {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-lg);
}

.cart-item {
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-xl);
    display: grid;
    grid-template-columns: 80px 1fr auto;
    gap: var(--spacing-lg);
    align-items: center;
    box-shadow: var(--shadow-sm);
    transition: all var(--transition-fast);
}

.cart-item:hover {
    box-shadow: var(--shadow-md);
    transform: translateY(-1px);
}

.item-image {
    width: 80px;
    height: 80px;
    border-radius: var(--radius-md);
    overflow: hidden;
}

.item-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.no-image {
    width: 100%;
    height: 100%;
    background: var(--tertiary-bg);
    display: flex;
    align-items: center;
    justify-content: center;
    color: var(--text-muted);
    font-size: 0.75rem;
    border-radius: var(--radius-md);
}

.item-details {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.item-name {
    color: var(--text-primary);
    font-size: 1.125rem;
    font-weight: 600;
    margin: 0;
    line-height: 1.4;
}

.item-price {
    color: var(--accent-primary);
    font-size: 1rem;
    font-weight: 600;
    margin: 0;
}

.stock-info {
    color: var(--text-muted);
    font-size: 0.75rem;
    margin: 0;
}

.item-controls {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: var(--spacing-md);
    min-width: 120px;
}

.quantity-controls {
    display: flex;
    align-items: center;
    gap: var(--spacing-xs);
    background: rgba(115, 115, 115, 0.05);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    padding: var(--spacing-xs);
}

.quantity-btn {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    background: rgba(115, 115, 115, 0.15);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    color: var(--text-secondary);
    cursor: pointer;
    font-size: 0.875rem;
    font-weight: 600;
    transition: all var(--transition-fast);
}

.quantity-btn:hover:not(:disabled) {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
    color: var(--text-primary);
    transform: translateY(-1px);
    box-shadow: var(--shadow-sm);
}

.quantity-btn:disabled {
    color: var(--text-disabled);
    cursor: not-allowed;
    opacity: 0.5;
    background: rgba(115, 115, 115, 0.05);
}

.quantity-input {
    width: 40px;
    text-align: center;
    padding: var(--spacing-xs);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-sm);
    background: rgba(115, 115, 115, 0.1);
    color: var(--text-primary);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
}

.quantity-input:focus {
    outline: none;
    background: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
}

.item-total {
    text-align: center;
}

.total-label {
    display: block;
    color: var(--text-muted);
    font-size: 0.75rem;
    margin-bottom: var(--spacing-xs);
}

.total-value {
    color: var(--text-primary);
    font-weight: 600;
    font-size: 0.875rem;
}

.remove-btn {
    display: inline-flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    background: transparent;
    color: var(--text-muted);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-sm);
    font-size: 1.2rem;
    font-weight: 400;
    cursor: pointer;
    transition: all var(--transition-fast);
    line-height: 1;
}

.remove-btn:hover {
    background: var(--accent-error);
    color: white;
    border-color: var(--accent-error);
    transform: scale(1.1);
}

.cart-summary {
    position: sticky;
    top: var(--spacing-xl);
    height: fit-content;
}

.summary-card {
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-sm);
    overflow: hidden;
    animation: fadeIn 0.5s ease-out;
}

.summary-header {
    padding: var(--spacing-lg);
    border-bottom: 1px solid var(--border-light);
    background: var(--tertiary-bg);
}

.summary-title {
    color: var(--text-primary);
    font-size: 1.125rem;
    font-weight: 600;
    margin: 0;
}

.summary-content {
    padding: var(--spacing-lg);
}

.summary-line {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: var(--spacing-sm);
}

.summary-line:last-child {
    margin-bottom: 0;
}

.summary-label {
    color: var(--text-secondary);
    font-size: 0.875rem;
}

.summary-value {
    color: var(--text-primary);
    font-weight: 500;
    font-size: 0.875rem;
}

.summary-divider {
    height: 1px;
    background: var(--border-light);
    margin: var(--spacing-md) 0;
}

.summary-line.total .summary-label,
.summary-line.total .summary-value {
    color: var(--text-primary);
    font-weight: 600;
    font-size: 1rem;
}

.summary-actions {
    padding: var(--spacing-lg);
    border-top: 1px solid var(--border-light);
    background: var(--tertiary-bg);
    display: flex;
    flex-direction: column;
    gap: var(--spacing-md);
}

.checkout-btn {
    position: relative;
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-sm) var(--spacing-md);
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

.checkout-btn:hover {
    transform: translateY(-1px);
    background: rgba(16, 185, 129, 0.25);
    border-color: rgba(16, 185, 129, 0.5);
    color: rgba(16, 185, 129, 1);
    box-shadow: var(--shadow-sm);
}

.checkout-btn::before {
    content: '';
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    background: rgba(16, 185, 129, 0.2);
    border: 1px solid rgba(16, 185, 129, 0.3);
    border-radius: var(--radius-md);
    transition: all var(--transition-fast);
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='rgba(16,185,129,0.9)' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpath d='M5 12l5 5L20 7'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: center;
    background-size: 16px;
}

.checkout-btn:hover::before {
    background: rgba(16, 185, 129, 0.3);
    border-color: rgba(16, 185, 129, 0.5);
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='rgba(16,185,129,1)' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpath d='M5 12l5 5L20 7'/%3e%3c/svg%3e");
}

.clear-cart-btn {
    position: relative;
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-sm) var(--spacing-md);
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

.clear-cart-btn:hover {
    transform: translateY(-1px);
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
    box-shadow: var(--shadow-sm);
}

.clear-cart-btn::before {
    content: '';
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    background: rgba(115, 115, 115, 0.15);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    transition: all var(--transition-fast);
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='%23a3a3a3' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpath d='M3 6h18M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: center;
    background-size: 16px;
}

.clear-cart-btn:hover::before {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='16' height='16' viewBox='0 0 24 24' fill='none' stroke='%23d4d4d4' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpath d='M3 6h18M8 6V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6'/%3e%3c/svg%3e");
}

@media (max-width: 1024px) {
    .cart-content {
        grid-template-columns: 1fr;
        gap: var(--spacing-xl);
    }
    
    .cart-summary {
        position: static;
    }
}

@media (max-width: 768px) {
    .cart-container {
        padding: 0 var(--spacing-md);
    }
    
    .cart-item {
        grid-template-columns: 1fr;
        text-align: center;
        gap: var(--spacing-md);
        padding: var(--spacing-lg);
    }
    
    .item-controls {
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        min-width: auto;
        width: 100%;
    }
    
    .quantity-controls {
        order: 1;
    }
    
    .item-total {
        order: 2;
    }
    
    .remove-btn {
        order: 3;
    }
}

@media (max-width: 480px) {
    .cart-view {
        padding: var(--spacing-lg) 0;
    }
    
    .cart-content {
        gap: var(--spacing-lg);
    }
    
    .cart-item {
        padding: var(--spacing-md);
    }
    
    .item-controls {
        flex-direction: column;
        gap: var(--spacing-sm);
    }
}
</style>