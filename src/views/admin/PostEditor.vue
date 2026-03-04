<template>
  <div class="post-editor">
    <header class="editor-header">
      <h1>{{ isEdit ? '编辑文章' : '新建文章' }}</h1>
      <div class="header-actions">
        <button @click="handleSave(false)" :disabled="saving" class="btn-secondary">
          保存草稿
        </button>
        <button @click="handleSave(true)" :disabled="saving" class="btn-primary">
          {{ saving ? '保存中...' : '发布' }}
        </button>
      </div>
    </header>

    <form class="editor-form">
      <div class="form-group">
        <label>标题</label>
        <input v-model="form.title" type="text" placeholder="请输入文章标题" />
      </div>

      <div class="form-row">
        <div class="form-group">
          <label>标签（逗号分隔）</label>
          <input v-model="tagsInput" type="text" placeholder="Java, Spring, 后端" />
        </div>
      </div>

      <div class="form-group editor-container">
        <label>内容（Markdown）</label>
        <div class="editor-wrapper">
          <textarea
            v-model="form.content"
            placeholder="使用 Markdown 格式编写文章..."
            class="editor-textarea"
          ></textarea>
          <div class="preview" v-html="renderedContent"></div>
        </div>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBlogStore } from '../../stores/blog'
import { marked } from 'marked'

const route = useRoute()
const router = useRouter()
const blogStore = useBlogStore()

const isEdit = computed(() => !!route.params.id)
const saving = ref(false)

const form = ref({
  title: '',
  content: '',
  tags: [],
  published: false
})

const tagsInput = ref('')

const renderedContent = computed(() => {
  if (!form.value.content) return '<p class="placeholder">预览区域</p>'
  return marked(form.value.content)
})

onMounted(async () => {
  if (isEdit.value) {
    await blogStore.fetchPost(route.params.id)
    if (blogStore.currentPost) {
      form.value = { ...blogStore.currentPost }
      tagsInput.value = form.value.tags?.join(', ') || ''
    }
  }
})

async function handleSave(publish) {
  if (!form.value.title.trim()) {
    alert('请输入文章标题')
    return
  }

  form.value.tags = tagsInput.value
    .split(',')
    .map(t => t.trim())
    .filter(t => t)
  
  form.value.published = publish
  saving.value = true

  try {
    if (isEdit.value) {
      await blogStore.editPost(route.params.id, form.value)
    } else {
      await blogStore.addPost(form.value)
    }
    router.push('/admin')
  } catch (e) {
    alert('保存失败：' + e.message)
  } finally {
    saving.value = false
  }
}
</script>

<style scoped>
.post-editor {
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
}

.editor-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.editor-header h1 {
  font-size: 1.5rem;
  color: #1a1a1a;
}

.header-actions {
  display: flex;
  gap: 1rem;
}

.btn-primary,
.btn-secondary {
  padding: 0.75rem 1.5rem;
  border-radius: 8px;
  font-weight: 500;
  cursor: pointer;
  transition: opacity 0.2s;
}

.btn-primary {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
}

.btn-secondary {
  background: #fff;
  color: #374151;
  border: 1px solid #e5e7eb;
}

.btn-primary:hover:not(:disabled),
.btn-secondary:hover:not(:disabled) {
  opacity: 0.9;
}

.btn-primary:disabled,
.btn-secondary:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.editor-form {
  background: #fff;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #374151;
}

.form-group input[type="text"] {
  width: 100%;
  padding: 0.75rem 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-size: 1rem;
}

.form-group input[type="text"]:focus {
  outline: none;
  border-color: #3b82f6;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1.5rem;
}

.editor-container {
  margin-bottom: 0;
}

.editor-wrapper {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
  min-height: 500px;
}

.editor-textarea {
  width: 100%;
  height: 100%;
  min-height: 500px;
  padding: 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  font-family: 'Monaco', 'Menlo', monospace;
  font-size: 0.875rem;
  line-height: 1.6;
  resize: none;
}

.editor-textarea:focus {
  outline: none;
  border-color: #3b82f6;
}

.preview {
  padding: 1rem;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  overflow-y: auto;
  line-height: 1.8;
}

.preview :deep(h1),
.preview :deep(h2),
.preview :deep(h3) {
  margin-top: 1.5rem;
  margin-bottom: 0.75rem;
  color: #1a1a1a;
}

.preview :deep(p) {
  margin-bottom: 1rem;
}

.preview :deep(code) {
  background: #f3f4f6;
  padding: 0.125rem 0.375rem;
  border-radius: 4px;
  font-size: 0.875rem;
}

.preview :deep(pre) {
  background: #1a1a1a;
  color: #e5e7eb;
  padding: 1rem;
  border-radius: 8px;
  overflow-x: auto;
}

.preview :deep(pre code) {
  background: transparent;
  padding: 0;
}

.placeholder {
  color: #9ca3af;
  text-align: center;
  padding: 2rem;
}
</style>