<template>
    <div class="users-list">
        <div v-if="loading" class="loading-state">
            <div class="loading-spinner">
                <svg class="spinner" width="24" height="24" viewBox="0 0 24 24" fill="none">
                    <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2" stroke-dasharray="31.416" stroke-dashoffset="31.416">
                        <animate attributeName="stroke-dasharray" dur="2s" values="0 31.416;15.708 15.708;0 31.416" repeatCount="indefinite"/>
                        <animate attributeName="stroke-dashoffset" dur="2s" values="0;-15.708;-31.416" repeatCount="indefinite"/>
                    </circle>
                </svg>
            </div>
            <p class="loading-text">Carregando usuários...</p>
        </div>

        <div v-else-if="error" class="error-state">
            <svg class="error-icon" width="48" height="48" viewBox="0 0 24 24" fill="none">
                <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                <line x1="15" y1="9" x2="9" y2="15" stroke="currentColor" stroke-width="2"/>
                <line x1="9" y1="9" x2="15" y2="15" stroke="currentColor" stroke-width="2"/>
            </svg>
            <h3 class="error-title">Erro ao carregar usuários</h3>
            <p class="error-message">{{ error }}</p>
            <button @click="loadUsers" class="retry-btn">
                <div class="btn-icon-wrapper">
                    <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M1 4V10H7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M23 20V14H17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M20.49 9C20.0288 7.38497 19.1243 5.9417 17.8747 4.82465C16.6251 3.7076 15.0799 2.95811 13.4222 2.66979C11.7645 2.38147 10.0607 2.56699 8.49248 3.20582C6.92426 3.84465 5.55878 4.91095 4.55 6.29" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M3.51 15C3.97116 16.615 4.87571 18.0583 6.12531 19.1754C7.3749 20.2924 8.92009 21.0419 10.5778 21.3302C12.2355 21.6185 13.9393 21.433 15.5075 20.7942C17.0757 20.1554 18.4412 19.0891 19.45 17.71" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </div>
                <span class="btn-text">Tentar Novamente</span>
            </button>
        </div>

        <div v-else-if="users.length === 0" class="empty-state">
            <svg class="empty-icon" width="64" height="64" viewBox="0 0 24 24" fill="none">
                <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
            </svg>
            <h3 class="empty-title">Nenhum usuário encontrado</h3>
            <p class="empty-message">Não há usuários cadastrados no sistema ainda.</p>
        </div>

        <div v-else class="users-grid">
            <div 
                v-for="user in users" 
                :key="user.id" 
                class="user-card"
            >
                <div class="user-header">
                    <div class="user-avatar">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                            <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <circle cx="12" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                        </svg>
                    </div>
                    <div class="user-info">
                        <h3 class="user-name">{{ user.firstName }} {{ user.lastName }}</h3>
                        <p class="user-username">@{{ user.username }}</p>
                    </div>
                </div>

                <div class="user-details">
                    <div class="detail-item">
                        <svg class="detail-icon" width="14" height="14" viewBox="0 0 24 24" fill="none">
                            <path d="M4 4H20C21.1 4 22 4.9 22 6V18C22 19.1 21.1 20 20 20H4C2.9 20 2 19.1 2 18V6C2 4.9 2.9 4 4 4Z" stroke="currentColor" stroke-width="2"/>
                            <polyline points="22,6 12,13 2,6" stroke="currentColor" stroke-width="2"/>
                        </svg>
                        <span class="detail-text">{{ user.email }}</span>
                    </div>
                    
                    <div class="detail-item">
                        <svg class="detail-icon" width="14" height="14" viewBox="0 0 24 24" fill="none">
                            <path d="M12 6.253V10C12 10.2652 12.1054 10.5196 12.2929 10.7071L15 13.414V16.5C15 16.776 14.776 17 14.5 17H13V15.5C13 15.224 12.776 15 12.5 15H11.5C11.224 15 11 15.224 11 15.5V17H9.5C9.224 17 9 16.776 9 16.5V13.414L11.7071 10.7071C11.8946 10.5196 12 10.2652 12 10V6.253" stroke="currentColor" stroke-width="1.5"/>
                            <path d="M5 8L12 3L19 8V19C19 20.1046 18.1046 21 17 21H7C5.89543 21 5 20.1046 5 19V8Z" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                        <span class="detail-text">
                            <span 
                                v-for="(role, index) in user.roles" 
                                :key="role"
                                class="role-tag"
                                :class="{ 'admin': role === 'ADMIN', 'user': role === 'USER' }"
                            >
                                {{ role === 'ADMIN' ? 'Admin' : 'Usuário' }}{{ index < user.roles.length - 1 ? ', ' : '' }}
                            </span>
                        </span>
                    </div>
                    
                    <div class="detail-item" v-if="user.createdAt">
                        <svg class="detail-icon" width="14" height="14" viewBox="0 0 24 24" fill="none">
                            <rect x="3" y="4" width="18" height="18" rx="2" ry="2" stroke="currentColor" stroke-width="2"/>
                            <line x1="16" y1="2" x2="16" y2="6" stroke="currentColor" stroke-width="2"/>
                            <line x1="8" y1="2" x2="8" y2="6" stroke="currentColor" stroke-width="2"/>
                            <line x1="3" y1="10" x2="21" y2="10" stroke="currentColor" stroke-width="2"/>
                        </svg>
                        <span class="detail-text">Criado em {{ formatDate(user.createdAt) }}</span>
                    </div>
                </div>

                <div class="user-actions">
                    <button 
                        @click="openEditModal(user)" 
                        class="action-btn edit-btn"
                        aria-label="Editar usuário"
                        title="Editar usuário"
                    >
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M11 4H4C3.46957 4 2.96086 4.21071 2.58579 4.58579C2.21071 4.96086 2 5.46957 2 6V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V13" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M18.5 2.50001C18.8978 2.10219 19.4374 1.87869 20 1.87869C20.5626 1.87869 21.1022 2.10219 21.5 2.50001C21.8978 2.89784 22.1213 3.4374 22.1213 4.00001C22.1213 4.56262 21.8978 5.10219 21.5 5.50001L12 15L8 16L9 12L18.5 2.50001Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </button>

                    <button 
                        @click="confirmDelete(user)" 
                        class="action-btn delete-btn"
                        :disabled="actionLoading[user.id]"
                        aria-label="Excluir usuário"
                        title="Excluir usuário"
                    >
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M3 6H5H21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6H19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </button>
                </div>
            </div>
        </div>

        <!-- Edit User Modal -->
        <div v-if="showEditModal && editingUser" class="modal-overlay" @click="closeEditModal">
            <div class="modal-content" @click.stop>
                <div class="modal-header">
                    <h2 class="modal-title">Editar Usuário</h2>
                    <button @click="closeEditModal" class="close-btn">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                            <line x1="18" y1="6" x2="6" y2="18" stroke="currentColor" stroke-width="2"/>
                            <line x1="6" y1="6" x2="18" y2="18" stroke="currentColor" stroke-width="2"/>
                        </svg>
                    </button>
                </div>
                
                <form @submit.prevent="updateUser" class="modal-form">
                    <div class="form-group">
                        <label>Nome de Usuário *</label>
                        <input 
                            v-model="editingUser.username" 
                            type="text" 
                            required 
                            placeholder="Digite o nome de usuário"
                        />
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label>Nome *</label>
                            <input 
                                v-model="editingUser.firstName" 
                                type="text" 
                                required 
                                placeholder="Nome"
                            />
                        </div>
                        <div class="form-group">
                            <label>Sobrenome *</label>
                            <input 
                                v-model="editingUser.lastName" 
                                type="text" 
                                required 
                                placeholder="Sobrenome"
                            />
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>Email *</label>
                        <input 
                            v-model="editingUser.email" 
                            type="email" 
                            required 
                            placeholder="usuario@email.com"
                        />
                    </div>
                    
                    <div class="form-group">
                        <label>Função</label>
                        <select v-model="editingUser.mainRole">
                            <option value="USER">Usuário</option>
                            <option value="ADMIN">Administrador</option>
                        </select>
                    </div>
                    
                    <div class="modal-actions">
                        <button type="button" @click="closeEditModal" class="btn-secondary">
                            Cancelar
                        </button>
                        <button type="submit" :disabled="isUpdating" class="btn-primary">
                            {{ isUpdating ? 'Salvando...' : 'Salvar Alterações' }}
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Delete Confirmation Modal -->
        <div v-if="showDeleteModal" class="modal-overlay" @click="closeDeleteModal">
            <div class="modal-content" @click.stop>
                <div class="modal-header">
                    <h2 class="modal-title">Confirmar Exclusão</h2>
                    <button @click="closeDeleteModal" class="close-btn">
                        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
                            <line x1="18" y1="6" x2="6" y2="18" stroke="currentColor" stroke-width="2"/>
                            <line x1="6" y1="6" x2="18" y2="18" stroke="currentColor" stroke-width="2"/>
                        </svg>
                    </button>
                </div>
                
                <div class="delete-content">
                    <div class="delete-icon">
                        <svg width="48" height="48" viewBox="0 0 24 24" fill="none">
                            <polyline points="3,6 5,6 21,6" stroke="currentColor" stroke-width="2"/>
                            <path d="M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6" stroke="currentColor" stroke-width="2"/>
                            <path d="M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6" stroke="currentColor" stroke-width="2"/>
                        </svg>
                    </div>
                    <h3 class="delete-title">Tem certeza?</h3>
                    <p class="delete-message">
                        Você está prestes a excluir o usuário <strong>{{ userToDelete?.firstName }} {{ userToDelete?.lastName }}</strong>.
                        Esta ação não pode ser desfeita.
                    </p>
                    
                    <div class="modal-actions">
                        <button @click="closeDeleteModal" class="btn-secondary">
                            Cancelar
                        </button>
                        <button @click="deleteUser" :disabled="isDeleting" class="btn-delete">
                            {{ isDeleting ? 'Excluindo...' : 'Excluir Usuário' }}
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, watch, reactive } from 'vue'
import { getAllUsers, updateUser as updateUserAPI, deleteUser as deleteUserAPI } from '@/api/users.js'

