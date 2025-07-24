<template>
  <div class="signup-view">
    <div class="signup-container">
      <div class="signup-card">
        <div class="signup-header">
          <h2 class="signup-title">Create Your Account</h2>
          <p class="signup-subtitle">Join us and start your journey</p>
        </div>
        
        <form @submit.prevent="handleSignUp" class="signup-form">
          <div class="form-row">
            <div class="form-group">
              <label for="username" class="form-label">Username *</label>
              <input 
                id="username"
                v-model="form.username" 
                type="text"
                class="form-input"
                placeholder="Choose a username"
                required 
              />
            </div>
            
            <div class="form-group">
              <label for="email" class="form-label">Email *</label>
              <input 
                id="email"
                v-model="form.email" 
                type="email"
                class="form-input"
                placeholder="Enter your email"
                required 
              />
            </div>
          </div>
          
          <div class="form-group">
            <label for="password" class="form-label">Password *</label>
            <input 
              id="password"
              v-model="form.password" 
              type="password"
              class="form-input"
              placeholder="Create a secure password"
              required 
            />
          </div>
          
          <div class="form-row">
            <div class="form-group">
              <label for="firstName" class="form-label">First Name</label>
              <input 
                id="firstName"
                v-model="form.firstName" 
                type="text"
                class="form-input"
                placeholder="Your first name"
              />
            </div>
            
            <div class="form-group">
              <label for="lastName" class="form-label">Last Name</label>
              <input 
                id="lastName"
                v-model="form.lastName" 
                type="text"
                class="form-input"
                placeholder="Your last name"
              />
            </div>
          </div>
          
          <button type="submit" class="signup-btn">
            <div class="btn-icon-wrapper">
              <svg class="btn-icon" width="16" height="16" viewBox="0 0 24 24" fill="none">
                <path d="M16 21V19C16 17.9391 15.5786 16.9217 14.8284 16.1716C14.0783 15.4214 13.0609 15.8 12 15.8H5C3.93913 15.8 2.92172 16.2214 2.17157 16.9716C1.42143 17.7217 1 18.7391 1 19.8V21.8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <circle cx="8.5" cy="7" r="4" stroke="currentColor" stroke-width="2"/>
                <path d="M20 8V14" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M23 11H17" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
            </div>
            <span class="btn-text">Cadastrar</span>
          </button>
          
          <div v-if="error" class="error-message">
            {{ error }}
          </div>
          
          <div class="login-link">
            <p>Already have an account? <router-link to="/login" class="link">Sign in</router-link></p>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import api from '../api/axios.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const error = ref('')

const form = reactive({
  id: '',
  username: '',
  password: '',
  email: '',
  firstName: '',
  lastName: '',
  address: '',
  roles: ["USER"],
})

const handleSignUp = async () => {
  try {
    await api.post('/auth/signup', form)
    alert("User created!")
    router.push('/login')
  } catch (err) {
    error.value = 'Signup failed. Username or email might already exist.'
  }
}
</script>

<style scoped>
.signup-view {
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: var(--spacing-lg) 0;
}

.signup-container {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-lg);
}

.signup-card {
  width: 100%;
  max-width: 500px;
  background: var(--surface-bg);
  border: 1px solid var(--border-light);
  border-radius: var(--radius-xl);
  padding: var(--spacing-2xl);
  box-shadow: var(--shadow-lg);
  animation: fadeIn 0.5s ease-out;
}

.signup-header {
  text-align: center;
  margin-bottom: var(--spacing-2xl);
}

.signup-title {
  color: var(--text-primary);
  font-size: 1.75rem;
  font-weight: 600;
  margin-bottom: var(--spacing-sm);
  letter-spacing: -0.025em;
}

.signup-subtitle {
  color: var(--text-secondary);
  font-size: 0.875rem;
  margin-bottom: 0;
}

.signup-form {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: var(--spacing-md);
}

.signup-btn {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: var(--spacing-sm);
  width: 100%;
  padding: var(--spacing-md) var(--spacing-lg);
  border: 1px solid var(--border-light);
  border-radius: var(--radius-md);
  background: rgba(115, 115, 115, 0.05);
  color: var(--text-secondary);
  font-size: 0.875rem;
  font-weight: 500;
  transition: all var(--transition-fast);
  cursor: pointer;
  white-space: nowrap;
  margin-top: var(--spacing-md);
}

.signup-btn:hover {
  transform: translateY(-1px);
  background: rgba(115, 115, 115, 0.15);
  border-color: var(--border-medium);
  color: var(--text-primary);
  box-shadow: var(--shadow-sm);
}

.btn-icon-wrapper {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 28px;
  height: 28px;
  background: rgba(115, 115, 115, 0.15);
  border: 1px solid var(--border-light);
  border-radius: var(--radius-md);
  transition: all var(--transition-fast);
}

.signup-btn:hover .btn-icon-wrapper {
  background: rgba(115, 115, 115, 0.25);
  border-color: var(--border-medium);
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

.login-link {
  text-align: center;
  margin-top: var(--spacing-lg);
  padding-top: var(--spacing-lg);
  border-top: 1px solid var(--border-light);
}

.login-link p {
  color: var(--text-secondary);
  font-size: 0.875rem;
  margin: 0;
}

.link {
  color: var(--accent-primary);
  text-decoration: none;
  font-weight: 500;
  transition: color var(--transition-fast);
}

.link:hover {
  color: var(--accent-primary-hover);
}

@media (max-width: 640px) {
  .signup-container {
    padding: var(--spacing-md);
  }
  
  .signup-card {
    padding: var(--spacing-xl);
  }
  
  .signup-title {
    font-size: 1.5rem;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {
  .signup-view {
    padding: var(--spacing-md) 0;
  }
  
  .signup-card {
    padding: var(--spacing-lg);
  }
}
</style>
