<template>
  <div class="admin-dashboard">
    <header class="admin-header">
      <h1>🐂 管理后台</h1>
      <div class="admin-actions">
        <router-link to="/admin/post" class="btn-primary">
          + 新建文章
        </router-link>
      </div>
    </header>

    <div v-if="blogStore.loading" class="loading">加载中...</div>

    <div v-else-if="blogStore.posts.length === 0" class="empty">
      <p>暂无文章</p>
      <router-link to="/admin/post" class="btn-primary">创建第一篇文章</router-link>
    </div>

    <div v-else class="posts-table">
      <table>
        <thead>
          <tr>
            <th>标题</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="post in blogStore.posts" :key="post.id">
            <td>{{ post.title }}</td>
            <td>
              <span :class="['status', post.published ? 'published' : 'draft']">
                {{ post.published ? '已发布' : '草稿' }}
              </span>
            </td>
            <td>{{ formatDate(post.createdAt) }}</td>
            <td class="actions">
              <router-link :to="`/admin/post/${post.id}`" class="btn-edit">编辑</router-link>
              <button @click="handleDelete(post.id)" class="btn-delete">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useBlogStore } from '../../stores/blog'

const blogStore = useBlogStore()

onMounted(() => {
  blogStore.fetchPosts()
})

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  })
}

async function handleDelete(id) {
  if (!confirm('确定要删除这篇文章吗？')) return
  
  try {
    await blogStore.removePost(id)
  } catch (e) {
    alert('删除失败：' + e.message)
  }
}
</script>

<style scoped>
.admin-dashboard {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.admin-header h1 {
  font-size: 1.5rem;
  color: #1a1a1a;
}

.btn-primary {
  display: inline-block;
  padding: 0.75rem 1.5rem;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  text-decoration: none;
  border-radius: 8px;
  font-weight: 500;
  transition: opacity 0.2s;
}

.btn-primary:hover {
  opacity: 0.9;
}

.loading,
.empty {
  text-align: center;
  padding: 3rem;
  color: #666;
}

.empty .btn-primary {
  margin-top: 1rem;
}

.posts-table {
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #e5e7eb;
}

th {
  background: #f9fafb;
  font-weight: 600;
  color: #374151;
}

.status {
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.75rem;
  font-weight: 500;
}

.status.published {
  background: #d1fae5;
  color: #065f46;
}

.status.draft {
  background: #fef3c7;
  color: #92400e;
}

.actions {
  display: flex;
  gap: 0.5rem;
}

.btn-edit {
  padding: 0.375rem 0.75rem;
  background: #3b82f6;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-size: 0.875rem;
}

.btn-delete {
  padding: 0.375rem 0.75rem;
  background: #ef4444;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.875rem;
  cursor: pointer;
}
</style>