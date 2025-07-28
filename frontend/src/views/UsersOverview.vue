<template>
    <div class="users-overview-page">
        <div class="page-header">
            <div class="header-content">
                <div class="title-section">
                    <svg class="page-icon" width="24" height="24" viewBox="0 0 24 24" fill="none">
                        <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                    </svg>
                    <div class="title-content">
                        <h1 class="page-title">Gerenciamento de Usuários</h1>
                        <p class="page-subtitle">Administre contas de usuários e permissões do sistema</p>
                    </div>
                </div>
                
                <div class="header-stats">
                    <div class="stat-card">
                        <div class="stat-icon admin">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M12 6.253V10C12 10.2652 12.1054 10.5196 12.2929 10.7071L15 13.414V16.5C15 16.776 14.776 17 14.5 17H13V15.5C13 15.224 12.776 15 12.5 15H11.5C11.224 15 11 15.224 11 15.5V17H9.5C9.224 17 9 16.776 9 16.5V13.414L11.7071 10.7071C11.8946 10.5196 12 10.2652 12 10V6.253" stroke="currentColor" stroke-width="1.5"/>
                                <path d="M5 8L12 3L19 8V19C19 20.1046 18.1046 21 17 21H7C5.89543 21 5 20.1046 5 19V8Z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-number">{{ getAdminCount() }}</span>
                            <span class="stat-label">Admins</span>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon total">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M17 21V19C17 17.9391 16.5786 16.9217 15.8284 16.1716C15.0783 15.4214 14.0609 15 13 15H5C3.93913 15 2.92172 15.4214 2.17157 16.1716C1.42143 16.9217 1 17.9391 1 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <circle cx="9" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                                <path d="M23 21V19C23 18.1636 22.7078 17.3966 22.2124 16.7999" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M16 3.12988C16.8604 3.51796 17.623 4.08346 18.2376 4.78702C18.8522 5.49058 19.3036 6.31305 19.5649 7.20146C19.8262 8.08987 19.8913 9.02702 19.7557 9.94616C19.6201 10.8653 19.2879 11.7429 18.7847 12.5194" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </div>
                        <div class="stat-content">
                            <span class="stat-number">{{ users.length }}</span>
                            <span class="stat-label">Total</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="page-content">
            <div class="filters-section" v-if="users.length > 0">
                <div class="filters-content">
                    <div class="filter-group">
                        <label class="filter-label">
                            <svg class="filter-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <circle cx="11" cy="11" r="8" stroke="currentColor" stroke-width="2"/>
                                <path d="m21 21-4.35-4.35" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            Buscar por Nome
                        </label>
                        <input 
                            v-model="nameFilter" 
                            type="text" 
                            class="filter-input"
                            placeholder="Digite o nome do usuário..."
                        />
                    </div>
                    
                    <div class="filter-group">
                        <label class="filter-label">
                            <svg class="filter-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                                <path d="M12 6.253V10C12 10.2652 12.1054 10.5196 12.2929 10.7071L15 13.414V16.5C15 16.776 14.776 17 14.5 17H13V15.5C13 15.224 12.776 15 12.5 15H11.5C11.224 15 11 15.224 11 15.5V17H9.5C9.224 17 9 16.776 9 16.5V13.414L11.7071 10.7071C11.8946 10.5196 12 10.2652 12 10V6.253" stroke="currentColor" stroke-width="1.5"/>
                                <path d="M5 8L12 3L19 8V19C19 20.1046 18.1046 21 17 21H7C5.89543 21 5 20.1046 5 19V8Z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                            Filtrar por Função
                        </label>
                        <select v-model="roleFilter" class="filter-select">
                            <option value="">Todas</option>
                            <option value="ADMIN">Admin</option>
                            <option value="USER">Usuário</option>
                        </select>
                    </div>
                    
                    <div class="results-summary">
                        <span class="results-count">
                            {{ filteredUsers.length }} de {{ users.length }} usuário{{ filteredUsers.length !== 1 ? 's' : '' }}
                        </span>
                    </div>
                </div>
            </div>
            
            <div class="actions-bar">
                <button @click="showCreateModal = true" class="create-btn">
                    <div class="btn-icon-wrapper">
                        <svg class="btn-icon" width="18" height="18" viewBox="0 0 24 24" fill="none">
                            <path d="M12 5V19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                    <span class="btn-text">Novo Usuário</span>
                </button>
            </div>
            
            <UsersList 
                ref="usersListRef" 
                @users-loaded="handleUsersLoaded" 
                @user-updated="handleUserUpdated"
                @user-deleted="handleUserDeleted"
                :role-filter="roleFilter"
                :sort-order="sortOrder"
                :users="filteredUsers"
            />
        </div>

        <!-- Create User Modal -->
        <div v-if="showCreateModal" class="modal-overlay" @click="closeCreateModal">
            <div class="modal-content" @click.stop>
                <div class="modal-header">
                    <h2 class="modal-title">Criar Novo Usuário</h2>
                    <button @click="closeCreateModal" class="close-btn">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                            <line x1="18" y1="6" x2="6" y2="18" stroke="currentColor" stroke-width="2"/>
                            <line x1="6" y1="6" x2="18" y2="18" stroke="currentColor" stroke-width="2"/>
                        </svg>
                    </button>
                </div>
                
                <form @submit.prevent="createUser" class="modal-form">
                    <div class="form-group">
                        <label>Nome de Usuário *</label>
                        <input 
                            v-model="newUser.username" 
                            type="text" 
                            required 
                            placeholder="Digite o nome de usuário"
                        />
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>Nome *</label>
                            <input 
                                v-model="newUser.firstName" 
                                type="text" 
                                required 
                                placeholder="Nome"
                            />
                        </div>
                        <div class="form-group">
                            <label>Sobrenome *</label>
                            <input 
                                v-model="newUser.lastName" 
                                type="text" 
                                required 
                                placeholder="Sobrenome"
                            />
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>Email *</label>
                        <input 
                            v-model="newUser.email" 
                            type="email" 
                            required 
                            placeholder="usuario@email.com"
                        />
                    </div>
                    
                    <div class="form-group">
                        <label>Senha *</label>
                        <input 
                            v-model="newUser.password" 
                            type="password" 
                            required 
                            placeholder="Digite a senha"
                        />
                    </div>
                    
                    <div class="form-group">
                        <label>Função</label>
                        <select v-model="newUser.roles">
                            <option value="USER">Usuário</option>
                            <option value="ADMIN">Administrador</option>
                        </select>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" @click="closeCreateModal" class="btn-secondary">
                            Cancelar
                        </button>
                        <button type="submit" :disabled="isCreating" class="btn-primary">
                            {{ isCreating ? 'Criando...' : 'Criar Usuário' }}
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Error/Success Messages -->
        <div v-if="message" class="message" :class="{ 'error': messageType === 'error', 'success': messageType === 'success' }">
            {{ message }}
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import UsersList from '../components/UsersList.vue'
import { getAllUsers, createUser as createUserAPI } from '@/api/users.js'