const props = defineProps({
    roleFilter: String,
    sortOrder: String,
    users: {
        type: Array,
        default: () => []
    }
})

const emit = defineEmits(['users-loaded', 'user-updated', 'user-deleted'])

const loading = ref(true)
const error = ref('')
const allUsers = ref([])
const actionLoading = reactive({})

// Edit modal
const showEditModal = ref(false)
const editingUser = ref(null)
const isUpdating = ref(false)

// Delete modal
const showDeleteModal = ref(false)
const userToDelete = ref(null)
const isDeleting = ref(false)

async function loadUsers() {
    loading.value = true
    error.value = ''
    
    try {
        const users = await getAllUsers()
        allUsers.value = users
        emit('users-loaded', users)
    } catch (err) {
        console.error('Error loading users:', err)
        error.value = err.message || 'Erro ao carregar usuários'
    } finally {
        loading.value = false
    }
}

function formatDate(dateString) {
    if (!dateString) return 'N/A'
    
    const date = new Date(dateString)
    return date.toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    })
}


function openEditModal(user) {
    editingUser.value = {
        ...user,
        mainRole: user.roles && user.roles.includes('ADMIN') ? 'ADMIN' : 'USER'
    }
    showEditModal.value = true
}

function closeEditModal() {
    showEditModal.value = false
    editingUser.value = null
    isUpdating.value = false
}

