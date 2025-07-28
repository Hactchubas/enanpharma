import api from './axios.js'
import auth from './auth.js'

// Get all users (admin only)
export const getAllUsers = async () => {
  try {
    const response = await api.get('/users', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth.token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('Error fetching users:', error)
    throw error
  }
}

// Get user by ID
export const getUserById = async (userId) => {
  try {
    const response = await api.get(`/users/${userId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth.token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('Error fetching user:', error)
    throw error
  }
}

// Update user
export const updateUser = async (userId, userData) => {
  try {
    const response = await api.put(`/users/${userId}`, userData, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth.token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('Error updating user:', error)
    throw error
  }
}

// Delete user
export const deleteUser = async (userId) => {
  try {
    const response = await api.delete(`/users/${userId}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth.token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('Error deleting user:', error)
    throw error
  }
}

// Create new user (admin only)
export const createUser = async (userData) => {
  try {
    const response = await api.post('/auth/signup', userData, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth.token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('Error creating user:', error)
    throw error
  }
}

// Toggle user active status
export const toggleUserStatus = async (userId, isActive) => {
  try {
    const response = await api.patch(`/users/${userId}/status`, { active: isActive }, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth.token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('Error updating user status:', error)
    throw error
  }
}

// Update user roles
export const updateUserRoles = async (userId, roles) => {
  try {
    const response = await api.patch(`/users/${userId}/roles`, { roles }, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${auth.token}`
      }
    })
    return response.data
  } catch (error) {
    console.error('Error updating user roles:', error)
    throw error
  }
}
