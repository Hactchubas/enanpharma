<template>
    <div class="cart-container">
        <h1>Carrinho de Compras</h1>
        
        <div v-if="isEmpty" class="empty-cart">
            <p>Seu carrinho está vazio</p>
            <router-link to="/" class="continue-shopping-btn">
                Continuar Comprando
            </router-link>
        </div>
        
        <div v-else class="cart-content">
            <div class="cart-items">
                <div class="cart-item" v-for="item in cartItems" :key="item.id">
                    <div class="item-image">
                        <img v-if="item.imageUrl" :src="`http://localhost:8080${item.imageUrl}`" :alt="item.name" />
                        <div v-else class="no-image">Sem imagem</div>
                    </div>
                    
                    <div class="item-details">
                        <h3>{{ item.name }}</h3>
                        <p class="item-price">R$ {{ item.price.toFixed(2) }}</p>
                        <p class="stock-info">Estoque: {{ item.maxQuantity }}</p>
                    </div>
                    
                    <div class="item-controls">
                        <div class="quantity-controls">
                            <button @click="decreaseQuantity(item.id)" class="quantity-btn" :disabled="item.quantity <= 1">-</button>
                            <input 
                                type="number" 
                                v-model.number="item.quantity" 
                                @change="validateAndUpdateQuantity(item.id, item.quantity, item.maxQuantity)"
                                :min="1" 
                                :max="item.maxQuantity"
                                class="quantity-input"
                            />
                            <button @click="increaseQuantity(item)" class="quantity-btn" :disabled="item.quantity >= item.maxQuantity">+</button>
                        </div>
                        
                        <div class="item-total">
                            <span>Total: R$ {{ (item.price * item.quantity).toFixed(2) }}</span>
                        </div>
                        
                        <button @click="removeFromCart(item.id)" class="remove-btn">
                            🗑️ Remover
                        </button>
                    </div>
                </div>
            </div>
            
            <div class="cart-summary">
                <div class="summary-card">
                    <h3>Resumo do Pedido</h3>
                    <div class="summary-line">
                        <span>Itens ({{ totalItems }})</span>
                        <span>R$ {{ totalPrice.toFixed(2) }}</span>
                    </div>
                    <div class="summary-line total">
                        <span>Total</span>
                        <span>R$ {{ totalPrice.toFixed(2) }}</span>
                    </div>
                    
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
.cart-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.cart-container h1 {
    color: white;
    margin-bottom: 30px;
}

.empty-cart {
    text-align: center;
    padding: 60px 20px;
    color: #ccc;
}

.empty-cart p {
    font-size: 18px;
    margin-bottom: 20px;
}

.continue-shopping-btn {
    background: #4CAF50;
    color: white;
    padding: 12px 24px;
    border-radius: 6px;
    text-decoration: none;
    font-weight: 500;
}

.continue-shopping-btn:hover {
    background: #45a049;
}

.cart-content {
    display: grid;
    grid-template-columns: 1fr 300px;
    gap: 30px;
}

.cart-items {
    display: flex;
    flex-direction: column;
    gap: 20px;
}

.cart-item {
    background: #333;
    border-radius: 8px;
    padding: 20px;
    display: grid;
    grid-template-columns: 100px 1fr auto;
    gap: 20px;
    align-items: center;
}

.item-image img {
    width: 80px;
    height: 80px;
    object-fit: cover;
    border-radius: 6px;
}

.no-image {
    width: 80px;
    height: 80px;
    background: #555;
    border-radius: 6px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #999;
    font-size: 12px;
}

.item-details h3 {
    color: white;
    margin: 0 0 8px 0;
}

.item-price {
    color: #4CAF50;
    font-weight: bold;
    margin: 0 0 4px 0;
}

.stock-info {
    color: #ccc;
    font-size: 14px;
    margin: 0;
}

.item-controls {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
}

.quantity-controls {
    display: flex;
    align-items: center;
    gap: 8px;
}

.quantity-btn {
    background: #666;
    color: white;
    border: none;
    border-radius: 4px;
    width: 32px;
    height: 32px;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    font-size: 16px;
    font-weight: bold;
}

.quantity-btn:hover:not(:disabled) {
    background: #777;
}

.quantity-btn:disabled {
    background: #444;
    cursor: not-allowed;
    opacity: 0.5;
}

.quantity-input {
    width: 60px;
    text-align: center;
    padding: 6px;
    border: 1px solid #555;
    border-radius: 4px;
    background: #444;
    color: white;
}

.item-total {
    color: white;
    font-weight: bold;
}

.remove-btn {
    background: #f44336;
    color: white;
    border: none;
    border-radius: 4px;
    padding: 6px 12px;
    cursor: pointer;
    font-size: 12px;
}

.remove-btn:hover {
    background: #d32f2f;
}

.cart-summary {
    position: sticky;
    top: 20px;
    height: fit-content;
}

.summary-card {
    background: #333;
    border-radius: 8px;
    padding: 24px;
}

.summary-card h3 {
    color: white;
    margin: 0 0 20px 0;
}

.summary-line {
    display: flex;
    justify-content: space-between;
    margin-bottom: 12px;
    color: #ccc;
}

.summary-line.total {
    border-top: 1px solid #555;
    padding-top: 12px;
    margin-top: 12px;
    font-weight: bold;
    color: white;
    font-size: 18px;
}

.checkout-btn {
    width: 100%;
    background: #4CAF50;
    color: white;
    border: none;
    border-radius: 6px;
    padding: 12px;
    font-size: 16px;
    font-weight: 500;
    cursor: pointer;
    margin-top: 20px;
    text-decoration: none;
    display: block;
    text-align: center;
}

.checkout-btn:hover {
    background: #45a049;
}

.clear-cart-btn {
    width: 100%;
    background: #f44336;
    color: white;
    border: none;
    border-radius: 6px;
    padding: 8px;
    font-size: 14px;
    cursor: pointer;
    margin-top: 12px;
}

.clear-cart-btn:hover {
    background: #d32f2f;
}

@media (max-width: 768px) {
    .cart-content {
        grid-template-columns: 1fr;
    }
    
    .cart-item {
        grid-template-columns: 1fr;
        text-align: center;
    }
    
    .item-controls {
        flex-direction: row;
        justify-content: space-between;
    }
}
</style>