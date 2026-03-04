<template>
  <div class="blog-post">
    <div v-if="blogStore.loading" class="loading">加载中...</div>
    
    <article v-else-if="blogStore.currentPost" class="article">
      <header class="article-header">
        <h1>{{ blogStore.currentPost.title }}</h1>
        <div class="article-meta">
          <span>{{ formatDate(blogStore.currentPost.createdAt) }}</span>
          <span v-if="blogStore.currentPost.author">· {{ blogStore.currentPost.author }}</span>
        </div>
      </header>
      
      <div class="article-content" v-html="renderedContent"></div>
      
      <div class="article-tags" v-if="blogStore.currentPost.tags?.length">
        <span v-for="tag in blogStore.currentPost.tags" :key="tag" class="tag">{{ tag }}</span>
      </div>
    </article>
    
    <div v-else class="not-found">
      <h2>文章不存在</h2>
      <router-link to="/">返回首页</router-link>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { useBlogStore } from '../stores/blog'
import { marked } from 'marked'

const route = useRoute()
const blogStore = useBlogStore()

const renderedContent = computed(() => {
  if (!blogStore.currentPost?.content) return ''
  return marked(blogStore.currentPost.content)
})

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}

onMounted(() => {
  if (route.params.id) {
    blogStore.fetchPost(route.params.id)
  }
})

watch(() => route.params.id, (newId) => {
  if (newId) {
    blogStore.fetchPost(newId)
  }
})
</script>

<style scoped>
.blog-post {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.loading,
.not-found {
  text-align: center;
  padding: 3rem;
  color: #666;
}

.article-header {
  margin-bottom: 2rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #e5e7eb;
}

.article-header h1 {
  font-size: 2rem;
  color: #1a1a1a;
  margin-bottom: 0.5rem;
}

.article-meta {
  color: #666;
  font-size: 0.875rem;
}

.article-content {
  line-height: 1.8;
  color: #333;
}

.article-content :deep(h1),
.article-content :deep(h2),
.article-content :deep(h3) {
  margin-top: 2rem;
  margin-bottom: 1rem;
  color: #1a1a1a;
}

.article-content :deep(p) {
  margin-bottom: 1rem;
}

.article-content :deep(code) {
  background: #f3f4f6;
  padding: 0.125rem 0.375rem;
  border-radius: 4px;
  font-size: 0.875rem;
}

.article-content :deep(pre) {
  background: #1a1a1a;
  color: #e5e7eb;
  padding: 1rem;
  border-radius: 8px;
  overflow-x: auto;
  margin: 1rem 0;
}

.article-content :deep(pre code) {
  background: transparent;
  padding: 0;
}

.article-tags {
  margin-top: 2rem;
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
}

.tag {
  background: #f3f4f6;
  padding: 0.25rem 0.75rem;
  border-radius: 16px;
  font-size: 0.875rem;
  color: #666;
}
</style>