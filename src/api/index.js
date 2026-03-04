import axios from 'axios'
import { useUserStore } from '../stores/user'

const api = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器 - 添加 token
api.interceptors.request.use(
  config => {
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers.Authorization = `Bearer ${userStore.token}`
    }
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器 - 处理错误
api.interceptors.response.use(
  response => response.data,
  error => {
    if (error.response?.status === 401) {
      const userStore = useUserStore()
      userStore.logout()
      window.location.href = '/admin/login'
    }
    return Promise.reject(error)
  }
)

// 认证 API
export const login = (username, password) => 
  api.post('/auth/login', { username, password })

export const logout = () => 
  api.post('/auth/logout')

export const getCurrentUser = () => 
  api.get('/auth/me')

// 博客 API
export const getPosts = (params) => 
  api.get('/posts', { params })

export const getPost = (id) => 
  api.get(`/posts/${id}`)

export const createPost = (data) => 
  api.post('/posts', data)

export const updatePost = (id, data) => 
  api.put(`/posts/${id}`, data)

export const deletePost = (id) => 
  api.delete(`/posts/${id}`)

export default api