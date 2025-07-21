<template>
    <div class="categories-page">
        <div class="page-header">
            <div class="header-content">
                <div class="title-section">
                    <svg class="page-icon" width="24" height="24" viewBox="0 0 24 24" fill="none">
                        <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <div class="title-content">
                        <h1 class="page-title">Gerenciamento de Categorias</h1>
                        <p class="page-subtitle">Organize os produtos em categorias para facilitar a navegação</p>
                    </div>
                </div>
                
                <button 
                    @click="showForm(add)" 
                    class="add-btn"
                    aria-label="Adicionar nova categoria"
                >
                    <div class="btn-icon-wrapper">
                        <svg class="btn-icon" width="18" height="18" viewBox="0 0 24 24" fill="none">
                            <path d="M12 5V19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M5 12H19" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                    <div class="btn-content">
                        <span class="btn-text">Nova Categoria</span>
                        <span class="btn-subtitle">Organizar produtos</span>
                    </div>
                </button>
            </div>
        </div>

        <div class="page-content">
            <CategoryForm v-if="formVisible" :id="editingId" @done="onFormDone" />
            <CategoriesList v-else @edit="showForm" :refresh-key="forceRefresh" />
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import CategoriesList from '../components/CategoriesList.vue'
import CategoryForm from './new/CategoryForm.vue'

const editingId = ref(null)
const formVisible = ref(false)
const forceRefresh = ref(0)

function showForm(id) {
    formVisible.value = true
    editingId.value = id
}

function onFormDone() {
    editingId.value = null
    formVisible.value = false

    forceRefresh.value++
}
</script>

<style scoped>
.categories-page {
    width: 100%;
    max-width: 1400px;
    margin: 0 auto;
    padding: var(--spacing-xl);
}

/* Page Header */
.page-header {
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-xl);
    margin-bottom: var(--spacing-xl);
    backdrop-filter: blur(10px);
    transition: all var(--transition-normal);
}

.page-header:hover {
    background: rgba(115, 115, 115, 0.06);
    border-color: var(--border-medium);
}

.header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: var(--spacing-lg);
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
}

.page-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
    line-height: 1.4;
}

/* Add Button */
.add-btn {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
    padding: var(--spacing-lg);
    background: rgba(59, 130, 246, 0.9);
    border: 1px solid rgba(59, 130, 246, 1);
    border-radius: var(--radius-lg);
    color: white;
    font-weight: 600;
    cursor: pointer;
    transition: all var(--transition-fast);
    text-decoration: none;
    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
    min-width: 200px;
}

.add-btn:hover {
    background: rgba(59, 130, 246, 1);
    transform: translateY(-2px) scale(1.02);
    box-shadow: 0 8px 20px rgba(59, 130, 246, 0.4);
}

.add-btn:active {
    transform: translateY(0) scale(1);
    box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
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

.add-btn:hover .btn-icon-wrapper {
    background: rgba(255, 255, 255, 0.25);
    border-color: rgba(255, 255, 255, 0.3);
    transform: rotate(90deg);
}

.btn-icon {
    color: white;
    filter: drop-shadow(0 1px 2px rgba(0, 0, 0, 0.2));
}

.btn-content {
    display: flex;
    flex-direction: column;
    gap: 2px;
    text-align: left;
}

.btn-text {
    font-size: 1rem;
    font-weight: 600;
    color: white;
    line-height: 1.2;
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.btn-subtitle {
    font-size: 0.75rem;
    color: rgba(255, 255, 255, 0.8);
    font-weight: 400;
    line-height: 1.2;
    text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

/* Page Content */
.page-content {
    width: 100%;
}

/* Responsive Design */
@media (max-width: 768px) {
    .categories-page {
        padding: var(--spacing-md);
    }
    
    .header-content {
        flex-direction: column;
        align-items: stretch;
        gap: var(--spacing-md);
    }
    
    .title-section {
        gap: var(--spacing-md);
    }
    
    .page-title {
        font-size: 1.5rem;
    }
    
    .add-btn {
        min-width: auto;
        justify-content: center;
        padding: var(--spacing-md);
    }
    
    .btn-content {
        text-align: center;
    }
}

@media (max-width: 640px) {
    .title-section {
        flex-direction: column;
        align-items: center;
        text-align: center;
    }
    
    .page-icon {
        margin-bottom: var(--spacing-sm);
    }
    
    .add-btn {
        width: 100%;
    }
    
    .btn-text {
        font-size: 0.9rem;
    }
    
    .btn-subtitle {
        font-size: 0.7rem;
    }
}

@media (max-width: 480px) {
    .page-header {
        padding: var(--spacing-lg);
    }
    
    .page-title {
        font-size: 1.25rem;
    }
    
    .page-subtitle {
        font-size: 0.8rem;
    }
    
    .add-btn {
        padding: var(--spacing-md) var(--spacing-lg);
    }
    
    .btn-icon-wrapper {
        width: 36px;
        height: 36px;
    }
}
</style>
