<template>
    <div>

        <div class="header">
            <h1>Products</h1>
            <button v-if="!formVisible" @click="showForm(add)">➕</button>
        </div>
        <ProductForm v-if="formVisible" :key="editingId" :id="editingId" @done="onFormDone" />

        <ProductsList :refresh-key="refreshKey" @edit="showForm" />
    </div>
</template>

<script setup>
import { ref } from 'vue'
import ProductsList from '../components/ProductsList.vue'
import ProductForm from './new/ProductForm.vue'

const formVisible = ref(false)
const editingId = ref(null)
const refreshKey = ref(0)

function showForm(id = null) {
    editingId.value = id
    formVisible.value = true
}

function onFormDone() {
    formVisible.value = false
    editingId.value = null
    refreshKey.value++
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