async function updateUser() {
    if (isUpdating.value || !editingUser.value) return
    
    isUpdating.value = true
    
    try {
        const userData = {
            username: editingUser.value.username,
            firstName: editingUser.value.firstName,
            lastName: editingUser.value.lastName,
            email: editingUser.value.email,
            roles: [editingUser.value.mainRole]
        }
        
        const updatedUser = await updateUserAPI(editingUser.value.id, userData)
        
        // Update local user data
        const index = allUsers.value.findIndex(u => u.id === editingUser.value.id)
        if (index !== -1) {
            allUsers.value[index] = updatedUser
            emit('user-updated', updatedUser)
        }
        
        closeEditModal()
    } catch (err) {
        console.error('Error updating user:', err)
        error.value = err.response?.data?.message || 'Erro ao atualizar usuário'
    } finally {
        isUpdating.value = false
    }
}

function confirmDelete(user) {
    userToDelete.value = user
    showDeleteModal.value = true
}

function closeDeleteModal() {
    showDeleteModal.value = false
    userToDelete.value = null
    isDeleting.value = false
}

async function deleteUser() {
    if (isDeleting.value || !userToDelete.value) return
    
    isDeleting.value = true
    
    try {
        await deleteUserAPI(userToDelete.value.id)
        
        // Remove from local data
        allUsers.value = allUsers.value.filter(u => u.id !== userToDelete.value.id)
        emit('user-deleted', userToDelete.value.id)
        
        closeDeleteModal()
    } catch (err) {
        console.error('Error deleting user:', err)
        error.value = err.response?.data?.message || 'Erro ao excluir usuário'
    } finally {
        isDeleting.value = false
    }
}

