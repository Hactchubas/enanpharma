<template>
    <form @submit.prevent="submit">
        <label>
            Product Name:
            <input v-model="name" required />
        </label>

        <label>
            Price:
            <input type="number" v-model.number="price" required min="0" step="0.01" />
        </label>

        <button type="submit">{{ id ? 'Update' : 'Add' }}</button>
        <button type="button" @click="$emit('done')">Cancel</button>
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
const price = ref(0)

async function loadProduct() {
    if (props.id) {
        if (!auth.isLoggedIn()) {
            return
        }
        const token = auth.state.token

        const res = await api.get(`/api/products/${props.id}`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token
            },
        })
        const data = res.data
        name.value = data.name
        price.value = data.price
    } else {
        name.value = ''
        price.value = 0
    }
}

onMounted(loadProduct)

watch(() => props.id, loadProduct)

async function submit() {
    if (!auth.isLoggedIn()) return
    const token = auth.state.token

    const body = JSON.stringify({ id: props.id, name: name.value, price: price.value })

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
