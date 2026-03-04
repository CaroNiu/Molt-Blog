<template>
  <div class="post-card" @click="$router.push(`/blog/${post.id}`)">
    <h3 class="post-title">{{ post.title }}</h3>
    <p class="post-excerpt">{{ post.excerpt || post.content?.substring(0, 150) + '...' }}</p>
    <div class="post-meta">
      <span class="post-date">{{ formatDate(post.createdAt) }}</span>
      <span v-if="post.tags" class="post-tags">
        <span v-for="tag in post.tags.slice(0, 3)" :key="tag" class="tag">{{ tag }}</span>
      </span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  post: {
    type: Object,
    required: true
  }
})

function formatDate(dateStr) {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: 'long',
    day: 'numeric'
  })
}
</script>

<style scoped>
.post-card {
  background: #fff;
  border-radius: 12px;
  padding: 1.5rem;
  cursor: pointer;
  transition: all 0.3s;
  border: 1px solid #e5e7eb;
}

.post-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.post-title {
  font-size: 1.25rem;
  color: #1a1a1a;
  margin-bottom: 0.5rem;
}

.post-excerpt {
  color: #666;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.post-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.875rem;
  color: #999;
}

.post-tags {
  display: flex;
  gap: 0.5rem;
}

.tag {
  background: #f3f4f6;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.75rem;
  color: #666;
}
</style>