onMounted(() => {
    if (props.users.length === 0) {
        loadUsers()
    } else {
        allUsers.value = props.users
        loading.value = false
    }
})

watch(() => props.users, (newUsers) => {
    if (newUsers.length > 0) {
        allUsers.value = newUsers
        loading.value = false
    }
})
</script>

<style scoped>
.users-list {
    width: 100%;
}

/* Loading State */
.loading-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: var(--spacing-2xl);
    text-align: center;
}

.loading-spinner {
    margin-bottom: var(--spacing-lg);
}

.spinner {
    color: var(--text-muted);
    animation: spin 1s linear infinite;
}

.loading-text {
    color: var(--text-muted);
    font-size: 0.875rem;
    margin: 0;
}

/* Error State */
.error-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: var(--spacing-2xl);
    text-align: center;
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(10px);
}

.error-icon {
    color: rgba(160, 160, 160, 0.9);
    margin-bottom: var(--spacing-lg);
}

.error-title {
    color: var(--text-primary);
    font-size: 1.25rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-sm) 0;
}

.error-message {
    color: var(--text-muted);
    margin: 0 0 var(--spacing-xl) 0;
}

/* Empty State */
.empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: var(--spacing-2xl);
    text-align: center;
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(10px);
}

.empty-icon {
    color: var(--text-muted);
    margin-bottom: var(--spacing-lg);
}

.empty-title {
    color: var(--text-primary);
    font-size: 1.25rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-sm) 0;
}

.empty-message {
    color: var(--text-muted);
    margin: 0;
}

/* Users Grid */
.users-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
    gap: var(--spacing-xl);
}

.user-card {
    background: rgba(115, 115, 115, 0.02);
    backdrop-filter: blur(10px);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-xl);
    transition: all var(--transition-fast);
    position: relative;
}

.user-card:hover {
    background: rgba(115, 115, 115, 0.04);
    border-color: var(--border-medium);
    transform: translateY(-2px);
    box-shadow: var(--shadow-lg);
}

.user-card.inactive {
    opacity: 0.7;
}

.user-card.inactive::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: repeating-linear-gradient(
        45deg,
        transparent,
        transparent 10px,
        rgba(80, 80, 80, 0.1) 10px,
        rgba(80, 80, 80, 0.1) 20px
    );
    border-radius: var(--radius-xl);
    pointer-events: none;
}

/* User Header */
.user-header {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
    margin-bottom: var(--spacing-lg);
    padding-bottom: var(--spacing-md);
    border-bottom: 1px solid var(--border-light);
}

.user-avatar {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 48px;
    height: 48px;
    background: rgba(115, 115, 115, 0.15);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    color: var(--text-secondary);
}

.user-info {
    flex: 1;
}

.user-name {
    font-size: 1.125rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0 0 var(--spacing-xs) 0;
}

.user-username {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
}


/* User Details */
.user-details {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-md);
    margin-bottom: var(--spacing-xl);
}

.detail-item {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
}

.detail-icon {
    color: var(--text-muted);
    flex-shrink: 0;
}

.detail-text {
    color: var(--text-secondary);
    font-size: 0.875rem;
    display: flex;
    align-items: center;
    gap: var(--spacing-xs);
}

.role-tag {
    padding: 2px var(--spacing-xs);
    border-radius: var(--radius-sm);
    font-size: 0.75rem;
    font-weight: 600;
    text-transform: uppercase;
}

.role-tag.admin {
    background: rgba(120, 120, 120, 0.2);
    color: rgba(200, 200, 200, 0.9);
}

.role-tag.user {
    background: rgba(100, 100, 100, 0.2);
    color: rgba(180, 180, 180, 0.9);
}

/* User Actions */
.user-actions {
    display: flex;
    gap: var(--spacing-sm);
    flex-wrap: wrap;
}

.action-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 36px;
    height: 36px;
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    background: rgba(115, 115, 115, 0.08);
    color: var(--text-secondary);
    cursor: pointer;
    transition: all var(--transition-fast);
    text-decoration: none;
    backdrop-filter: blur(5px);
}

.action-btn:hover {
    background: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
    color: var(--text-primary);
    transform: translateY(-1px) scale(1.05);
    box-shadow: var(--shadow-sm);
}

.action-btn:active {
    transform: translateY(0) scale(1);
}

