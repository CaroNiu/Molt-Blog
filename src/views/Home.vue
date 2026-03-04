<template>
  <div class="home">
    <section class="hero">
      <h1>🐂 欢迎来到 Molt Blog</h1>
      <p>记录学习、分享成长</p>
    </section>

    <section class="posts-section">
      <h2>最新文章</h2>
      
      <div v-if="blogStore.loading" class="loading">加载中...</div>
      
      <div v-else-if="blogStore.posts.length === 0" class="empty">
        暂无文章
      </div>
      
      <div v-else class="posts-grid">
        <PostCard
          v-for="post in blogStore.posts"
          :key="post.id"
          :post="post"
        />
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted } from 'vue'
import { useBlogStore } from '../stores/blog'
import PostCard from '../components/PostCard.vue'

const blogStore = useBlogStore()

onMounted(() => {
  blogStore.fetchPosts()
})
</script>

<style scoped>
.home {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
}

.hero {
  text-align: center;
  padding: 4rem 0;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 16px;
  color: white;
  margin-bottom: 3rem;
}

.hero h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.hero p {
  font-size: 1.25rem;
  opacity: 0.9;
}

.posts-section h2 {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
  color: #1a1a1a;
}

.posts-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 1.5rem;
}

.loading,
.empty {
  text-align: center;
  padding: 3rem;
  color: #666;
}
</style>