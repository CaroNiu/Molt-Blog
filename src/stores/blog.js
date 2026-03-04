import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getPosts, getPost, createPost, updatePost, deletePost } from '../api'

export const useBlogStore = defineStore('blog', () => {
  const posts = ref([])
  const currentPost = ref(null)
  const loading = ref(false)
  const error = ref(null)

  async function fetchPosts() {
    loading.value = true
    error.value = null
    try {
      const data = await getPosts()
      posts.value = data
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  async function fetchPost(id) {
    loading.value = true
    error.value = null
    try {
      const data = await getPost(id)
      currentPost.value = data
    } catch (e) {
      error.value = e.message
    } finally {
      loading.value = false
    }
  }

  async function addPost(postData) {
    loading.value = true
    error.value = null
    try {
      const data = await createPost(postData)
      posts.value.unshift(data)
      return data
    } catch (e) {
      error.value = e.message
      throw e
    } finally {
      loading.value = false
    }
  }

  async function editPost(id, postData) {
    loading.value = true
    error.value = null
    try {
      const data = await updatePost(id, postData)
      const index = posts.value.findIndex(p => p.id === id)
      if (index !== -1) {
        posts.value[index] = data
      }
      return data
    } catch (e) {
      error.value = e.message
      throw e
    } finally {
      loading.value = false
    }
  }

  async function removePost(id) {
    loading.value = true
    error.value = null
    try {
      await deletePost(id)
      posts.value = posts.value.filter(p => p.id !== id)
    } catch (e) {
      error.value = e.message
      throw e
    } finally {
      loading.value = false
    }
  }

  return {
    posts,
    currentPost,
    loading,
    error,
    fetchPosts,
    fetchPost,
    addPost,
    editPost,
    removePost
  }
})