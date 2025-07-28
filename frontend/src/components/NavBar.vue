<script setup>
import auth from "../api/auth.js";
import { ref, onMounted, onUpdated } from "vue";
import { useRouter } from 'vue-router'
import { useCart } from '@/composables/useCart.js'
const router = useRouter()
const { totalItems, reloadCart } = useCart()

const info = ref({
    isAdmin: false
})
const isAdminPanelExpanded = ref(false)

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
    reloadCart(); // Recarregar carrinho após logout para limpar dados do usuário anterior
    router.push('/')
}

function toggleAdminPanel() {
    isAdminPanelExpanded.value = !isAdminPanelExpanded.value
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
                        <div class="brand-icon-wrapper">
                            <svg class="brand-icon" width="24" height="24" viewBox="0 0 24 24" fill="none">
                                <path d="M9 3H15L17 8L12 12L7 8L9 3Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M12 12L17 16V20C17 20.5523 16.5523 21 16 21H8C7.44772 21 7 20.5523 7 20V16L12 12Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <circle cx="12" cy="8" r="2" stroke="currentColor" stroke-width="2"/>
                            </svg>
                        </div>
                        <div class="brand-content">
                            <span class="brand-text">Enanpharma</span>
                            <span v-if='info.isAdmin' class="admin-badge">ADMIN</span>
                        </div>
                    </div>
                </router-link>
            </div>
            
            <div class="navbar-center">
                <div v-if="auth.isLoggedIn()" class="user-welcome">
                    <router-link to="/profile" class="user-link">
                        <div class="user-avatar">
                            <svg class="user-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                            </svg>
                        </div>
                        <div class="user-info">
                            <span class="user-name">{{ auth.user }}</span>
                            <span class="user-label">Perfil</span>
                        </div>
                    </router-link>
                </div>
            </div>
            
            <div class="navbar-right">
                <div class="nav-actions">
                <button 
                    v-if="info.isAdmin" 
                    @click="toggleAdminPanel"
                     class="nav-btn admin-panel-btn"
                     :class="{ 'active': isAdminPanelExpanded }"
                    :aria-expanded="isAdminPanelExpanded"
                     aria-label="Toggle Admin Panel"
                 >
                    <div class="btn-icon-wrapper">
                        <svg class="btn-icon admin-icon" :class="{ 'rotated': isAdminPanelExpanded }" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M12 6.253V10C12 10.2652 12.1054 10.5196 12.2929 10.7071L15 13.414V16.5C15 16.776 14.776 17 14.5 17H13V15.5C13 15.224 12.776 15 12.5 15H11.5C11.224 15 11 15.224 11 15.5V17H9.5C9.224 17 9 16.776 9 16.5V13.414L11.7071 10.7071C11.8946 10.5196 12 10.2652 12 10V6.253C12.0856 6.253 12.1712 6.253 12.2568 6.253" stroke="currentColor" stroke-width="1.5"/>
                            <path d="M5 8L12 3L19 8V19C19 20.1046 18.1046 21 17 21H7C5.89543 21 5 20.1046 5 19V8Z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                    <span class="btn-text">Administrador</span>
                </button>
                    <router-link to="/cart" class="nav-btn cart-btn">
                        <div class="btn-icon-wrapper">
                            <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M9 22C9.55228 22 10 21.5523 10 21C10 20.4477 9.55228 20 9 20C8.44772 20 8 20.4477 8 21C8 21.5523 8.44772 22 9 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M20 22C20.5523 22 21 21.5523 21 21C21 20.4477 20.5523 20 20 20C19.4477 20 19 20.4477 19 21C19 21.5523 19.4477 22 20 22Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M1 1H5L7.68 14.39C7.77144 14.8504 8.02191 15.264 8.38755 15.5583C8.75318 15.8526 9.2107 16.009 9.68 16H19.4C19.8693 16.009 20.3268 15.8526 20.6925 15.5583C21.0581 15.264 21.3086 14.8504 21.4 14.39L23 6H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            <span v-if="totalItems > 0" class="cart-badge">{{ totalItems }}</span>
                        </div>
                        <span class="btn-text">Carrinho</span>
                    </router-link>
                    
                    <template v-if="auth.isLoggedIn()">
                        <router-link to="/orders" class="nav-btn orders-btn">
                            <div class="btn-icon-wrapper">
                                <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                    <path d="M14 2H6C5.46957 2 4.96086 2.21071 4.58579 2.58579C4.21071 2.96086 4 3.46957 4 4V20C4 20.5304 4.21071 21.0391 4.58579 21.4142C4.96086 21.7893 5.46957 22 6 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V8L14 2Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M14 2V8H20" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M16 13H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M16 17H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M10 9H9H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                            </div>
                            <span class="btn-text">Pedidos</span>
                        </router-link>
                        
                        
                        <button @click="logout" class="nav-btn logout-btn">
                            <div class="btn-icon-wrapper">
                                <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                    <path d="M9 21H5C4.46957 21 3.96086 20.7893 3.58579 20.4142C3.21071 20.0391 3 19.5304 3 19V5C3 4.46957 3.21071 3.96086 3.58579 3.58579C3.96086 3.21071 4.46957 3 5 3H9" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M16 17L21 12L16 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M21 12H9" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                            </div>
                            <span class="btn-text">Sair</span>
                        </button>
                    </template>
                    
                    <template v-else>
                        <router-link to="/login" class="nav-btn login-btn">
                            <div class="btn-icon-wrapper">
                                <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                    <path d="M15 3H19C19.5304 3 20.0391 3.21071 20.4142 3.58579C20.7893 3.96086 21 4.46957 21 5V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M10 17L15 12L10 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M15 12H3" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                            </div>
                            <span class="btn-text">Entrar</span>
                        </router-link>
                        <router-link to="/signup" class="nav-btn signup-btn">
                            <div class="btn-icon-wrapper">
                                <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                    <path d="M16 21V19C16 17.9391 15.5786 16.9217 14.8284 16.1716C14.0783 15.4214 13.0609 15.8 12 15.8H5C3.93913 15.8 2.92172 16.2214 2.17157 16.9716C1.42143 17.7217 1 18.7391 1 19.8V21.8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <circle cx="8.5" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                                    <path d="M20 8V14" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    <path d="M23 11H17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                </svg>
                            </div>
                            <span class="btn-text">Cadastrar</span>
                        </router-link>
                    </template>
                </div>
            </div>
        </div>
        
        <!-- Admin Panel Dropdown -->
        <transition name="admin-panel-slide">
            <div v-if="info.isAdmin && isAdminPanelExpanded" class="admin-panel-dropdown">
                <div class="admin-panel-content">
                    <div class="panel-header">
                        <h3 class="panel-title">Controles Administrativos</h3>
                        <p class="panel-subtitle">Gerencie aqui os recursos da Enanpharma</p>
                    </div>
                    
                    <nav class="admin-nav">
                        <div class="nav-section">
                            <h4 class="section-title">Genrenciamento de Conteúdo</h4>
                            <div class="nav-links">
                                <router-link to="/categories" class="nav-link" @click="isAdminPanelExpanded = false">
                                    <svg class="nav-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                    </svg>
                                    <span>Categorias</span>
                                </router-link>
                                
                                <router-link to="/products" class="nav-link" @click="isAdminPanelExpanded = false">
                                    <svg class="nav-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M20 7L12 3L4 7L12 11L20 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <path d="M4 7V17L12 21L20 17V7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    <span>Produtos</span>
                                </router-link>
                            </div>
                        </div>

                        <div class="nav-section">
                            <h4 class="section-title">Sistema</h4>
                            <div class="nav-links">
                                <router-link to="/admin/orders" class="nav-link" @click="isAdminPanelExpanded = false">
                                    <svg class="nav-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M9 11H15M9 15H15M17 21H7C5.89543 21 5 20.1046 5 19V5C5 3.89543 5.89543 3 7 3H12.5858C12.851 3 13.1054 3.10536 13.2929 3.29289L19.7071 9.70711C19.8946 9.89464 20 10.149 20 10.4142V19C20 20.1046 19.1046 21 18 21H17Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    <span>Pedidos</span>
                                </router-link>
                                
                                <router-link to="/admin/users" class="nav-link" @click="isAdminPanelExpanded = false">
                                    <svg class="nav-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                                    </svg>
                                    <span>Usuários</span>
                                </router-link>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </transition>
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
    transition: all var(--transition-fast);
}

