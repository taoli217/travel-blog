import axios from 'axios'
import { ElMessage } from 'element-plus'

const request = axios.create({
  baseURL: '/api/v1',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res.data
  },
  error => {
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request

// 用户相关API
export const userAPI = {
  login: (data) => request.post('/users/login', data),
  register: (data) => request.post('/users/register', data),
  getUserInfo: (id) => request.get(`/users/${id}`)
}

// 文章相关API
export const articleAPI = {
  list: (params) => request.get('/articles', { params }),
  get: (id) => request.get(`/articles/${id}`),
  create: (data) => request.post('/articles', data),
  update: (id, data) => request.put(`/articles/${id}`, data),
  delete: (id) => request.delete(`/articles/${id}`)
}

// 分类相关API
export const categoryAPI = {
  list: () => request.get('/categories'),
  create: (data) => request.post('/categories', data)
}

// 标签相关API
export const tagAPI = {
  list: () => request.get('/tags')
}

// 评论相关API
export const commentAPI = {
  list: (articleId, params) => request.get(`/articles/${articleId}/comments`, { params }),
  create: (articleId, data) => request.post(`/articles/${articleId}/comments`, data)
}

// 点赞相关API
export const likeAPI = {
  toggle: (articleId) => request.post(`/articles/${articleId}/like`)
}
