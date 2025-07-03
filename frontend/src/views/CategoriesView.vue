<template>
    <div>
        <div class="header">
            <h1>Categories</h1>
            <button @click="showForm(add)">➕</button>
        </div>

        <CategoryForm v-if="formVisible" :id="editingId" @done="onFormDone" />

        <CategoriesList @edit="showForm" :refresh-key="forceRefresh" />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import CategoriesList from '../components/CategoriesList.vue'
import CategoryForm from './new/CategoryForm.vue'

const editingId = ref(null)
const formVisible = ref(false)
const forceRefresh = ref(0)

function showForm(id) {
    formVisible.value = true
    editingId.value = id
}

function onFormDone() {
    editingId.value = null
    formVisible.value = false

    forceRefresh.value++
}
</script>

<style scoped>
* {
    color: white;
}

.header {
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-between;
    margin: 10px;
}
</style>