.brand:hover {
    transform: translateY(-1px);
}

.brand-logo {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
}

.brand-icon-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
    background: rgba(115, 115, 115, 0.15);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    transition: all var(--transition-fast);
}

.brand-icon-wrapper:hover {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.brand-icon {
    color: var(--text-secondary);
    transition: color var(--transition-fast);
}

.brand:hover .brand-icon {
    color: var(--text-primary);
}

.brand-content {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xs);
}

.brand-text {
    font-size: 1.25rem;
    font-weight: 600;
    color: var(--text-primary);
    line-height: 1.2;
}

.admin-badge {
    background: rgba(115, 115, 115, 0.3);
    color: var(--text-primary);
    border: 1px solid var(--border-medium);
    padding: 2px var(--spacing-xs);
    border-radius: var(--radius-sm);
    font-size: 0.625rem;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    width: fit-content;
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
    background: rgba(115, 115, 115, 0.1);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    color: var(--text-secondary);
    text-decoration: none;
    transition: all var(--transition-fast);
}

.user-link:hover {
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
    transform: translateY(-1px);
}

.user-avatar {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 32px;
    height: 32px;
    background: rgba(115, 115, 115, 0.2);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    transition: all var(--transition-fast);
}

.user-link:hover .user-avatar {
    background: rgba(115, 115, 115, 0.3);
    border-color: var(--border-medium);
}

