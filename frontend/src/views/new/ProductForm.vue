<template>
    <div class="form-container">
        <div class="form-header">
            <h2 class="form-title">
                {{ id ? 'Editar Produto' : 'Novo Produto' }}
            </h2>
            <p class="form-subtitle">
                {{ id ? 'Atualize os dados do produto' : 'Preencha os dados para criar um novo produto' }}
            </p>
        </div>

        <form @submit.prevent="submit" class="product-form">
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label">
                        <svg class="label-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M20 7L12 3L4 7L12 11L20 7Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M4 7V17L12 21L20 17V7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                        Nome do Produto
                    </label>
                    <div class="input-wrapper">
                        <input 
                            v-model="name" 
                            required 
                            class="form-input"
                            type="text"
                            placeholder="Digite o nome do produto"
                            :disabled="isSubmitting"
                        />
                        <div class="input-border"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">
                        <svg class="label-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M4 6H20M4 12H20M4 18H20" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                        </svg>
                        Categoria
                    </label>
                    <div class="select-wrapper">
                        <select v-model="selectedCategory" required class="form-select" :disabled="isSubmitting">
                            <option value="" disabled>-- Selecione uma categoria --</option>
                            <option v-for="cat in categories" :key="cat.id" :value="cat.id">
                                {{ cat.name }}
                            </option>
                        </select>
                        <div class="select-border"></div>
                    </div>
                </div>
            </div>

            <div class="form-group full-width">
                <label class="form-label">
                    <svg class="label-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M14 2H6C5.46957 2 4.96086 2.21071 4.58579 2.58579C4.21071 2.96086 4 3.46957 4 4V20C4 20.5304 4.21071 21.0391 4.58579 21.4142C4.96086 21.7893 5.46957 22 6 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V8L14 2Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M14 2V8H20" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M16 13H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <path d="M16 17H8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    Descrição
                </label>
                <div class="textarea-wrapper">
                    <textarea 
                        v-model="description" 
                        required 
                        class="form-textarea"
                        placeholder="Descreva o produto detalhadamente"
                        :disabled="isSubmitting"
                        rows="3"
                    ></textarea>
                    <div class="input-border"></div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label class="form-label">
                        <svg class="label-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M12 1V23" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M17 5H9.5C8.57174 5 7.6815 5.36875 7.02513 6.02513C6.36875 6.6815 6 7.57174 6 8.5C6 9.42826 6.36875 10.3185 7.02513 10.9749C7.6815 11.6312 8.57174 12 9.5 12H14.5C15.4283 12 16.3185 12.3687 16.9749 13.0251C17.6312 13.6815 18 14.5717 18 15.5C18 16.4283 17.6312 17.3185 16.9749 17.9749C16.3185 18.6312 15.4283 19 14.5 19H6" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                        Preço (R$)
                    </label>
                    <div class="input-wrapper">
                        <input 
                            type="number" 
                            v-model.number="price" 
                            required 
                            min="0" 
                            step="0.01" 
                            class="form-input"
                            placeholder="0.00"
                            :disabled="isSubmitting"
                        />
                        <div class="input-border"></div>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">
                        <svg class="label-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M3 9L12 2L21 9V20C21 20.5304 20.5304 21 20 21H4C3.44772 21 3 20.5304 3 20V9Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M9 22V12H15V22" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                        Quantidade em Estoque
                    </label>
                    <div class="input-wrapper">
                        <input 
                            type="number" 
                            v-model.number="quantity" 
                            required 
                            min="0" 
                            step="1" 
                            class="form-input"
                            placeholder="0"
                            :disabled="isSubmitting"
                        />
                        <div class="input-border"></div>
                    </div>
                </div>
            </div>

            <div class="form-group full-width">
                <label class="form-label">
                    <svg class="label-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M23 19C23 19.5304 22.7893 20.0391 22.4142 20.4142C22.0391 20.7893 21.5304 21 21 21H3C2.46957 21 1.96086 20.7893 1.58579 20.4142C1.21071 20.0391 1 19.5304 1 19V8C1 7.46957 1.21071 6.96086 1.58579 6.58579C1.96086 6.21071 2.46957 6 3 6H7L9 3H15L17 6H21C21.5304 6 22.0391 6.21071 22.4142 6.58579C22.7893 6.96086 23 7.46957 23 8V19Z" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        <circle cx="12" cy="13" r="4" stroke="currentColor" stroke-width="2"/>
                    </svg>
                    Imagem do Produto
                </label>
                <div class="file-input-wrapper">
                    <input 
                        type="file" 
                        @change="handleFileSelect" 
                        accept="image/jpeg,image/jpg,image/png,image/gif,image/webp" 
                        class="file-input"
                        id="product-image"
                        :disabled="isSubmitting"
                    />
                    <label for="product-image" class="file-input-label" :class="{ 'has-file': selectedFile }">
                        <svg class="upload-icon" width="20" height="20" viewBox="0 0 24 24" fill="none">
                            <path d="M21 15V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 20 21H4C3.46957 21 2.96086 20.7893 1.58579 20.4142C1.21071 20.0391 1 19.5304 1 19V15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M17 8L12 3L7 8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M12 3V15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                        <span class="upload-text">
                            {{ selectedFile ? selectedFile.name : 'Clique para selecionar uma imagem' }}
                        </span>
                    </label>
                </div>
                <p class="file-info">Tamanho máximo: 5MB. Formatos suportados: JPG, PNG, GIF, WebP</p>
            </div>

            <div v-if="imagePreview" class="image-preview-container">
                <div class="image-preview">
                    <img :src="imagePreview" alt="Preview do produto" class="preview-image" />
                    <div class="preview-overlay">
                        <span class="preview-label">Preview da Imagem</span>
                    </div>
                </div>
            </div>

            <div v-if="errorMessage" class="error-container">
                <div class="error-message">
                    <svg class="error-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <circle cx="12" cy="12" r="10" stroke="currentColor" stroke-width="2"/>
                        <path d="M15 9L9 15" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                        <path d="M9 9L15 15" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
                    </svg>
                    <span>{{ errorMessage }}</span>
                </div>
            </div>

            <div class="form-actions">
                <button 
                    type="button" 
                    @click="$emit('done')" 
                    class="form-btn cancel-btn"
                    :disabled="isSubmitting"
                >
                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M18 6L6 18M6 6L18 18" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <span>Cancelar</span>
                </button>
                
                <button 
                    type="submit" 
                    class="form-btn submit-btn"
                    :disabled="isSubmitting || !name.trim() || !description.trim() || !selectedCategory || price <= 0 || quantity < 0"
                >
                    <svg v-if="!isSubmitting" width="16" height="16" viewBox="0 0 24 24" fill="none">
                        <path d="M20 6L9 17L4 12" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    <div v-else class="loading-spinner"></div>
                    <span>{{ isSubmitting ? 'Salvando...' : (id ? 'Atualizar Produto' : 'Criar Produto') }}</span>
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
    id: Number // null or undefined for new
})