const users = ref([])
const nameFilter = ref('')
const roleFilter = ref('')
const sortOrder = ref('desc')
const usersListRef = ref(null)
const showCreateModal = ref(false)
const isCreating = ref(false)
const message = ref('')
const messageType = ref('')

const newUser = ref({
    username: '',
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    roles: 'USER'
})

const filteredUsers = computed(() => {
    let filtered = [...users.value]
    
    // Filter by name
    if (nameFilter.value) {
        const searchTerm = nameFilter.value.toLowerCase().trim()
        filtered = filtered.filter(user => {
            const fullName = `${user.firstName || ''} ${user.lastName || ''}`.toLowerCase()
            const username = (user.username || '').toLowerCase()
            return fullName.includes(searchTerm) || username.includes(searchTerm)
        })
    }
    
    // Filter by role
    if (roleFilter.value) {
        filtered = filtered.filter(user => 
            user.roles && user.roles.includes(roleFilter.value)
        )
    }
    
    return filtered
})

function handleUsersLoaded(loadedUsers) {
    users.value = loadedUsers
}

function handleUserUpdated(updatedUser) {
    const index = users.value.findIndex(user => user.id === updatedUser.id)
    if (index !== -1) {
        users.value[index] = updatedUser
    }
}

function handleUserDeleted(userId) {
    users.value = users.value.filter(user => user.id !== userId)
}

function getAdminCount() {
    return users.value.filter(user => 
        user.roles && user.roles.includes('ADMIN')
    ).length
}

function closeCreateModal() {
    showCreateModal.value = false
    newUser.value = {
        username: '',
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        roles: 'USER'
    }
}

