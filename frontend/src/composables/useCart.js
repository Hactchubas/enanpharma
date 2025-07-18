import { ref, computed } from 'vue'

const cartItems = ref([])

export function useCart() {
    // Carregar carrinho do localStorage na inicialização
    const loadCart = () => {
        const savedCart = localStorage.getItem('enanpharma-cart')
        if (savedCart) {
            cartItems.value = JSON.parse(savedCart)
        }
    }

    // Salvar carrinho no localStorage
    const saveCart = () => {
        localStorage.setItem('enanpharma-cart', JSON.stringify(cartItems.value))
    }

    // Adicionar produto ao carrinho
    const addToCart = (product, quantity = 1) => {
        const existingItem = cartItems.value.find(item => item.id === product.id)
        
        if (existingItem) {
            // Se o produto já existe, atualizar quantidade
            existingItem.quantity += quantity
        } else {
            // Se é novo produto, adicionar ao carrinho
            cartItems.value.push({
                id: product.id,
                name: product.name,
                price: product.price,
                imageUrl: product.imageUrl,
                quantity: quantity,
                maxQuantity: product.quantity // estoque disponível
            })
        }
        
        saveCart()
    }

    // Remover produto do carrinho
    const removeFromCart = (productId) => {
        const index = cartItems.value.findIndex(item => item.id === productId)
        if (index !== -1) {
            cartItems.value.splice(index, 1)
            saveCart()
        }
    }

    // Atualizar quantidade do produto no carrinho
    const updateQuantity = (productId, newQuantity) => {
        const item = cartItems.value.find(item => item.id === productId)
        if (item) {
            if (newQuantity <= 0) {
                removeFromCart(productId)
            } else if (newQuantity <= item.maxQuantity) {
                item.quantity = newQuantity
                saveCart()
            }
        }
    }

    // Limpar carrinho
    const clearCart = () => {
        cartItems.value = []
        saveCart()
    }

    // Computadas
    const totalItems = computed(() => {
        return cartItems.value.reduce((total, item) => total + item.quantity, 0)
    })

    const totalPrice = computed(() => {
        return cartItems.value.reduce((total, item) => total + (item.price * item.quantity), 0)
    })

    const isEmpty = computed(() => {
        return cartItems.value.length === 0
    })

    // Verificar se produto está no carrinho
    const isInCart = (productId) => {
        return cartItems.value.some(item => item.id === productId)
    }

    // Obter quantidade do produto no carrinho
    const getQuantityInCart = (productId) => {
        const item = cartItems.value.find(item => item.id === productId)
        return item ? item.quantity : 0
    }

    // Inicializar carrinho
    loadCart()

    return {
        cartItems: computed(() => cartItems.value),
        totalItems,
        totalPrice,
        isEmpty,
        addToCart,
        removeFromCart,
        updateQuantity,
        clearCart,
        isInCart,
        getQuantityInCart
    }
}