.user-icon {
    color: currentColor;
}

.user-info {
    display: flex;
    flex-direction: column;
    gap: 1px;
}

.user-name {
    font-weight: 500;
    font-size: 0.875rem;
    color: currentColor;
}

.user-label {
    font-size: 0.75rem;
    color: var(--text-muted);
    opacity: 0.8;
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
    gap: var(--spacing-sm);
    padding: var(--spacing-sm) var(--spacing-md);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    background: rgba(115, 115, 115, 0.1);
    color: var(--text-secondary);
    text-decoration: none;
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
}

.nav-btn:hover {
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
    background: rgba(115, 115, 115, 0.15);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    transition: all var(--transition-fast);
}

.nav-btn:hover .btn-icon-wrapper {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.btn-icon {
    color: currentColor;
    transition: color var(--transition-fast);
}

.btn-text {
    display: none;
    color: currentColor;
    font-weight: 500;
}

/* Specific button style adjustments */
.cart-btn {
    background: rgba(16, 185, 129, 0.15);
    border-color: rgba(16, 185, 129, 0.3);
    color: rgba(16, 185, 129, 0.9);
}

.cart-btn:hover {
    background: rgba(16, 185, 129, 0.25);
    border-color: rgba(16, 185, 129, 0.5);
    color: rgba(16, 185, 129, 1);
}

.cart-btn .btn-icon-wrapper {
    background: rgba(16, 185, 129, 0.2);
    border-color: rgba(16, 185, 129, 0.3);
}

.cart-btn:hover .btn-icon-wrapper {
    background: rgba(16, 185, 129, 0.3);
    border-color: rgba(16, 185, 129, 0.5);
}

.orders-btn {
    background: rgba(6, 182, 212, 0.15);
    border-color: rgba(6, 182, 212, 0.3);
    color: rgba(6, 182, 212, 0.9);
}

.orders-btn:hover {
    background: rgba(6, 182, 212, 0.25);
    border-color: rgba(6, 182, 212, 0.5);
    color: rgba(6, 182, 212, 1);
}

.orders-btn .btn-icon-wrapper {
    background: rgba(6, 182, 212, 0.2);
    border-color: rgba(6, 182, 212, 0.3);
}

.orders-btn:hover .btn-icon-wrapper {
    background: rgba(6, 182, 212, 0.3);
    border-color: rgba(6, 182, 212, 0.5);
}

.login-btn {
    background: rgba(59, 130, 246, 0.15);
    border-color: rgba(59, 130, 246, 0.3);
    color: rgba(59, 130, 246, 0.9);
}

.login-btn:hover {
    background: rgba(59, 130, 246, 0.25);
    border-color: rgba(59, 130, 246, 0.5);
    color: rgba(59, 130, 246, 1);
}

.login-btn .btn-icon-wrapper {
    background: rgba(59, 130, 246, 0.2);
    border-color: rgba(59, 130, 246, 0.3);
}

.login-btn:hover .btn-icon-wrapper {
    background: rgba(59, 130, 246, 0.3);
    border-color: rgba(59, 130, 246, 0.5);
}

.signup-btn {
    background: rgba(115, 115, 115, 0.05);
    border-color: var(--border-light);
    color: var(--text-secondary);
}

.signup-btn:hover {
    background: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
    color: var(--text-primary);
}

.logout-btn {
    background: rgba(239, 68, 68, 0.15);
    border-color: rgba(239, 68, 68, 0.3);
    color: rgba(239, 68, 68, 0.9);
}

.logout-btn:hover {
    background: rgba(239, 68, 68, 0.25);
    border-color: rgba(239, 68, 68, 0.5);
    color: rgba(239, 68, 68, 1);
}

.logout-btn .btn-icon-wrapper {
    background: rgba(239, 68, 68, 0.2);
    border-color: rgba(239, 68, 68, 0.3);
}

.logout-btn:hover .btn-icon-wrapper {
    background: rgba(239, 68, 68, 0.3);
    border-color: rgba(239, 68, 68, 0.5);
}

/* Cart Badge */
.cart-badge {
    position: absolute;
    top: -6px;
    right: -6px;
    background: rgba(239, 68, 68, 0.9);
    color: white;
    border-radius: 50%;
    padding: 2px 6px;
    font-size: 0.65rem;
    font-weight: 600;
    min-width: 18px;
    text-align: center;
    line-height: 1;
    border: 2px solid rgba(10, 10, 10, 0.9);
    backdrop-filter: blur(10px);
    animation: subtle-pulse 2s infinite;
}

@keyframes subtle-pulse {
    0% {
        box-shadow: 0 0 0 0 rgba(239, 68, 68, 0.4);
    }
    70% {
        box-shadow: 0 0 0 6px rgba(239, 68, 68, 0);
    }
    100% {
        box-shadow: 0 0 0 0 rgba(239, 68, 68, 0);
    }
}

/* Admin Panel Button */
.admin-panel-btn {
    background: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
    color: var(--text-muted);
    position: relative;
}

.admin-panel-btn:hover {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-heavy);
    color: var(--text-primary);
}

