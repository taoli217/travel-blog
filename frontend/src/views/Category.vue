<template>
  <div class="category-page">
    <header class="header">
      <div class="container">
        <div class="logo">
          <router-link to="/">🌍 Travel Blog</router-link>
        </div>
        <nav class="nav">
          <router-link to="/">首页</router-link>
        </nav>
      </div>
    </header>

    <main class="main">
      <div class="container">
        <h1>{{ categoryName }} - 文章列表</h1>
        <div class="article-list">
          <div class="article-item" v-for="article in articles" :key="article.id">
            <img :src="article.coverImage || '/placeholder.jpg'" :alt="article.title" />
            <div class="article-info">
              <h3>
                <router-link :to="`/article/${article.id}`">{{ article.title }}</router-link>
              </h3>
              <p class="summary">{{ article.summary }}</p>
              <div class="meta">
                <span class="author">{{ article.author?.nickname }}</span>
                <span class="date">{{ article.createdAt }}</span>
                <span class="views">👁 {{ article.viewCount }}</span>
              </div>
            </div>
          </div>
          <el-empty v-if="articles.length === 0" description="暂无文章" />
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { articleAPI, categoryAPI } from '@/api'

const route = useRoute()
const articles = ref([])
const categoryName = ref('')

const loadArticles = async () => {
  try {
    const res = await articleAPI.list({ 
      page: 1, 
      size: 20, 
      categoryId: route.params.id 
    })
    articles.value = res.records || []
    
    const cats = await categoryAPI.list()
    const cat = (cats || []).find(c => c.id == route.params.id)
    categoryName.value = cat?.name || '分类'
  } catch (e) {
    console.error(e)
  }
}

onMounted(loadArticles)
watch(() => route.params.id, loadArticles)
</script>

<style scoped>
.category-page { min-height: 100vh; background: #f5f5f5; }
.header { background: #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 20px 0; }
.container { max-width: 1200px; margin: 0 auto; padding: 0 20px; }
.logo a { font-size: 24px; font-weight: bold; color: #333; text-decoration: none; }
.main { padding: 30px 0; }
.article-list { display: flex; flex-direction: column; gap: 20px; }
.article-item { display: flex; background: #fff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }
.article-item img { width: 250px; height: 180px; object-fit: cover; }
.article-info { flex: 1; padding: 20px; }
.article-info h3 { margin-bottom: 10px; }
.article-info h3 a { color: #333; text-decoration: none; }
.summary { color: #666; margin-bottom: 15px; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.meta { color: #999; font-size: 14px; }
.meta span { margin-right: 15px; }
</style>
