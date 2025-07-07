<script setup>
import auth from "../api/auth.js";
import { ref, onMounted, onUpdated } from "vue";
import { useRouter } from 'vue-router'
const router = useRouter()

const info = ref({
    isAdmin: false
})

onMounted(() => {
    if (auth.isLoggedIn()) fetchUserInfo()
})
onUpdated(fetchUserInfo)
async function fetchUserInfo() {
    const res = await auth.getUserInfo()
    if (res == null) {
        info.value.isAdmin = false
        return
    }
    const roles = res.roles
    if (roles.includes("ADMIN")) {
        info.value.isAdmin = true
    } else {
        info.value.isAdmin = false
    }
}

function logout() {
    auth.logout();
    router.push('/')
}


import RegisterDropdown from './DropdownLinks.vue'

const registerOptions = [
    { label: 'Category', path: '/categories' },
    { label: 'Product', path: '/products' },
]
</script>

<template>
    <nav class="navbar">
        <div class="navbar-left">
            <a href="/" class="brand">Enanpharma<span v-if='info.isAdmin'> [ADMIN]</span></a>
        </div>
        <div class="navbar-middle">
            <h1>
                <a href="/profile">{{ auth.user }}</a>
            </h1>
        </div>
        <div class="navbar-right">
            <template v-if="auth.isLoggedIn()">
                <div>
                    <RegisterDropdown v-if="info.isAdmin" :items="registerOptions" button-text="Register" />
                    <a @click="logout">Logout</a>
                </div>
            </template>
            <template v-else>
                <a href="/login">Login</a>
                <a href="/signup">Sign Up</a>
            </template>
        </div>
    </nav>
</template>

<style scoped>
.navbar {
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    align-items: center;
    background-color: #222;
    color: white;
    padding: 10px 20px;
}

.navbar a {
    color: white;
    text-decoration: none;
    margin: 0 10px;
}
</style>
