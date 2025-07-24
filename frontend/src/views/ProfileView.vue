<template>
  <div class="profile-view">
    <div class="profile-container">
      <div class="profile-card">
        <div class="profile-header">
          <div class="profile-avatar">
            <div class="avatar-placeholder">
              {{ getInitials() }}
            </div>
          </div>
          <h2 class="profile-title">Seu Perfil</h2>
          <p class="profile-subtitle">Gerencie as informações da sua conta</p>
        </div>

        <form @submit.prevent="handleEdit" class="profile-form">
          <div class="form-section">
            <h3 class="section-title">Informações Pessoais</h3>
            
            <div class="form-row">
              <div class="form-group">
                <label for="username" class="form-label">Nome de Usuário</label>
                <input 
                  v-model="form.username" 
                  id="username" 
                  type="text"
                  class="form-input"
                  placeholder="Digite seu nome de usuário"
                />
              </div>
              <div class="form-group">
                <label for="firstName" class="form-label">Nome</label>
                <input 
                  v-model="form.firstName" 
                  id="firstName" 
                  type="text"
                  class="form-input"
                  placeholder="Digite seu nome"
                />
              </div>
              
              <div class="form-group">
                <label for="lastName" class="form-label">Sobrenome</label>
                <input 
                  v-model="form.lastName" 
                  id="lastName" 
                  type="text"
                  class="form-input"
                  placeholder="Digite seu sobrenome"
                />
              </div>

            </div>

            <div class="form-row">
              <div class="form-group">
                <label for="email" class="form-label">Email</label>
                <input 
                  v-model="form.email" 
                  id="email" 
                  type="email"
                  class="form-input"
                  placeholder="Digite seu endereço de email"
                />
              </div>

              <div class="form-group">
                <label for="address" class="form-label">Endereço</label>
                <input 
                  v-model="form.address" 
                  id="address" 
                  type="text"
                  class="form-input"
                  placeholder="Digite seu endereço"
                />
              </div>
            </div>
          </div>

          <div class="form-section">
            <h3 class="section-title">Configurações da Conta</h3>
            
            <div class="form-row">
              <div class="form-group">
                <label for="password" class="form-label">Nova Senha</label>
                <input 
                  v-model="form.password" 
                  id="password" 
                  type="password"
                  class="form-input"
                  placeholder="Digite uma nova senha"
                />
                <small class="form-help">Deixe em branco para manter a senha atual</small>
              </div>
            </div>
          </div>

          <div class="form-actions">
            <button type="submit" class="btn btn-primary save-btn" :disabled="loading">
              <span v-if="loading">Salvando...</span>
              <span v-else>Salvar Alterações</span>
            </button>
            
            <button type="button" class="btn btn-error delete-btn" @click="handleDelete" :disabled="loading">
              Excluir Conta
            </button>
          </div>

          <div v-if="message" class="message" :class="messageType">
            {{ message }}
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import api from "../api/axios";

const router = useRouter();
const loading = ref(false);
const message = ref("");
const messageType = ref("");

const form = ref({
  firstName: "",
  username: "",
  lastName: "",
  email: "",
  address: "",
  password: "",
  roles: ["USER"],
});

const token = localStorage.getItem("token");
const username = localStorage.getItem("username");

onMounted(fetchUserData);

function getInitials() {
  const first = form.value.firstName || '';
  const last = form.value.lastName || '';
  return (first.charAt(0) + last.charAt(0)).toUpperCase() || 'U';
}

function showMessage(msg, type = 'success') {
  message.value = msg;
  messageType.value = type;
  setTimeout(() => {
    message.value = "";
  }, 5000);
}

