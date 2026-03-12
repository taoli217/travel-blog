<template>
  <div class="home">
    <!-- 头部导航 -->
    <header class="header">
      <div class="container">
        <div class="logo">
          <router-link to="/">🌍 Travel Blog</router-link>
        </div>
        <nav class="nav">
          <router-link to="/">首页</router-link>
          <router-link to="/category/1">日本</router-link>
          <router-link to="/category/2">泰国</router-link>
          <router-link to="/category/3">欧洲</router-link>
          <template v-if="!token">
            <router-link to="/login">登录</router-link>
            <router-link to="/register">注册</router-link>
          </template>
          <template v-else>
            <router-link to="/write">写文章</router-link>
            <a href="javascript:;" @click="logout">退出</a>
          </template>
        </nav>
      </div>
    </header>

    <!-- 主要内容 -->
    <main class="main">
      <div class="container">
        <!-- 轮播图 -->
        <div class="banner">
          <el-carousel height="400px">
            <el-carousel-item v-for="i in 3" :key="i">
              <div class="banner-item">
                <h2>探索世界的美好 {{ i }}</h2>
                <p>分享你的旅行故事</p>
              </div>
            </el-carousel-item>
          </el-carousel>
        </div>

        <!-- 文章列表 -->
        <div class="content">
          <div class="sidebar">
            <div class="categories">
              <h3>📁 分类</h3>
              <ul>
                <li v-for="cat in categories" :key="cat.id">
                  <router-link :to="`/category/${cat.id}`">
                    {{ cat.name }} ({{ cat.articleCount }})
                  </router-link>
                </li>
              </ul>
            </div>
          </div>
          
          <div class="articles">
            <h2>最新文章</h2>
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
                    <span class="likes">❤️ {{ article.likeCount }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>

    <!-- 底部 -->
    <footer class="footer">
      <p>© 2024 Travel Blog. All rights reserved.</p>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { articleAPI, categoryAPI } from '@/api'

const router = useRouter()
const token = localStorage.getItem('token')
const articles = ref([])
const categories = ref([
  { id: 1, name: '日本', articleCount: 10 },
  { id: 2, name: '泰国', articleCount: 5 },
  { id: 3, name: '欧洲', articleCount: 8 }
])

onMounted(async () => {
  try {
    const res = await articleAPI.list({ page: 1, size: 10 })
    articles.value = res.records || []
  } catch (e) {
    console.error(e)
  }
})

const logout = () => {
  localStorage.removeItem('token')
  router.push('/login')
}
</script>

<style scoped>
.home {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.header {
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  padding: 20px 0;
}

.header .container {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.logo a {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-decoration: none;
}

.nav a {
  margin-left: 20px;
  color: #666;
  text-decoration: none;
}

.nav a:hover {
  color: #409eff;
}

.main {
  flex: 1;
  padding: 30px 0;
}

.banner {
  margin-bottom: 30px;
}

.banner-item {
  height: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: #fff;
}

.banner-item h2 {
  font-size: 36px;
  margin-bottom: 10px;
}

.content {
  display: flex;
  gap: 30px;
}

.sidebar {
  width: 250px;
}

.categories h3 {
  margin-bottom: 15px;
}

.categories ul {
  list-style: none;
}

.categories li {
  padding: 10px 0;
  border-bottom: 1px solid #eee;
}

.categories a {
  color: #666;
  text-decoration: none;
}

.articles {
  flex: 1;
}

.articles h2 {
  margin-bottom: 20px;
}

.article-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.article-item {
  display: flex;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
}

.article-item img {
  width: 250px;
  height: 180px;
  object-fit: cover;
}

.article-info {
  flex: 1;
  padding: 20px;
}

.article-info h3 {
  margin-bottom: 10px;
}

.article-info h3 a {
  color: #333;
  text-decoration: none;
}

.summary {
  color: #666;
  margin-bottom: 15px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.meta {
  color: #999;
  font-size: 14px;
}

.meta span {
  margin-right: 15px;
}

.footer {
  background: #333;
  color: #fff;
  text-align: center;
  padding: 20px 0;
  margin-top: 50px;
}
</style>
