<template>
    <div class="categories-container">
        <div v-if="categories.length === 0" class="empty-state">
            <div class="empty-icon">
                <svg width="64" height="64" viewBox="0 0 24 24" fill="none">
                    <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                </svg>
            </div>
            <p class="empty-text">Nenhuma categoria encontrada</p>
            <p class="empty-subtitle">Comece criando sua primeira categoria</p>
        </div>

        <div v-else class="table-container">
            <div class="table-wrapper">
                <table class="categories-table">
                    <thead>
                        <tr class="table-header">
                            <th class="th-id">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M7 20L17 12L7 4V20Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    ID
                                </div>
                            </th>
                            <th class="th-name">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                                    </svg>
                                    Nome da Categoria
                                </div>
                            </th>
                            <th class="th-actions">
                                <div class="header-content">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                        <path d="M12 20H21M3 20H6.5M6.5 20C7.32843 20 8 19.3284 8 18.5C8 17.6716 7.32843 17 6.5 17C5.67157 17 5 17.6716 5 18.5C5 19.3284 5.67157 20 6.5 20ZM16 4H19M16 4C16 5.1046 16.8954 6 18 6C19.1046 6 20 5.1046 20 4C20 2.8954 19.1046 2 18 2C16.8954 2 16 2.8954 16 4ZM16 4H3M8 12H21M3 12H5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                    </svg>
                                    Ações
                                </div>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr 
                            v-for="(category, index) in categories" 
                            :key="category.id" 
                            class="table-row"
                            :class="{ 'row-even': index % 2 === 0, 'row-odd': index % 2 === 1 }"
                        >
                            <td class="td-id">
                                <span class="category-id">#{{ category.id }}</span>
                            </td>
                            <td class="td-name">
                                <div class="category-name-wrapper">
                                    <span class="category-name">{{ category.name }}</span>
                                </div>
                            </td>
                            <td class="td-actions">
                                <div class="action-buttons">
                                    <button 
                                        @click="$emit('edit', category.id)" 
                                        class="action-btn edit-btn"
                                        aria-label="Editar categoria"
                                        title="Editar categoria"
                                    >
                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                            <path d="M11 4H4C3.46957 4 2.96086 4.21071 2.58579 4.58579C2.21071 4.96086 2 5.46957 2 6V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V13" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                            <path d="M18.5 2.49998C18.8978 2.10216 19.4374 1.87866 20 1.87866C20.5626 1.87866 21.1022 2.10216 21.5 2.49998C21.8978 2.89781 22.1213 3.43737 22.1213 3.99998C22.1213 4.56259 21.8978 5.10216 21.5 5.49998L12 15L8 16L9 12L18.5 2.49998Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                    </button>
                                    
                                    <button 
                                        @click="confirmDelete(category)" 
                                        class="action-btn delete-btn"
                                        aria-label="Excluir categoria"
                                        title="Excluir categoria"
                                    >
                                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                                            <path d="M3 6H5H21" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                            <path d="M8 6V4C8 3.46957 8.21071 2.96086 8.58579 2.58579C8.96086 2.21071 9.46957 2 10 2H14C14.5304 2 15.0391 2.21071 15.4142 2.58579C15.7893 2.96086 16 3.46957 16 4V6M19 6V20C19 20.5304 18.7893 21.0391 18.4142 21.4142C18.0391 21.7893 17.5304 22 17 22H7C6.46957 22 5.96086 21.7893 5.58579 21.4142C5.21071 21.0391 5 20.5304 5 20V6H19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Delete confirmation modal -->
        <div v-if="showDeleteModal" class="modal-overlay" @click="cancelDelete">
            <div class="delete-modal" @click.stop>
                <div class="modal-header">
                    <h3 class="modal-title">Confirmar Exclusão</h3>
                </div>
                <div class="modal-body">
                    <p class="modal-text">
                        Tem certeza que deseja excluir a categoria 
                        <strong>"{{ categoryToDelete?.name }}"</strong>?
                    </p>
                    <p class="modal-warning">Esta ação não pode ser desfeita.</p>
                </div>
                <div class="modal-actions">
                    <button @click="cancelDelete" class="modal-btn cancel-btn">
                        Cancelar
                    </button>
                    <button @click="executeDelete" class="modal-btn confirm-btn">
                        Excluir
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import auth from "../api/auth.js"
import api from "../api/axios";
const emit = defineEmits(['edit', 'refresh'])

