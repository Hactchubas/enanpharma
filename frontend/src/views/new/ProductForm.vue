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
        <div>
            <button type="submit">{{ id ? 'Update' : 'Add' }}</button>
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
const selectedCategory = ref('')

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
        selectedCategory.value = data.categoryId

    } else {
        name.value = ''
        price.value = 0
    }
}

onMounted(async function () {
    await fetchCategories()
    loadProduct()
})

watch(() => props.id, loadProduct)

async function submit() {
    if (!auth.isLoggedIn()) return
    const token = auth.token

    const body = JSON.stringify({
        id: props.id,
        name: name.value,
        description: description.value,
        price: price.value,
        categoryId: selectedCategory.value
    })

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
        await api.post(
            '/api/products',
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
</style>
