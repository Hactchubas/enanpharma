<script setup>
import auth from "../api/auth.js";
import { ref, onMounted, onUpdated } from "vue";
import { useRouter } from 'vue-router'
import { useCart } from '@/composables/useCart.js'
const router = useRouter()
const { totalItems } = useCart()

const info = ref({
    isAdmin: false
})

onMounted(() => {
    if (auth.isLoggedIn()) fetchUserInfo()
})
onUpdated(fetchUserInfo)
async function fetchUserInfo() {
    const res = await auth.getUserInfo()
    if (res == null) {
        info.value.isAdmin = false
        return
    }
    const roles = res.roles
    if (roles.includes("ADMIN")) {
        info.value.isAdmin = true
    } else {
        info.value.isAdmin = false
    }
}

function logout() {
    auth.logout();
    router.push('/')
}


import RegisterDropdown from './DropdownLinks.vue'

const registerOptions = [
    { label: 'Category', path: '/categories' },
    { label: 'Product', path: '/products' },
]
</script>

<template>
    <nav class="navbar">
        <div class="container navbar-content">
            <div class="navbar-left">
                <router-link to="/" class="brand">
                    <div class="brand-logo">
                        <span class="brand-icon">🔬</span>
                        <span class="brand-text">Enanpharma</span>
                        <span v-if='info.isAdmin' class="admin-badge">ADMIN</span>
                    </div>
                </router-link>
            </div>
            
            <div class="navbar-center">
                <div v-if="auth.isLoggedIn()" class="user-welcome">
                    <router-link to="/profile" class="user-link">
                        <span class="user-icon">👤</span>
                        <span class="user-name">{{ auth.user }}</span>
                    </router-link>
                </div>
            </div>
            
            <div class="navbar-right">
                <div class="nav-actions">
                    <router-link to="/cart" class="nav-btn cart-btn">
                        <span class="btn-icon">🛒</span>
                        <span class="btn-text">Carrinho</span>
                        <span v-if="totalItems > 0" class="cart-badge">{{ totalItems }}</span>
                    </router-link>
                    
                    <template v-if="auth.isLoggedIn()">
                        <router-link to="/orders" class="nav-btn orders-btn">
                            <span class="btn-icon">📋</span>
                            <span class="btn-text">Pedidos</span>
                        </router-link>
                        
                        <RegisterDropdown v-if="info.isAdmin" :items="registerOptions" button-text="Cadastrar" class="admin-dropdown" />
                        
                        <button @click="logout" class="nav-btn logout-btn">
                            <span class="btn-icon">🚪</span>
                            <span class="btn-text">Sair</span>
                        </button>
                    </template>
                    
                    <template v-else>
                        <router-link to="/login" class="nav-btn login-btn">
                            <span class="btn-icon">🔑</span>
                            <span class="btn-text">Entrar</span>
                        </router-link>
                        <router-link to="/signup" class="nav-btn signup-btn">
                            <span class="btn-icon">📝</span>
                            <span class="btn-text">Cadastrar</span>
                        </router-link>
                    </template>
                </div>
            </div>
        </div>
    </nav>
</template>

<style scoped>
.navbar {
    position: sticky;
    top: 0;
    z-index: 1000;
    background: rgba(10, 10, 10, 0.95);
    backdrop-filter: blur(10px);
    border-bottom: 1px solid var(--border-light);
    box-shadow: var(--shadow-md);
}

.navbar-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: var(--spacing-md) 0;
    min-height: 70px;
}

.navbar-left {
    flex: 1;
}

.navbar-center {
    flex: 2;
    display: flex;
    justify-content: center;
}

.navbar-right {
    flex: 1;
    display: flex;
    justify-content: flex-end;
}

/* Brand */
.brand {
    color: var(--text-primary);
    text-decoration: none;
    transition: color var(--transition-fast);
}

.brand:hover {
    color: var(--accent-primary);
}

.brand-logo {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
}

.brand-icon {
    font-size: 1.5rem;
}

.brand-text {
    font-size: 1.5rem;
    font-weight: 700;
    background: linear-gradient(45deg, var(--accent-primary), var(--accent-info));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
}