const emit = defineEmits(['done'])

const name = ref('')
const description = ref('')
const price = ref(0)
const quantity = ref(0)
const selectedCategory = ref('')
const selectedFile = ref(null)
const imagePreview = ref(null)
const currentImageUrl = ref('')
const errorMessage = ref('')
const isSubmitting = ref(false)

const categories = ref([])

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

async function loadProduct() {
    if (props.id) {
        if (!auth.isLoggedIn()) {
            return
        }
        const token = auth.token

        const res = await api.get(`/products/${props.id}`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token
            },
        })
        const data = res.data
        name.value = data.name
        price.value = data.price
        description.value = data.description
        quantity.value = data.quantity
        selectedCategory.value = data.categoryId
        
        // Store and display existing image if available
        currentImageUrl.value = data.imageUrl || ''
        if (data.imageUrl) {
            imagePreview.value = `http://localhost:8080${data.imageUrl}`
        }

    } else {
        name.value = ''
        price.value = 0
        quantity.value = 0
        selectedFile.value = null
        imagePreview.value = null
        currentImageUrl.value = ''
        errorMessage.value = ''
    }
}

onMounted(async function () {
    await fetchCategories()
    loadProduct()
})

watch(() => props.id, loadProduct)

function handleFileSelect(event) {
    const file = event.target.files[0]
    if (file) {
        // Clear previous errors
        errorMessage.value = ''
        
        // Validate file size (5MB limit)
        const maxSize = 5 * 1024 * 1024; // 5MB in bytes
        if (file.size > maxSize) {
            errorMessage.value = 'File size exceeds 5MB limit. Please choose a smaller image.'
            event.target.value = '' // Clear the input
            selectedFile.value = null
            imagePreview.value = null
            return
        }
        
        // Validate file type
        const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp']
        if (!allowedTypes.includes(file.type)) {
            errorMessage.value = 'Only JPG, PNG, GIF, and WebP images are allowed.'
            event.target.value = '' // Clear the input
            selectedFile.value = null
            imagePreview.value = null
            return
        }
        
        selectedFile.value = file
        
        // Create preview
        const reader = new FileReader()
        reader.onload = (e) => {
            imagePreview.value = e.target.result
        }
        reader.readAsDataURL(file)
    }
}

