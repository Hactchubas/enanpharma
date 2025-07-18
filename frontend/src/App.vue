
<template>
  <div id="app" class="dark-theme">
    <NavBar/>
    <main class="container-fluid mt-4">
      <router-view/>
    </main>
  </div>
</template>

<script setup>
  import NavBar from './components/NavBar.vue'
</script>

<style scoped>
#app {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

main {
  flex: 1;
}
</style>