.admin-badge {
    background: var(--accent-warning);
    color: white;
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-sm);
    font-size: 0.625rem;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

/* User Welcome */
.user-welcome {
    display: flex;
    align-items: center;
}

.user-link {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-sm) var(--spacing-md);
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    color: var(--text-primary);
    text-decoration: none;
    transition: all var(--transition-fast);
}

.user-link:hover {
    background: var(--elevated-bg);
    border-color: var(--border-medium);
    color: var(--accent-primary);
    transform: translateY(-1px);
}

.user-icon {
    font-size: 1.1rem;
}

.user-name {
    font-weight: 500;
    font-size: 0.875rem;
}

/* Navigation Actions */
.nav-actions {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
}

.nav-btn {
    position: relative;
    display: flex;
    align-items: center;
    gap: var(--spacing-xs);
    padding: var(--spacing-sm) var(--spacing-md);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    background: var(--surface-bg);
    color: var(--text-primary);
    text-decoration: none;
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
}

.nav-btn:hover {
    transform: translateY(-1px);
    box-shadow: var(--shadow-md);
}

.btn-icon {
    font-size: 1rem;
}

.btn-text {
    display: none;
}

/* Specific button styles */
.cart-btn {
    background: var(--accent-success);
    border-color: var(--accent-success);
    color: white;
}

.cart-btn:hover {
    background: #059669;
    border-color: #059669;
    color: white;
}

.orders-btn {
    background: var(--accent-info);
    border-color: var(--accent-info);
    color: white;
}

.orders-btn:hover {
    background: #0891b2;
    border-color: #0891b2;
    color: white;
}

.login-btn {
    background: var(--accent-primary);
    border-color: var(--accent-primary);
    color: white;
}

.login-btn:hover {
    background: var(--accent-primary-hover);
    border-color: var(--accent-primary-hover);
    color: white;
}

.signup-btn {
    background: transparent;
    border-color: var(--accent-primary);
    color: var(--accent-primary);
}

.signup-btn:hover {
    background: var(--accent-primary);
    color: white;
}

.logout-btn {
    background: var(--accent-error);
    border-color: var(--accent-error);
    color: white;
}

.logout-btn:hover {
    background: #dc2626;
    border-color: #dc2626;
    color: white;
}

/* Cart Badge */
.cart-badge {
    position: absolute;
    top: -8px;
    right: -8px;
    background: var(--accent-error);
    color: white;
    border-radius: 50%;
    padding: 2px 6px;
    font-size: 0.75rem;
    font-weight: 600;
    min-width: 20px;
    text-align: center;
    line-height: 1;
    border: 2px solid var(--surface-bg);
    animation: pulse 2s infinite;
}

@keyframes pulse {
    0% {
        box-shadow: 0 0 0 0 rgba(239, 68, 68, 0.7);
    }
    70% {
        box-shadow: 0 0 0 10px rgba(239, 68, 68, 0);
    }
    100% {
        box-shadow: 0 0 0 0 rgba(239, 68, 68, 0);
    }
}

/* Admin Dropdown */
.admin-dropdown {
    margin-left: var(--spacing-sm);
}

/* Responsive Design */
@media (min-width: 768px) {
    .btn-text {
        display: inline;
    }
    
    .nav-btn {
        padding: var(--spacing-sm) var(--spacing-lg);
    }
}

@media (max-width: 768px) {
    .navbar-content {
        padding: var(--spacing-sm) 0;
        min-height: 60px;
    }
    
    .navbar-center {
        display: none;
    }
    
    .navbar-left,
    .navbar-right {
        flex: auto;
    }
    
    .brand-text {
        font-size: 1.25rem;
    }
    
    .nav-actions {
        gap: var(--spacing-xs);
    }
    
    .nav-btn {
        padding: var(--spacing-sm);
        min-width: 44px;
        justify-content: center;
    }
    
    .admin-badge {
        display: none;
    }
}

@media (max-width: 480px) {
    .brand-text {
        display: none;
    }
    
    .brand-icon {
        font-size: 1.75rem;
    }
}
</style>