async function uploadProductImage(productId) {
    if (!selectedFile.value) return currentImageUrl.value
    
    try {
        const token = auth.token
        const formData = new FormData()
        formData.append('file', selectedFile.value)
        
        const response = await api.post(`/products/${productId}/image`, formData, {
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'multipart/form-data'
            }
        })
        
        // Update the current image URL with the new one
        currentImageUrl.value = response.data.imageUrl
        return currentImageUrl.value
    } catch (error) {
        console.error('Error uploading image:', error)
        throw new Error('Failed to upload image: ' + (error.response?.data?.message || error.message))
    }
}

async function submit() {
    if (!auth.isLoggedIn()) return
    
    errorMessage.value = ''
    isSubmitting.value = true
    
    try {
        const token = auth.token

        const body = JSON.stringify({
            id: props.id,
            name: name.value,
            description: description.value,
            price: price.value,
            quantity: quantity.value,
            categoryId: selectedCategory.value,
            imageUrl: currentImageUrl.value
        })

        let productId = props.id
        
        if (props.id) {
            await api.put(
                `/products/${props.id}`,
                body,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: 'Bearer ' + token
                    },
                }
            )
        } else {
            const response = await api.post(
                '/products',
                body,
                {
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: 'Bearer ' + token
                    },
                }
            )
            productId = response.data.id
        }

        // Upload image if a file was selected
        if (selectedFile.value) {
            await uploadProductImage(productId)
        }

        emit('done')
    } catch (error) {
        console.error('Error submitting product:', error)
        
        if (error.response?.status === 409) {
            errorMessage.value = 'A product with this name already exists. Please choose a different name.'
        } else if (error.response?.data?.message) {
            errorMessage.value = error.response.data.message
        } else {
            errorMessage.value = 'An error occurred while saving the product. Please try again.'
        }
    } finally {
        isSubmitting.value = false
    }
}
</script>


<style scoped>
.form-container {
    max-width: 800px;
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
    font-size: 1.75rem;
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

/* Form Layout */
.product-form {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-xl);
}

.form-row {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: var(--spacing-lg);
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-sm);
}