async function createUser() {
    if (isCreating.value) return
    
    isCreating.value = true
    message.value = ''
    
    try {
        const userData = {
            ...newUser.value,
            roles: [newUser.value.roles]
        }
        
        const createdUser = await createUserAPI(userData)
        users.value.push(createdUser)
        
        message.value = 'Usuário criado com sucesso!'
        messageType.value = 'success'
        closeCreateModal()
        
        setTimeout(() => {
            message.value = ''
        }, 3000)
    } catch (error) {
        message.value = error.response?.data?.message || 'Erro ao criar usuário'
        messageType.value = 'error'
        
        setTimeout(() => {
            message.value = ''
        }, 5000)
    } finally {
        isCreating.value = false
        loadedUsers()
    }
}

async function loadUsers() {
    try {
        const loadedUsers = await getAllUsers()
        users.value = loadedUsers
    } catch (error) {
        console.error('Error loading users:', error)
        message.value = 'Erro ao carregar usuários'
        messageType.value = 'error'
    }
}

onMounted(() => {
    loadUsers()
})
</script>

<style scoped>
.users-overview-page {
    width: 100%;
    max-width: 1600px;
    margin: 0 auto;
    padding: var(--spacing-xl);
    background: var(--primary-bg);
}

/* Page Header */
.page-header {
    background: rgba(26, 26, 26, 0.98);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-xl);
    margin-bottom: var(--spacing-xl);
    backdrop-filter: blur(15px);
    transition: all var(--transition-normal);
    box-shadow: var(--shadow-lg);
}

.page-header:hover {
    border-color: var(--border-medium);
}

.header-content {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: var(--spacing-2xl);
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
    letter-spacing: -0.025em;
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
    background: rgba(38, 38, 38, 0.6);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    transition: all var(--transition-fast);
}

.stat-card:hover {
    background: rgba(48, 48, 48, 0.8);
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

.stat-icon.active {
    background: rgba(115, 115, 115, 0.2);
    color: var(--text-primary);
    border-color: rgba(115, 115, 115, 0.4);
}

.stat-icon.inactive {
    background: rgba(80, 80, 80, 0.2);
    color: rgba(160, 160, 160, 0.9);
    border-color: rgba(80, 80, 80, 0.4);
}

.stat-icon.admin {
    background: rgba(120, 120, 120, 0.2);
    color: rgba(200, 200, 200, 0.9);
    border-color: rgba(120, 120, 120, 0.4);
}

.stat-icon.total {
    background: rgba(100, 100, 100, 0.2);
    color: rgba(180, 180, 180, 0.9);
    border-color: rgba(100, 100, 100, 0.4);
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
    background: rgba(38, 38, 38, 0.5);
    backdrop-filter: blur(15px);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    padding: var(--spacing-lg);
    margin-bottom: var(--spacing-lg);
    transition: all var(--transition-fast);
}

.filters-section:hover {
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
    background: rgba(20, 20, 20, 0.6);
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
    background-color: rgba(32, 32, 32, 0.8);
    border-color: var(--border-medium);
}

.filter-select:focus {
    outline: none;
    border-color: var(--border-heavy);
    box-shadow: 0 0 0 3px rgba(115, 115, 115, 0.1);
}

.filter-input {
    appearance: none;
    background: rgba(20, 20, 20, 0.6);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    padding: var(--spacing-sm) var(--spacing-md);
    color: var(--text-primary);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    min-width: 200px;
}

.filter-input:hover {
    background-color: rgba(32, 32, 32, 0.8);
    border-color: var(--border-medium);
}

.filter-input:focus {
    outline: none;
    border-color: var(--border-heavy);
    background: rgba(32, 32, 32, 0.8);
    box-shadow: 0 0 0 3px rgba(115, 115, 115, 0.1);
}

.filter-input::placeholder {
    color: var(--text-muted);
    font-weight: 400;
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
    background: rgba(20, 20, 20, 0.6);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
}

/* Actions Bar */
.actions-bar {
    display: flex;
    justify-content: flex-end;
    margin-bottom: var(--spacing-xl);
}

.create-btn {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
    padding: var(--spacing-lg);
    background: rgba(139, 69, 19, 0.9);
    border: 1px solid rgba(139, 69, 19, 1);
    border-radius: var(--radius-lg);
    color: white;
    font-weight: 600;
    cursor: pointer;
    transition: all var(--transition-fast);
    text-decoration: none;
    box-shadow: 0 4px 12px rgba(139, 69, 19, 0.3);
    min-width: 200px;
}

.create-btn:hover {
    background: rgba(139, 69, 19, 1);
    transform: translateY(-2px) scale(1.02);
    box-shadow: 0 8px 20px rgba(139, 69, 19, 0.4);
}

.create-btn:active {
    transform: translateY(0) scale(1);
    box-shadow: 0 4px 12px rgba(139, 69, 19, 0.3);
}

.btn-icon-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 40px;
    height: 40px;
    background: rgba(255, 255, 255, 0.15);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: var(--radius-lg);
    transition: all var(--transition-fast);
    backdrop-filter: blur(10px);
}

