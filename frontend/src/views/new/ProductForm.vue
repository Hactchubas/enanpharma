<template>
    <form @submit.prevent="submit">
        <label>
            Name:
            <input v-model="name" required />
        </label>
        <label>
            Description:
            <input v-model="description" required />
        </label>
        <label>
            Category:
            <select v-model="selectedCategory" required>
                <option selected disabled value="">-- Select a category --</option>
                <option v-for="cat in categories" :key="cat.id" :value="cat.id">
                    {{ cat.name }}
                </option>
            </select>
        </label>
        <label>
            Price:
            <input type="number" v-model.number="price" required min="0" step="0.01" />
        </label>
        <label>
            Quantity:
            <input type="number" v-model.number="quantity" required min="0" step="1" />
        </label>
        <label>
            Product Image:
            <input type="file" @change="handleFileSelect" accept="image/jpeg,image/jpg,image/png,image/gif,image/webp" />
            <small class="file-info">Maximum file size: 5MB. Supported formats: JPG, PNG, GIF, WebP</small>
        </label>
        <div v-if="imagePreview" class="image-preview">
            <img :src="imagePreview" alt="Product preview" />
        </div>
        <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
        </div>
        <div>
            <button type="submit" :disabled="isSubmitting">
                {{ isSubmitting ? 'Submitting...' : (id ? 'Update' : 'Add') }}
            </button>
            <button type="button" @click="$emit('done')">Cancel</button>
        </div>
    </form>
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
    const res = await api.get("/api/categories", {
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

        const res = await api.get(`/api/products/${props.id}`, {
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
        
        const response = await api.post(`/api/products/${productId}/image`, formData, {
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
                `/api/products/${props.id}`,
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
                '/api/products',
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
form {
    display: flex;
    flex-flow: column nowrap;
    gap: 20px;
    width: 100%;
    justify-content: start;
    align-items: center;

    margin: 30px 0px;
}

label {
    width: 100%;
    display: flex;
    flex-flow: column nowrap;
    gap: 0px;
    justify-content: start;
    align-items: start;
}

label>input {
    width: 100%;
}

button {
    color: white;
}

.image-preview {
    width: 100%;
    max-width: 300px;
    margin: 10px 0;
    border: 2px dashed #ccc;
    border-radius: 8px;
    padding: 10px;
    text-align: center;
}

.image-preview img {
    max-width: 100%;
    max-height: 200px;
    object-fit: contain;
    border-radius: 4px;
}

.error-message {
    width: 100%;
    padding: 10px;
    background-color: #ffebee;
    border: 1px solid #f44336;
    border-radius: 4px;
    color: #c62828;
    font-size: 14px;
    margin: 10px 0;
}

button:disabled {
    opacity: 0.6;
    cursor: not-allowed;
}

.file-info {
    color: #666;
    font-size: 12px;
    margin-top: 5px;
    display: block;
}
</style>