.form-group.full-width {
    grid-column: 1 / -1;
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

/* Input Styles */
.input-wrapper,
.textarea-wrapper,
.select-wrapper {
    position: relative;
}

.form-input,
.form-textarea,
.form-select {
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
    font-family: inherit;
}

.form-textarea {
    resize: vertical;
    min-height: 80px;
}

.form-select {
    appearance: none;
    background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='%23737373' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpath d='M6 9l6 6 6-6'/%3e%3c/svg%3e");
    background-repeat: no-repeat;
    background-position: right var(--spacing-md) center;
    background-size: 16px;
    padding-right: calc(var(--spacing-md) + 24px);
    cursor: pointer;
}

.form-input::placeholder,
.form-textarea::placeholder {
    color: var(--text-muted);
    opacity: 0.7;
}

.form-input:focus,
.form-textarea:focus,
.form-select:focus {
    background: rgba(115, 115, 115, 0.12);
    border-color: var(--border-heavy);
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(115, 115, 115, 0.15);
}

.form-input:disabled,
.form-textarea:disabled,
.form-select:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.input-border,
.select-border {
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

.form-input:focus + .input-border,
.form-textarea:focus + .input-border,
.form-select:focus + .select-border {
    width: 100%;
}

/* File Input */
.file-input-wrapper {
    position: relative;
}

.file-input {
    position: absolute;
    opacity: 0;
    width: 100%;
    height: 100%;
    cursor: pointer;
}

.file-input-label {
    display: flex;
    align-items: center;
    gap: var(--spacing-md);
    padding: var(--spacing-lg) var(--spacing-md);
    background: rgba(115, 115, 115, 0.08);
    border: 2px dashed var(--border-light);
    border-radius: var(--radius-lg);
    color: var(--text-muted);
    font-size: 0.875rem;
    font-weight: 500;
    cursor: pointer;
    transition: all var(--transition-normal);
    min-height: 60px;
}

.file-input-label:hover {
    background: rgba(115, 115, 115, 0.12);
    border-color: var(--border-medium);
    color: var(--text-secondary);
    transform: translateY(-1px);
}

.file-input-label.has-file {
    background: rgba(16, 185, 129, 0.08);
    border-color: rgba(16, 185, 129, 0.3);
    color: rgba(16, 185, 129, 0.9);
}

.upload-icon {
    color: currentColor;
    flex-shrink: 0;
}

.upload-text {
    flex: 1;
    line-height: 1.4;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.file-info {
    font-size: 0.75rem;
    color: var(--text-muted);
    margin: var(--spacing-xs) 0 0 0;
    line-height: 1.4;
}

/* Image Preview */
.image-preview-container {
    display: flex;
    justify-content: center;
    margin: var(--spacing-lg) 0;
}

.image-preview {
    position: relative;
    max-width: 300px;
    width: 100%;
    background: rgba(115, 115, 115, 0.05);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    overflow: hidden;
    box-shadow: var(--shadow-md);
    transition: all var(--transition-normal);
}

.image-preview:hover {
    transform: translateY(-2px);
    box-shadow: var(--shadow-lg);
    border-color: var(--border-medium);
}

.preview-image {
    width: 100%;
    height: auto;
    max-height: 300px;
    object-fit: contain;
    display: block;
}

.preview-overlay {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: linear-gradient(to top, rgba(0, 0, 0, 0.8), transparent);
    padding: var(--spacing-md);
    color: white;
    text-align: center;
}

.preview-label {
    font-size: 0.75rem;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

/* Error Message */
.error-container {
    margin: var(--spacing-lg) 0;
}

.error-message {
    display: flex;
    align-items: center;
    gap: var(--spacing-sm);
    padding: var(--spacing-md) var(--spacing-lg);
    background: rgba(239, 68, 68, 0.08);
    border: 1px solid rgba(239, 68, 68, 0.3);
    border-radius: var(--radius-lg);
    color: rgba(239, 68, 68, 0.9);
    font-size: 0.875rem;
    font-weight: 500;
    backdrop-filter: blur(10px);
}

.error-icon {
    color: currentColor;
    flex-shrink: 0;
}

/* Form Actions */
.form-actions {
    display: flex;
    gap: var(--spacing-md);
    justify-content: flex-end;
    margin-top: var(--spacing-xl);
    padding-top: var(--spacing-lg);
    border-top: 1px solid var(--border-light);
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
    min-width: 140px;
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
        font-size: 1.5rem;
    }
    
    .form-row {
        grid-template-columns: 1fr;
        gap: var(--spacing-md);
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
    
    .product-form {
        gap: var(--spacing-lg);
    }
    
    .form-title {
        font-size: 1.25rem;
    }
    
    .file-input-label {
        padding: var(--spacing-md);
        min-height: 50px;
    }
    
    .upload-text {
        font-size: 0.8rem;
    }
}

/* Focus and accessibility improvements */
.form-btn:focus {
    outline: 2px solid rgba(59, 130, 246, 0.5);
    outline-offset: 2px;
}

.form-input:focus,
.form-textarea:focus,
.form-select:focus {
    outline: 2px solid rgba(115, 115, 115, 0.3);
    outline-offset: 2px;
}

.file-input:focus + .file-input-label {
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
