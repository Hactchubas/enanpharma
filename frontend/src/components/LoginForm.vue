<template>
    <div class="login-container">
        <div class="login-card">
            <div class="login-header">
                <h2 class="login-title">Welcome Back</h2>
                <p class="login-subtitle">Sign in to your account</p>
            </div>
            
            <form @submit.prevent="login" class="login-form">
                <div class="form-group">
                    <label for="username" class="form-label">Username</label>
                    <input 
                        id="username"
                        v-model="username" 
                        type="text" 
                        class="form-input"
                        placeholder="Enter your username"
                        required 
                    />
                </div>
                
                <div class="form-group">
                    <label for="password" class="form-label">Password</label>
                    <input 
                        id="password"
                        v-model="password" 
                        type="password" 
                        class="form-input"
                        placeholder="Enter your password"
                        required 
                    />
                </div>
                
                <button type="submit" class="login-btn">
                    <div class="btn-icon-wrapper">
                        <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                            <path d="M15 3H19C19.5304 3 20.0391 3.21071 20.4142 3.58579C20.7893 3.96086 21 4.46957 21 5V19C21 19.5304 20.7893 20.0391 20.4142 20.4142C20.0391 20.7893 19.5304 21 19 21H15" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M10 17L15 12L10 7" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            <path d="M15 12H3" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                        </svg>
                    </div>
                    <span class="btn-text">Entrar</span>
                </button>
                
                <div v-if="error" class="error-message">
                    {{ error }}
                </div>
            </form>
        </div>
    </div>
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
.login-container {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 70vh;
    padding: var(--spacing-lg);
}

.login-card {
    width: 100%;
    max-width: 400px;
    background: var(--surface-bg);
    border: 1px solid var(--border-light);
    border-radius: var(--radius-xl);
    padding: var(--spacing-2xl);
    box-shadow: var(--shadow-lg);
    animation: fadeIn 0.5s ease-out;
}

.login-header {
    text-align: center;
    margin-bottom: var(--spacing-2xl);
}

.login-title {
    color: var(--text-primary);
    font-size: 1.75rem;
    font-weight: 600;
    margin-bottom: var(--spacing-sm);
    letter-spacing: -0.025em;
}

.login-subtitle {
    color: var(--text-secondary);
    font-size: 0.875rem;
    margin-bottom: 0;
}

.login-form {
    display: flex;
    flex-direction: column;
    gap: var(--spacing-lg);
}

.login-btn {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: var(--spacing-sm);
    width: 100%;
    padding: var(--spacing-md) var(--spacing-lg);
    border: 1px solid rgba(59, 130, 246, 0.3);
    border-radius: var(--radius-md);
    background: rgba(59, 130, 246, 0.15);
    color: rgba(59, 130, 246, 0.9);
    font-size: 0.875rem;
    font-weight: 500;
    transition: all var(--transition-fast);
    cursor: pointer;
    white-space: nowrap;
    margin-top: var(--spacing-md);
}

.login-btn:hover {
    transform: translateY(-1px);
    background: rgba(59, 130, 246, 0.25);
    border-color: rgba(59, 130, 246, 0.5);
    color: rgba(59, 130, 246, 1);
    box-shadow: var(--shadow-sm);
}

.btn-icon-wrapper {
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 28px;
    height: 28px;
    background: rgba(59, 130, 246, 0.2);
    border: 1px solid rgba(59, 130, 246, 0.3);
    border-radius: var(--radius-md);
    transition: all var(--transition-fast);
}

.login-btn:hover .btn-icon-wrapper {
    background: rgba(59, 130, 246, 0.3);
    border-color: rgba(59, 130, 246, 0.5);
}

.btn-icon {
    color: currentColor;
    transition: color var(--transition-fast);
}

.btn-text {
    color: currentColor;
    font-weight: 500;
}

.error-message {
    background: rgba(239, 68, 68, 0.1);
    border: 1px solid rgba(239, 68, 68, 0.3);
    color: var(--accent-error);
    padding: var(--spacing-sm) var(--spacing-md);
    border-radius: var(--radius-md);
    font-size: 0.875rem;
    text-align: center;
    animation: fadeIn 0.3s ease-out;
}

@media (max-width: 480px) {
    .login-container {
        padding: var(--spacing-md);
        min-height: 60vh;
    }
    
    .login-card {
        padding: var(--spacing-xl);
    }
    
    .login-title {
        font-size: 1.5rem;
    }
}
</style>