.admin-panel-btn.active {
    background: rgba(115, 115, 115, 0.3);
    border-color: var(--border-heavy);
    color: var(--text-primary);
}

.admin-icon {
    transition: transform var(--transition-normal);
}

.admin-icon.rotated {
    transform: rotate(180deg);
}

/* Admin Panel Dropdown */
.admin-panel-dropdown {
    position: absolute;
    top: 100%;
    right: 0;
    left: 0;
    z-index: 999;
    background: rgba(26, 26, 26, 0.98);
    backdrop-filter: blur(15px);
    border-bottom: 1px solid var(--border-light);
    box-shadow: var(--shadow-lg);
}

.admin-panel-content {
    max-width: 1200px;
    margin: 0 auto;
    padding: var(--spacing-lg);
}

.panel-header {
    text-align: center;
    margin-bottom: var(--spacing-lg);
    padding-bottom: var(--spacing-lg);
    border-bottom: 1px solid var(--border-light);
}

.panel-title {
    font-size: 1.125rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0 0 var(--spacing-xs) 0;
}

.panel-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
}

.admin-nav {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: var(--spacing-xl);
}

.nav-section {
    background: rgba(38, 38, 38, 0.5);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
}

.section-title {
    font-size: 0.875rem;
    font-weight: 600;
    color: var(--text-muted);
    text-transform: uppercase;
    letter-spacing: 0.05em;
    margin: 0 0 var(--spacing-md) 0;
    padding-bottom: var(--spacing-sm);
    border-bottom: 1px solid var(--border-light);
}

.nav-links {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-sm);
}

.nav-link {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md);
    color: var(--text-secondary);
    text-decoration: none;
    border-radius: var(--radius-md);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    border: 1px solid transparent;
    background: rgba(20, 20, 20, 0.3);
}

.nav-link:hover {
    background: rgba(64, 64, 64, 0.4);
    color: var(--text-primary);
    border-color: var(--border-medium);
    transform: translateY(-1px);
}

.nav-link.router-link-active {
    background: rgba(82, 82, 82, 0.5);
    color: var(--text-primary);
    border-color: var(--border-heavy);
}

.nav-icon {
    color: currentColor;
    flex-shrink: 0;
}

/* Admin Panel Slide Animation */
.admin-panel-slide-enter-active,
.admin-panel-slide-leave-active {
    transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.admin-panel-slide-enter-from {
    opacity: 0;
    transform: translateY(-20px);
}

.admin-panel-slide-leave-to {
    opacity: 0;
    transform: translateY(-20px);
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
    
    .brand-icon-wrapper {
        width: 36px;
        height: 36px;
    }
    
    .brand-text {
        font-size: 1.125rem;
    }
    
    .admin-badge {
        display: none;
    }
    
    .nav-actions {
        gap: var(--spacing-xs);
    }
    
    .nav-btn {
        padding: var(--spacing-sm);
        min-width: 44px;
        justify-content: center;
        gap: 0;
    }
    
    .btn-icon-wrapper {
        width: 32px;
        height: 32px;
    }
    
    /* Admin Panel Mobile Styles */
    .admin-panel-content {
        padding: var(--spacing-md);
    }
    
    .admin-nav {
        grid-template-columns: 1fr;
        gap: var(--spacing-md);
    }
    
    .nav-section {
        padding: var(--spacing-md);
    }
    
    .nav-link {
        padding: var(--spacing-sm) var(--spacing-md);
        font-size: 0.8rem;
    }
}

@media (max-width: 480px) {
    .brand-content {
        display: none;
    }
    
    .brand-icon-wrapper {
        width: 40px;
        height: 40px;
    }
    
    .nav-btn {
        min-width: 40px;
        padding: var(--spacing-xs);
    }
    
    .btn-icon-wrapper {
        width: 28px;
        height: 28px;
    }
}
</style>
