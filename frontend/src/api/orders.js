import api from './axios.js'
import auth from './auth.js'

// Get headers with auth token
const getAuthHeaders = () => {
    const token = auth.token
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    }
}

// Create a new order
export const createOrder = async (orderData) => {
    const response = await api.post('/orders', orderData, {
        headers: getAuthHeaders()
    })
    return response.data
}

// Get all orders for current user
export const getUserOrders = async () => {
    const response = await api.get('/orders', {
        headers: getAuthHeaders()
    })
    return response.data
}

// Get specific order by ID
export const getOrderById = async (orderId) => {
    const response = await api.get(`/orders/${orderId}`, {
        headers: getAuthHeaders()
    })
    return response.data
}

// Cancel order
export const cancelOrder = async (orderId) => {
    const response = await api.post(`/orders/${orderId}/cancel`, {}, {
        headers: getAuthHeaders()
    })
    return response.data
}

// Admin endpoints
export const getAllOrders = async () => {
    const response = await api.get('/orders/admin/all', {
        headers: getAuthHeaders()
    })
    return response.data
}

export const updateOrderStatus = async (orderId, status) => {
    const response = await api.put(`/orders/admin/${orderId}/status`, { status }, {
        headers: getAuthHeaders()
    })
    return response.data
}

export default {
    createOrder,
    getUserOrders,
    getOrderById,
    cancelOrder,
    getAllOrders,
    updateOrderStatus
}
