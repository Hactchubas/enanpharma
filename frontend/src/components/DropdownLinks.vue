<template>
    <div class="dropdown">
        <button @click="toggleDropdown" class="dropdown-toggle">
            {{ buttonText }}
        </button>
        <ul v-if="open" class="custom-dropdown-menu">
            <li v-for="(item, index) in items" :key="index" @click="goTo(item.path)">
                {{ item.label }}
            </li>
        </ul>
    </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

defineProps({
    items: {
        type: Array,
        required: true
    },
    buttonText: {
        type: String,
        default: 'Options'
    }
})

const open = ref(false)

function toggleDropdown() {
    console.log(open.value)
    open.value = !open.value
}

function goTo(path) {
    window.location.href = path
}

function handleClickOutside(event) {
    const dropdown = document.querySelector('.dropdown')
    if (dropdown && !dropdown.contains(event.target)) {
        open.value = false
    }
}

onMounted(() => document.addEventListener('click', handleClickOutside))
onBeforeUnmount(() => document.removeEventListener('click', handleClickOutside))
</script>

<style scoped>
.dropdown {
    position: relative;
    display: inline-block;
}

.dropdown-toggle {
    background-color: #222;
    border: none;
    padding: 0.6rem 1.2rem;
    font-size: 1rem;
    border-radius: 8px;
    cursor: pointer;
    transition: background-color 0.2s;
    transition: color 0.2s;
}

.dropdown-toggle:hover {
    background-color: #eaeaea;
    color: #222;
}

.custom-dropdown-menu {
    position: absolute;
    top: 110%;
    left: 0;
    background: #222;
    border: 1px solid #ccc;
    border-radius: 8px;
    list-style: none;
    padding: 0;
    margin: 0;
    min-width: 160px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    z-index: 1000;
}

.custom-dropdown-menu li {
    padding: 0.8rem 1rem;
    cursor: pointer;
    color: white;
}

.custom-dropdown-menu li:hover {
    background-color: #f0f0f0;
    color: #222;
}
</style>
