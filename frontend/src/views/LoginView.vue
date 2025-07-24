<template>
  <div class="login-view">
    <LoginForm />
  </div>
</template>

<script setup>
import LoginForm from "../components/LoginForm.vue";
</script>

<style scoped>
.login-view {
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  /* Hot reload test comment */
}
</style>