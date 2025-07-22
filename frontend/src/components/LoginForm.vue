<template>
    <form @submit.prevent="login">
        <label>Username
            <input v-model="username" type="text" required />
        </label>
        <label>Password
            <input v-model="password" type="password" required />
        </label>
        <button type="submit">Login</button>
        <p v-if="error" style='color: red'> {{ error }} </p>
    </form>
</template>


<script setup>
import { ref } from "vue";
import api from "../api/axios";
import { useRouter } from 'vue-router'
import auth from '../api/auth.js'

const username = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()


async function login() {
    try {
        const response = await api.post("/auth/login", {
            username: username.value,
            password: password.value,
        });
        localStorage.setItem("token", response.data.accessToken);
        localStorage.setItem("username", username.value);

        auth.login(response.data.accessToken, username.value)
        router.push('/')
        return true;
    } catch (err) {
        error.value = 'Login or password incorrect'
        return false;
    }
}

</script>

<style scoped>
form {
    display: flex;
    flex-flow: column nowrap;
    gap: 20px;
    width: 100%;
    justify-content: center;
    align-items: center;

    margin: 30px 0px;
}

label {
    width: 50%;
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
