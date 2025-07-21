<template>
    <div class="form-container">
        <div class="form-header">
            <h2 class="form-title">
                {{ id ? 'Editar Categoria' : 'Nova Categoria' }}
            </h2>
            <p class="form-subtitle">
                {{ id ? 'Atualize os dados da categoria' : 'Preencha os dados para criar uma nova categoria' }}
            </p>
        </div>

        <form @submit.prevent="submit" class="category-form">
            <div class="form-group">
                <label class="form-label">
                    <svg class="label-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    Nome da Categoria
                </label>
                <div class="input-wrapper">
                    <input 
                        v-model="name" 
                        required 
                        class="form-input"
                        type="text"
                        placeholder="Digite o nome da categoria"
                        :disabled="isLoading"
                    />
                    <div class="input-border"></div>
                </div>
                <p class="input-help">O nome deve ser único e descritivo</p>
            </div>

            <div class="form-actions">
                <button 
                    type="button" 
                    @click="$emit('done')" 
                    class="form-btn cancel-btn"
                    :disabled="isLoading"
                >
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <span>Cancelar</span>
                </button>
                
                <button 
                    type="submit" 
                    class="form-btn submit-btn"
                    :disabled="isLoading || !name.trim()"
                >
                    <svg v-if="!isLoading" width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M20 6L9 17L4 12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <div v-else class="loading-spinner"></div>
                    <span>{{ id ? 'Atualizar' : 'Criar' }}</span>
                </button>
            </div>
        </form>
    </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import auth from '@/api/auth.js'
import api from '@/api/axios.js'

const props = defineProps({
    id: Number // null for new
})

const emit = defineEmits(['done'])

const name = ref('')
const isLoading = ref(false)

async function loadCategories() {
    if (props.id) {
        if (!auth.isLoggedIn()) {
            return
        }
        const token = auth.token

        const res = await api.get(`/api/categories/${props.id}`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token
            },
        })
        const data = await res.data
        name.value = data.name
    }
}



onMounted(async () => {
    loadCategories()
})

async function submit() {
    if (isLoading.value) return
    
    isLoading.value = true
    
    try {
        const body = JSON.stringify({ id: props.id, name: name.value.trim() })
        if (!auth.isLoggedIn()) {
            return
        }
        const token = auth.token
        
        if (props.id) {
            await api.put(
                `/api/categories/${props.id}`,
                body,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: 'Bearer ' + token
                    },
                }
            )
        } else {
            await api.post(
                '/api/categories',
                body,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: 'Bearer ' + token
                    },
                }
            )
        }

        emit('done')
    } catch (error) {
        console.error('Error saving category:', error)
    } finally {
        isLoading.value = false
    }
}


// Reload if id changes
watch(() => props.id, () => {
    loadCategories()
})
</script>

<style scoped>
.form-container {
    max-width: 500px;
    margin: 0 auto;
    padding: var(--spacing-xl);
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(10px);
    box-shadow: var(--shadow-md);
}

/* Form Header */
.form-header {
    text-align: center;
    margin-bottom: var(--spacing-2xl);
}

.form-title {
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--text-primary);
    margin: 0 0 var(--spacing-sm) 0;
    line-height: 1.3;
}

.form-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
    line-height: 1.5;
}

/* Form */
.category-form {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-2xl);
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-sm);
}

.form-label {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    font-size: 0.875rem;
    font-weight: 600;
    color: var(--text-primary);
    margin-bottom: var(--spacing-xs);
}

.label-icon {
    color: var(--text-muted);
}

/* Input Wrapper */
.input-wrapper {
    position: relative;
}

.form-input {
    width: 100%;
    padding: var(--spacing-lg) var(--spacing-md);
    background: rgba(115, 115, 115, 0.08);
    border: 2px solid transparent;
    border-radius: var(--radius-lg);
    color: var(--text-primary);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-normal);
    outline: none;
    box-sizing: border-box;
}

.form-input::placeholder {
    color: var(--text-muted);
    opacity: 0.7;
}

.form-input:focus {
    background: rgba(115, 115, 115, 0.12);
    border-color: var(--border-heavy);
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(115, 115, 115, 0.15);
}

.form-input:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.input-border {
    position: absolute;
    bottom: 0;
    left: 50%;
    width: 0;
    height: 2px;
    background: linear-gradient(90deg, var(--border-heavy), var(--text-muted));
    transform: translateX(-50%);
    transition: width var(--transition-normal);
    border-radius: 1px;
}

.form-input:focus + .input-border {
    width: 100%;
}

.input-help {
    font-size: 0.75rem;
    color: var(--text-muted);
    margin: var(--spacing-xs) 0 0 0;
    line-height: 1.4;
}

/* Form Actions */
.form-actions {
    display: flex;
    gap: var(--spacing-md);
    justify-content: flex-end;
    margin-top: var(--spacing-lg);
}

.form-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md) var(--spacing-xl);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-lg);
    font-size: 0.875rem;
    font-weight: 600;
    cursor: pointer;
    transition: all var(--transition-fast);
    text-decoration: none;
    min-width: 120px;
    position: relative;
}

.form-btn:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none !important;
}

.cancel-btn {
    background: rgba(115, 115, 115, 0.1);
    color: var(--text-secondary);
}

.cancel-btn:hover:not(:disabled) {
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
    transform: translateY(-1px);
}

.submit-btn {
    background: rgba(59, 130, 246, 0.9);
    border-color: rgba(59, 130, 246, 1);
    color: white;
    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.submit-btn:hover:not(:disabled) {
    background: rgba(59, 130, 246, 1);
    transform: translateY(-2px);
    box-shadow: 0 8px 20px rgba(59, 130, 246, 0.4);
}

.submit-btn:disabled {
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-light);
    color: var(--text-muted);
    box-shadow: none;
}

/* Loading Spinner */
.loading-spinner {
    width: 16px;
    height: 16px;
    border: 2px solid transparent;
    border-top: 2px solid currentColor;
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

/* Responsive Design */
@media (max-width: 768px) {
    .form-container {
        margin: var(--spacing-md);
        padding: var(--spacing-lg);
    }
    
    .form-title {
        font-size: 1.25rem;
    }
    
    .form-actions {
        flex-direction: column-reverse;
    }
    
    .form-btn {
        width: 100%;
        justify-content: center;
    }
}

@media (max-width: 480px) {
    .form-container {
        margin: var(--spacing-sm);
        padding: var(--spacing-md);
    }
    
    .form-header {
        margin-bottom: var(--spacing-xl);
    }
    
    .category-form {
        gap: var(--spacing-xl);
    }
}

/* Focus and accessibility improvements */
.form-btn:focus {
    outline: 2px solid rgba(59, 130, 246, 0.5);
    outline-offset: 2px;
}

.form-input:focus {
    outline: 2px solid rgba(115, 115, 115, 0.3);
    outline-offset: 2px;
}

/* Animation for form appearance */
.form-container {
    animation: slideUp 0.4s ease-out;
}

@keyframes slideUp {
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