.action-btn:disabled {
    opacity: 0.5;
    cursor: not-allowed;
}


.status-btn.deactivate {
    background: rgba(80, 80, 80, 0.15);
    color: rgba(160, 160, 160, 0.9);
    border-color: rgba(80, 80, 80, 0.3);
}

.status-btn.deactivate:hover:not(:disabled) {
    background: rgba(80, 80, 80, 0.25);
    border-color: rgba(80, 80, 80, 0.5);
}

.edit-btn {
    background: rgba(59, 130, 246, 0.08);
    border-color: rgba(59, 130, 246, 0.2);
    color: rgba(59, 130, 246, 0.8);
}

.edit-btn:hover:not(:disabled) {
    background: rgba(59, 130, 246, 0.15);
    border-color: rgba(59, 130, 246, 0.4);
    color: rgba(59, 130, 246, 1);
    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.2);
}

.delete-btn {
    background: rgba(239, 68, 68, 0.08);
    border-color: rgba(239, 68, 68, 0.2);
    color: rgba(239, 68, 68, 0.8);
}

.delete-btn:hover:not(:disabled) {
    background: rgba(239, 68, 68, 0.15);
    border-color: rgba(239, 68, 68, 0.4);
    color: rgba(239, 68, 68, 1);
    box-shadow: 0 4px 12px rgba(239, 68, 68, 0.2);
}

.retry-btn {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md) var(--spacing-xl);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    background: rgba(115, 115, 115, 0.08);
    color: var(--text-secondary);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    backdrop-filter: blur(5px);
}

.retry-btn:hover {
    transform: translateY(-1px);
    background: rgba(115, 115, 115, 0.15);
    border-color: var(--border-medium);
    color: var(--text-primary);
    box-shadow: var(--shadow-sm);
}

.btn-icon-wrapper {
    display: flex;
    align-items: center;
    justify-content: center;
    width: 20px;
    height: 20px;
    background: rgba(115, 115, 115, 0.2);
    border: 1px solid rgba(115, 115, 115, 0.2);
    border-radius: var(--radius-sm);
}

.btn-icon {
    color: currentColor;
}

.btn-text {
    color: currentColor;
    font-weight: 500;
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
    background: rgba(26, 26, 26, 0.98);
    border: 1px solid var(--border-medium);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(20px);
    box-shadow: var(--shadow-xl);
    padding: var(--spacing-2xl);
    width: 50%;
    max-height: 90vh;
    overflow-y: auto;
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
    border-color: rgba(115, 115, 115, 0.6);
    background: rgba(48, 48, 48, 0.8);
    box-shadow: 0 0 0 3px rgba(115, 115, 115, 0.1);
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
.btn-secondary,
.btn-delete {
    padding: var(--spacing-md) var(--spacing-xl);
    border-radius: var(--radius-md);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
}

.btn-primary {
    background: rgba(115, 115, 115, 0.1);
    border: 1px solid var(--border-light);
    color: var(--text-secondary);
}

.btn-primary:hover:not(:disabled) {
    transform: translateY(-1px);
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
    box-shadow: var(--shadow-sm);
}

.btn-primary:disabled {
    opacity: 0.5;
    cursor: not-allowed;
    transform: none;
}

.btn-secondary {
    background: rgba(115, 115, 115, 0.1);
    border: 1px solid var(--border-light);
    color: var(--text-secondary);
}

.btn-secondary:hover {
    transform: translateY(-1px);
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
    box-shadow: var(--shadow-sm);
}

.btn-delete {
    background: rgba(239, 68, 68, 0.9);
    border-color: rgba(239, 68, 68, 1);
    color: white;
}

.btn-delete:hover:not(:disabled) {
    background: rgba(239, 68, 68, 1);
    transform: translateY(-1px);
    box-shadow: var(--shadow-sm);
}

.btn-delete:disabled {
    opacity: 0.5;
    cursor: not-allowed;
    transform: none;
}

/* Delete Content */
.delete-content {
    text-align: center;
}

.delete-icon {
    color: rgba(160, 160, 160, 0.9);
    margin-bottom: var(--spacing-lg);
}

.delete-title {
    color: var(--text-primary);
    font-size: 1.25rem;
    font-weight: 600;
    margin: 0 0 var(--spacing-md) 0;
}

.delete-message {
    color: var(--text-secondary);
    margin: 0 0 var(--spacing-xl) 0;
    line-height: 1.5;
}

@keyframes spin {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
}
</style>