.create-btn:hover .btn-icon-wrapper {
    background: rgba(255, 255, 255, 0.25);
    border-color: rgba(255, 255, 255, 0.3);
    transform: rotate(90deg);
}

.btn-icon {
    color: white;
    filter: drop-shadow(0 1px 2px rgba(0, 0, 0, 0.2));
}

.btn-text {
    font-size: 1rem;
    font-weight: 600;
    color: white;
    line-height: 1.2;
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

/* Page Content */
.page-content {
    width: 100%;
}

/* Modal Styles */
.modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.7);
    backdrop-filter: blur(5px);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
    padding: var(--spacing-lg);
}

.modal-content {
    background: rgba(26, 26, 26, 0.95);
    backdrop-filter: blur(20px);
    border: 1px solid var(--border-medium);
    border-radius: var(--radius-xl);
    padding: var(--spacing-2xl);
    width: 50%;
    max-height: 90vh;
    overflow-y: auto;
    box-shadow: var(--shadow-lg);
}

.modal-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: var(--spacing-xl);
    padding-bottom: var(--spacing-lg);
    border-bottom: 1px solid var(--border-light);
}

.modal-title {
    font-size: 1.25rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0;
}

.close-btn {
    background: none;
    border: none;
    color: var(--text-muted);
    cursor: pointer;
    padding: var(--spacing-xs);
    border-radius: var(--radius-md);
    transition: all var(--transition-fast);
}

.close-btn:hover {
    color: var(--text-primary);
    background: rgba(115, 115, 115, 0.1);
}

.modal-form {
    display: flex;
    width: 100%;
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
    background: rgba(38, 38, 38, 0.6);
    color: var(--text-primary);
    font-size: 0.875rem;
    transition: all var(--transition-fast);
}

.form-group input:focus,
.form-group select:focus {
    outline: none;
    border-color: rgba(139, 69, 19, 0.6);
    background: rgba(48, 48, 48, 0.8);
    box-shadow: 0 0 0 3px rgba(139, 69, 19, 0.1);
}

.form-group input::placeholder {
    color: var(--text-muted);
}

.form-row {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: var(--spacing-lg);
}

.modal-actions {
    display: flex;
    gap: var(--spacing-md);
    justify-content: flex-end;
    margin-top: var(--spacing-lg);
    padding-top: var(--spacing-lg);
    border-top: 1px solid var(--border-light);
}

.btn-primary,
.btn-secondary {
    padding: var(--spacing-md) var(--spacing-xl);
    border-radius: var(--radius-md);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
}

.btn-primary {
    border: 1px solid rgba(139, 69, 19, 0.5);
    background: rgba(139, 69, 19, 0.15);
    color: rgba(139, 69, 19, 0.9);
}

.btn-primary:hover:not(:disabled) {
    transform: translateY(-1px);
    background: rgba(139, 69, 19, 0.25);
    border-color: rgba(139, 69, 19, 0.7);
    color: rgba(139, 69, 19, 1);
    box-shadow: var(--shadow-sm);
}

.btn-primary:disabled {
    opacity: 0.5;
    cursor: not-allowed;
    transform: none;
}

.btn-secondary {
    border: 1px solid var(--border-light);
    background: rgba(38, 38, 38, 0.6);
    color: var(--text-secondary);
}

.btn-secondary:hover {
    transform: translateY(-1px);
    background: rgba(48, 48, 48, 0.8);
    border-color: var(--border-medium);
    color: var(--text-primary);
    box-shadow: var(--shadow-sm);
}

/* Messages */
.message {
    position: fixed;
    top: var(--spacing-xl);
    right: var(--spacing-xl);
    padding: var(--spacing-lg);
    border-radius: var(--radius-lg);
    font-size: 0.875rem;
    font-weight: 500;
    z-index: 1001;
    animation: slideIn 0.3s ease-out;
}

.message.success {
    background: rgba(16, 185, 129, 0.9);
    border: 1px solid rgba(16, 185, 129, 1);
    color: white;
}

.message.error {
    background: rgba(239, 68, 68, 0.9);
    border: 1px solid rgba(239, 68, 68, 1);
    color: white;
}

@keyframes slideIn {
    from {
        transform: translateX(100%);
        opacity: 0;
    }
    to {
        transform: translateX(0);
        opacity: 1;
    }
}
</style>