const categories = ref([])
const showDeleteModal = ref(false)
const categoryToDelete = ref(null)

async function fetchCategories() {
    if (!auth.isLoggedIn()) {
        categories.value = [];
        return
    }
    const token = auth.token
    const res = await api.get("/categories", {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    });
    if (res.status != 200) {
        categories.value = [];
        return
    }
    categories.value = await res.data
}

function confirmDelete(category) {
    categoryToDelete.value = category
    showDeleteModal.value = true
}

function cancelDelete() {
    showDeleteModal.value = false
    categoryToDelete.value = null
}

async function executeDelete() {
    if (!categoryToDelete.value || !auth.isLoggedIn()) {
        return
    }
    
    const token = auth.token
    await api.delete(`/categories/${categoryToDelete.value.id}`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    })
    
    await fetchCategories()
    cancelDelete()
}

async function deleteCategory(id) {
    if (!auth.isLoggedIn()) {
        categories.value = [];
        return
    }
    const token = auth.token
    await api.delete(`/categories/${id}`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + token,
        },
    })
    await fetchCategories()
}

onMounted(fetchCategories)


const props = defineProps({
    refreshKey: Number
});
watch(() => props.refreshKey, () => {
    fetchCategories()
});
</script>

<style scoped>
.categories-container {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: var(--spacing-xl);
}

/* Empty State */
.empty-state {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: var(--spacing-2xl) var(--spacing-xl);
    text-align: center;
    background: rgba(115, 115, 115, 0.03);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(10px);
}

.empty-icon {
    color: var(--text-muted);
    opacity: 0.6;
    margin-bottom: var(--spacing-lg);
}

.empty-text {
    font-size: 1.125rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0 0 var(--spacing-sm) 0;
}

.empty-subtitle {
    font-size: 0.875rem;
    color: var(--text-muted);
    margin: 0;
}

/* Table Container */
.table-container {
    background: rgba(115, 115, 115, 0.02);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    overflow: hidden;
    backdrop-filter: blur(10px);
    transition: all var(--transition-normal);
}

.table-container:hover {
    background: rgba(115, 115, 115, 0.04);
    border-color: var(--border-medium);
}

.table-wrapper {
    overflow-x: auto;
}

/* Modern Table */
.categories-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 0.875rem;
}

.table-header {
    background: rgba(115, 115, 115, 0.08);
    border-bottom: 2px solid var(--border-medium);
}

.table-header th {
    padding: var(--spacing-lg) var(--spacing-md);
    text-align: left;
    font-weight: 600;
    color: var(--text-secondary);
    font-size: 0.8rem;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    border-bottom: none;
}

.header-content {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
}

.header-content svg {
    color: var(--text-muted);
    opacity: 0.7;
}

/* Table Rows */
.table-row {
    border-bottom: 1px solid var(--border-light);
    transition: all var(--transition-fast);
}

.table-row:hover {
    background: rgba(115, 115, 115, 0.06);
    border-bottom-color: var(--border-medium);
}

.table-row.row-even {
    background: rgba(115, 115, 115, 0.015);
}

.table-row.row-odd {
    background: rgba(115, 115, 115, 0.025);
}

.table-row:hover.row-even,
.table-row:hover.row-odd {
    background: rgba(115, 115, 115, 0.06);
}

/* Table Cells */
.table-row td {
    padding: var(--spacing-lg) var(--spacing-md);
    vertical-align: middle;
    border-bottom: none;
}

.td-id {
    width: 120px;
}

.category-id {
    display: inline-block;
    font-size: 0.75rem;
    font-weight: 600;
    color: var(--text-muted);
    background: rgba(115, 115, 115, 0.15);
    padding: var(--spacing-xs) var(--spacing-sm);
    border-radius: var(--radius-sm);
    border: 1px solid var(--border-light);
    transition: all var(--transition-fast);
}

.table-row:hover .category-id {
    background: rgba(115, 115, 115, 0.25);
    border-color: var(--border-medium);
}

.td-name {
    min-width: 200px;
}

.category-name-wrapper {
    display: flex;
    align-items: center;
}

.category-name {
    font-size: 1rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0;
    line-height: 1.3;
    transition: color var(--transition-fast);
}

.table-row:hover .category-name {
    color: var(--text-primary);
}

.td-actions {
    width: 120px;
    text-align: right;
}