async function fetchUserData() {
  if (!token || !username) {
    router.push("/login");
    return;
  }

  try {
    loading.value = true;
    const res = await api.get(`/users/username/${username}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    const data = res.data;
    form.value.firstName = data.firstName || '';
    form.value.lastName = data.lastName || '';
    form.value.email = data.email || '';
    form.value.address = data.address || '';
    form.value.roles = data.roles || ['USER'];
    form.value.username = data.username || '';

    localStorage.setItem('userId', data.id);
    localStorage.setItem('username', data.username);
    
  } catch (err) {
    console.error(err);
    showMessage("Erro ao carregar perfil. Tente fazer login novamente.", "error");
    setTimeout(() => router.push("/login"), 2000);
  } finally {
    loading.value = false;
  }
}

async function handleEdit() {
  if (!token) {
    router.push("/login");
    return;
  }

  try {
    loading.value = true;
    const userId = localStorage.getItem('userId');
    
    const updateData = {
      firstName: form.value.firstName,
      lastName: form.value.lastName,
      email: form.value.email,
      address: form.value.address,
      username: form.value.username,
      roles: form.value.roles
    };

    // Only include password if it's not empty
    if (form.value.password && form.value.password.trim()) {
      updateData.password = form.value.password;
    }

    const res = await api.put(`/users/${userId}`, updateData, {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    showMessage("Perfil atualizado com sucesso!", "success");
    form.value.password = ""; // Clear password field after successful update
    
  } catch (error) {
    console.error(error);
    showMessage("Falha ao atualizar perfil. Tente novamente.", "error");
  } finally {
    loading.value = false;
  }
}

async function handleDelete() {
  if (!confirm("Tem certeza que deseja excluir sua conta? Esta ação não pode ser desfeita.")) {
    return;
  }

  try {
    loading.value = true;
    const userId = localStorage.getItem('userId');
    
    await api.delete(`/users/${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("userId");
    
    showMessage("Conta excluída com sucesso.", "success");
    setTimeout(() => router.push("/login"), 2000);
    
  } catch (error) {
    console.error(error);
    showMessage("Falha ao excluir conta. Tente novamente.", "error");
  } finally {
    loading.value = false;
  }
}
</script>


<style scoped>
.profile-view {
  width: 100%;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: var(--spacing-lg);
}

.profile-container {
  width: 100%;
  max-width: 1200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.profile-card {
  width: 100%;
  max-width: 1000px;
  background: var(--surface-bg);
  border: 1px solid var(--border-light);
  border-radius: var(--radius-xl);
  padding: var(--spacing-xl);
  box-shadow: var(--shadow-lg);
  animation: fadeIn 0.5s ease-out;
}

.profile-header {
  text-align: center;
  margin-bottom: var(--spacing-lg);
  padding-bottom: var(--spacing-md);
  border-bottom: 1px solid var(--border-light);
}

.profile-avatar {
  margin-bottom: var(--spacing-md);
}

.avatar-placeholder {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: var(--accent-primary);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 auto;
  box-shadow: var(--shadow-md);
}

.profile-title {
  color: var(--text-primary);
  font-size: 1.75rem;
  font-weight: 600;
  margin-bottom: var(--spacing-sm);
  letter-spacing: -0.025em;
}

.profile-subtitle {
  color: var(--text-secondary);
  font-size: 0.875rem;
  margin-bottom: 0;
}

.profile-form {
  display: flex;
  flex-direction: column;
  gap: var(--spacing-lg);
}

.form-section {
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: var(--spacing-md);
}

.section-title {
  color: var(--text-primary);
  font-size: 1rem;
  font-weight: 600;
  margin-bottom: var(--spacing-xs);
  padding-bottom: var(--spacing-xs);
  border-bottom: 1px solid var(--border-light);
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: var(--spacing-md);
}

@media (max-width: 900px) {
  .form-row {
    grid-template-columns: 1fr 1fr;
  }
}

.form-help {
  color: var(--text-muted);
  font-size: 0.75rem;
  margin-top: var(--spacing-xs);
  display: block;
}

.form-input:disabled {
  background: var(--tertiary-bg);
  color: var(--text-muted);
  cursor: not-allowed;
  opacity: 0.7;
}

.form-actions {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  gap: var(--spacing-md);
  margin-top: var(--spacing-md);
}

.save-btn {
  min-width: 180px;
  font-weight: 600;
  letter-spacing: 0.025em;
}

.delete-btn {
  min-width: 150px;
  font-weight: 600;
  letter-spacing: 0.025em;
  background: var(--accent-error) !important;
  color: white !important;
  border: 1px solid var(--accent-error) !important;
}

.delete-btn:hover:not(:disabled) {
  background: #dc2626 !important;
  transform: translateY(-1px);
}

.message {
  padding: var(--spacing-sm) var(--spacing-md);
  border-radius: var(--radius-md);
  font-size: 0.875rem;
  text-align: center;
  margin-top: var(--spacing-md);
  animation: fadeIn 0.3s ease-out;
}

.message.success {
  background: rgba(16, 185, 129, 0.1);
  border: 1px solid rgba(16, 185, 129, 0.3);
  color: var(--accent-success);
}

.message.error {
  background: rgba(239, 68, 68, 0.1);
  border: 1px solid rgba(239, 68, 68, 0.3);
  color: var(--accent-error);
}

/* Loading state */
.form-input:disabled,
.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

@media (max-width: 640px) {
  .profile-view {
    padding: var(--spacing-md);
    align-items: flex-start;
    min-height: calc(100vh - 2 * var(--spacing-md));
  }
  
  .profile-card {
    padding: var(--spacing-xl);
  }
  
  .profile-title {
    font-size: 1.5rem;
  }
  
  .form-row {
    grid-template-columns: 1fr;
  }
  
  .form-actions {
    flex-direction: column;
    align-items: flex-end;
  }
  
  .save-btn,
  .delete-btn {
    min-width: 200px;
    width: auto;
  }
}

@media (max-width: 480px) {
  .profile-view {
    padding: var(--spacing-sm);
  }
  
  .profile-card {
    padding: var(--spacing-lg);
  }
  
  .avatar-placeholder {
    width: 50px;
    height: 50px;
    font-size: 1.25rem;
  }
}
</style>
