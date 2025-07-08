<template>
    <form @submit.prevent="submit">
        <label>
            Category Name:
            <input v-model="name" required />
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
    id: Number // null for new
})

const emit = defineEmits(['done'])

const name = ref('')

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
    const body = JSON.stringify({ id: props.id, name: name.value })
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
}


// Reload if id changes
watch(() => props.id, () => {
    loadCategories()
})
</script>
