<template>
    <div class="home">
        <AdminPanel />
        <ProductCardList />
    </div>
</template>

<script setup>
import ProductCardList from "../components/ProductCardList.vue";
import AdminPanel from "../components/AdminPanel.vue";




import { ref, onMounted } from 'vue'
import auth from '../api/auth.js'
import api from '../api/axios'
import { useRouter } from 'vue-router'
const router = useRouter()
import { computed } from "vue";


let username = ref(null)
let isLoggedIn = ref(false)


onMounted(async () => {
    isLoggedIn.value = auth.isLoggedIn()
    const token = localStorage.getItem('token')
    if (token) {
        username.value = localStorage.getItem('username')
    }
})

const logout = () => {
    localStorage.removeItem('username')
    localStorage.removeItem('token')
    username.value = null
    router.push('/')
}
</script>

<style scoped>
button {
    margin: 0 10px;
}
</style>
