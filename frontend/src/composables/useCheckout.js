import { ref, computed } from 'vue'
import { useCart } from './useCart.js'
import { createOrder } from '@/api/orders.js'
import auth from '@/api/auth.js'

const checkoutStep = ref(1) // 1: Customer Info, 2: Payment, 3: Confirmation
const isProcessing = ref(false)
const orderSuccess = ref(false)
const orderError = ref('')

// Customer Information
const customerInfo = ref({
    name: '',
    email: '',
    phone: '',
    address: {
        street: '',
        number: '',
        complement: '',
        neighborhood: '',
        city: '',
        state: '',
        zipCode: ''
    }
})

// Payment Information
const paymentInfo = ref({
    method: 'credit_card', // credit_card, debit_card, pix, boleto
    cardNumber: '',
    cardName: '',
    expiryDate: '',
    cvv: '',
    installments: 1
})

export function useCheckout() {
    const { cartItems, totalPrice, clearCart } = useCart()

    // Validation functions
    const validateCustomerInfo = () => {
        const errors = []
        
        if (!customerInfo.value.name.trim()) errors.push('Nome é obrigatório')
        if (!customerInfo.value.email.trim()) errors.push('Email é obrigatório')
        if (!customerInfo.value.phone.trim()) errors.push('Telefone é obrigatório')
        if (!customerInfo.value.address.street.trim()) errors.push('Endereço é obrigatório')
        if (!customerInfo.value.address.number.trim()) errors.push('Número é obrigatório')
        if (!customerInfo.value.address.neighborhood.trim()) errors.push('Bairro é obrigatório')
        if (!customerInfo.value.address.city.trim()) errors.push('Cidade é obrigatória')
        if (!customerInfo.value.address.state.trim()) errors.push('Estado é obrigatório')
        if (!customerInfo.value.address.zipCode.trim()) errors.push('CEP é obrigatório')
        
        return errors
    }

    const validatePaymentInfo = () => {
        const errors = []
        
        if (paymentInfo.value.method === 'credit_card' || paymentInfo.value.method === 'debit_card') {
            if (!paymentInfo.value.cardNumber.trim()) errors.push('Número do cartão é obrigatório')
            if (!paymentInfo.value.cardName.trim()) errors.push('Nome no cartão é obrigatório')
            if (!paymentInfo.value.expiryDate.trim()) errors.push('Data de validade é obrigatória')
            if (!paymentInfo.value.cvv.trim()) errors.push('CVV é obrigatório')
        }
        
        return errors
    }

    // Step navigation
    const nextStep = () => {
        if (checkoutStep.value === 1) {
            const errors = validateCustomerInfo()
            if (errors.length > 0) {
                orderError.value = errors.join(', ')
                return false
            }
        } else if (checkoutStep.value === 2) {
            const errors = validatePaymentInfo()
            if (errors.length > 0) {
                orderError.value = errors.join(', ')
                return false
            }
        }
        
        orderError.value = ''
        checkoutStep.value++
        return true
    }

    const prevStep = () => {
        if (checkoutStep.value > 1) {
            checkoutStep.value--
            orderError.value = ''
        }
    }

    // Order processing
    const processOrder = async () => {
        isProcessing.value = true
        orderError.value = ''

        try {
            // Check if user is logged in
            if (!auth.isLoggedIn()) {
                throw new Error('User must be logged in to place an order')
            }

            // Convert frontend payment method to backend format
            const paymentMethodMap = {
                'credit_card': 'CREDIT_CARD',
                'debit_card': 'DEBIT_CARD',
                'pix': 'PIX',
                'boleto': 'BOLETO'
            }

            // Create order object for backend
            const orderData = {
                customerName: customerInfo.value.name,
                customerEmail: customerInfo.value.email,
                customerPhone: customerInfo.value.phone,
                address: {
                    street: customerInfo.value.address.street,
                    number: customerInfo.value.address.number,
                    complement: customerInfo.value.address.complement,
                    neighborhood: customerInfo.value.address.neighborhood,
                    city: customerInfo.value.address.city,
                    state: customerInfo.value.address.state,
                    zipCode: customerInfo.value.address.zipCode
                },
                items: cartItems.value.map(item => ({
                    productId: item.id,
                    quantity: item.quantity
                })),
                paymentMethod: paymentMethodMap[paymentInfo.value.method],
                paymentInstallments: paymentInfo.value.installments || 1
            }

            // Send order to backend
            const order = await createOrder(orderData)

            // Clear cart after successful order
            clearCart()
            
            orderSuccess.value = true
            return order
            
        } catch (error) {
            console.error('Error processing order:', error)
            if (error.response?.data?.message) {
                orderError.value = error.response.data.message
            } else {
                orderError.value = 'Erro ao processar pedido. Tente novamente.'
            }
            throw error
        } finally {
            isProcessing.value = false
        }
    }

    // Calculate installment value
    const installmentValue = computed(() => {
        if (paymentInfo.value.method === 'credit_card' && paymentInfo.value.installments > 1) {
            return totalPrice.value / paymentInfo.value.installments
        }
        return totalPrice.value
    })

    // Payment method options
    const paymentMethods = [
        { value: 'credit_card', label: 'Cartão de Crédito', icon: '💳' },
        { value: 'debit_card', label: 'Cartão de Débito', icon: '💳' },
        { value: 'pix', label: 'PIX', icon: '📱' },
        { value: 'boleto', label: 'Boleto', icon: '📄' }
    ]

    // Reset checkout
    const resetCheckout = () => {
        checkoutStep.value = 1
        orderSuccess.value = false
        orderError.value = ''
        customerInfo.value = {
            name: '',
            email: '',
            phone: '',
            address: {
                street: '',
                number: '',
                complement: '',
                neighborhood: '',
                city: '',
                state: '',
                zipCode: ''
            }
        }
        paymentInfo.value = {
            method: 'credit_card',
            cardNumber: '',
            cardName: '',
            expiryDate: '',
            cvv: '',
            installments: 1
        }
    }

    return {
        // State
        checkoutStep: computed(() => checkoutStep.value),
        isProcessing: computed(() => isProcessing.value),
        orderSuccess: computed(() => orderSuccess.value),
        orderError: computed(() => orderError.value),
        
        // Data
        customerInfo,
        paymentInfo,
        paymentMethods,
        installmentValue,
        
        // Methods
        nextStep,
        prevStep,
        processOrder,
        resetCheckout,
        validateCustomerInfo,
        validatePaymentInfo
    }
}