/* Action Buttons */
.action-buttons {
    display: flex;
    gap: var(--spacing-sm);
    justify-content: flex-end;
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

.edit-btn {
    background: rgba(59, 130, 246, 0.08);
    border-color: rgba(59, 130, 246, 0.2);
    color: rgba(59, 130, 246, 0.8);
}

.edit-btn:hover {
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

.delete-btn:hover {
    background: rgba(239, 68, 68, 0.15);
    border-color: rgba(239, 68, 68, 0.4);
    color: rgba(239, 68, 68, 1);
    box-shadow: 0 4px 12px rgba(239, 68, 68, 0.2);
}

/* Modal */
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
    animation: fadeIn 0.3s ease-out;
}

.delete-modal {
    background: rgba(26, 26, 26, 0.98);
    border: 1px solid var(--border-medium);
    border-radius: var(--radius-xl);
    backdrop-filter: blur(20px);
    box-shadow: var(--shadow-xl);
    max-width: 400px;
    width: 90%;
    animation: slideUp 0.3s ease-out;
}

.modal-header {
    padding: var(--spacing-xl) var(--spacing-xl) 0;
}

.modal-title {
    font-size: 1.25rem;
    font-weight: 600;
    color: var(--text-primary);
    margin: 0;
}

.modal-body {
    padding: var(--spacing-lg) var(--spacing-xl);
}

.modal-text {
    color: var(--text-secondary);
    font-size: 0.875rem;
    line-height: 1.5;
    margin: 0 0 var(--spacing-md) 0;
}

.modal-warning {
    color: var(--text-muted);
    font-size: 0.8rem;
    margin: 0;
    font-style: italic;
}

.modal-actions {
    display: flex;
    gap: var(--spacing-sm);
    padding: 0 var(--spacing-xl) var(--spacing-xl);
}

.modal-btn {
    flex: 1;
    padding: var(--spacing-md) var(--spacing-lg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-md);
    font-size: 0.875rem;
    font-weight: 600;
    cursor: pointer;
    transition: all var(--transition-fast);
    text-align: center;
}

.cancel-btn {
    background: rgba(115, 115, 115, 0.1);
    color: var(--text-secondary);
}

.cancel-btn:hover {
    background: rgba(115, 115, 115, 0.2);
    border-color: var(--border-medium);
    color: var(--text-primary);
}

.confirm-btn {
    background: rgba(239, 68, 68, 0.9);
    border-color: rgba(239, 68, 68, 1);
    color: white;
}

.confirm-btn:hover {
    background: rgba(239, 68, 68, 1);
    transform: translateY(-1px);
    box-shadow: var(--shadow-sm);
}

/* Animations */
@keyframes fadeIn {
    from { opacity: 0; }
    to { opacity: 1; }
}

@keyframes slideUp {
    from {
        opacity: 0;
        transform: translateY(20px) scale(0.95);
    }
    to {
        opacity: 1;
        transform: translateY(0) scale(1);
    }
}

/* Responsive Design */
@media (max-width: 768px) {
    .categories-container {
        padding: var(--spacing-md);
    }
    
    .table-header th {
        padding: var(--spacing-md) var(--spacing-sm);
        font-size: 0.75rem;
    }
    
    .table-row td {
        padding: var(--spacing-md) var(--spacing-sm);
    }
    
    .td-id {
        width: 80px;
    }
    
    .category-id {
        font-size: 0.7rem;
        padding: 2px var(--spacing-xs);
    }
    
    .category-name {
        font-size: 0.9rem;
    }
    
    .action-btn {
        width: 32px;
        height: 32px;
    }
    
    .action-buttons {
        gap: 4px;
    }
    
    .delete-modal {
        margin: var(--spacing-md);
    }
}

@media (max-width: 640px) {
    .table-wrapper {
        border-radius: var(--radius-lg);
    }
    
    .th-id,
    .td-id {
        display: none;
    }
    
    .td-actions {
        width: 80px;
    }
    
    .action-btn {
        width: 28px;
        height: 28px;
    }
    
    .action-btn svg {
        width: 14px;
        height: 14px;
    }
}

@media (max-width: 480px) {
    .categories-container {
        padding: var(--spacing-sm);
    }
    
    .table-header th {
        padding: var(--spacing-sm);
    }
    
    .table-row td {
        padding: var(--spacing-sm);
    }
    
    .category-name {
        font-size: 0.875rem;
    }
    
    .action-buttons {
        flex-direction: column;
        gap: 2px;
    }
    
    .td-actions {
        width: 60px;
    }
}
</style>
