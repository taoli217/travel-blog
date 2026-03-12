<template>
  <div class="article-page">
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
        <article class="article-content" v-if="article">
          <h1>{{ article.title }}</h1>
          <div class="meta">
            <span class="author">{{ article.author?.nickname }}</span>
            <span class="date">{{ article.createdAt }}</span>
            <span class="views">👁 {{ article.viewCount }}</span>
          </div>
          <img :src="article.coverImage" :alt="article.title" class="cover" />
          <div class="content" v-html="renderedContent"></div>
          
          <div class="actions">
            <el-button :type="article.isLiked ? 'primary' : 'default'" @click="handleLike">
              ❤️ 点赞 ({{ article.likeCount }})
            </el-button>
          </div>
        </article>

        <div class="comments">
          <h3>评论 ({{ comments.length }})</h3>
          <div class="comment-form" v-if="token">
            <el-input v-model="commentContent" type="textarea" placeholder="写下你的评论..." :rows="3" />
            <el-button type="primary" @click="submitComment">提交评论</el-button>
          </div>
          <div class="comment-list">
            <div class="comment-item" v-for="comment in comments" :key="comment.id">
              <div class="comment-content">{{ comment.content }}</div>
              <div class="comment-meta">
                <span>{{ comment.user?.nickname }}</span>
                <span>{{ comment.createdAt }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { articleAPI, commentAPI, likeAPI } from '@/api'
import MarkdownIt from 'markdown-it'

const route = useRoute()
const token = localStorage.getItem('token')
const article = ref(null)
const comments = ref([])
const commentContent = ref('')
const md = new MarkdownIt()

const renderedContent = computed(() => {
  return article.value ? md.render(article.value.content) : ''
})

onMounted(async () => {
  try {
    article.value = await articleAPI.get(route.params.id)
    const res = await commentAPI.list(route.params.id)
    comments.value = res.records || []
  } catch (e) {
    console.error(e)
  }
})

const handleLike = async () => {
  try {
    const res = await likeAPI.toggle(route.params.id)
    article.value.likeCount = res.likeCount
    article.value.isLiked = res.isLiked
  } catch (e) {
    console.error(e)
  }
}

const submitComment = async () => {
  if (!commentContent.value.trim()) return
  try {
    await commentAPI.create(route.params.id, { content: commentContent.value })
    commentContent.value = ''
    const res = await commentAPI.list(route.params.id)
    comments.value = res.records || []
  } catch (e) {
    console.error(e)
  }
}
</script>

<style scoped>
.article-page { min-height: 100vh; background: #f5f5f5; }
.header { background: #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 20px 0; }
.container { max-width: 900px; margin: 0 auto; padding: 0 20px; }
.logo a { font-size: 24px; font-weight: bold; color: #333; text-decoration: none; }
.main { padding: 30px 0; }
.article-content { background: #fff; padding: 40px; border-radius: 8px; }
.article-content h1 { margin-bottom: 20px; }
.meta { color: #999; margin-bottom: 20px; }
.meta span { margin-right: 15px; }
.cover { width: 100%; max-height: 400px; object-fit: cover; border-radius: 8px; margin-bottom: 20px; }
.content { line-height: 1.8; font-size: 16px; }
.actions { margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; }
.comments { margin-top: 30px; background: #fff; padding: 30px; border-radius: 8px; }
.comment-form { margin-bottom: 20px; }
.comment-form .el-button { margin-top: 10px; }
.comment-item { padding: 15px 0; border-bottom: 1px solid #eee; }
.comment-content { margin-bottom: 8px; }
.comment-meta { color: #999; font-size: 14px; }
</style>
