<template>
  <div class="write-page">
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
        <div class="write-box">
          <h2>写文章</h2>
          <el-form :model="form" :rules="rules" ref="formRef">
            <el-form-item prop="title">
              <el-input v-model="form.title" placeholder="文章标题" size="large" />
            </el-form-item>
            <el-form-item prop="coverImage">
              <el-input v-model="form.coverImage" placeholder="封面图URL" size="large" />
            </el-form-item>
            <el-form-item prop="categoryId">
              <el-select v-model="form.categoryId" placeholder="选择分类" style="width: 100%">
                <el-option v-for="cat in categories" :key="cat.id" :label="cat.name" :value="cat.id" />
              </el-select>
            </el-form-item>
            <el-form-item prop="content">
              <el-input v-model="form.content" type="textarea" placeholder="文章内容 (支持Markdown)" :rows="20" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="handlePublish" :loading="loading">发布</el-button>
              <el-button @click="handleDraft">存草稿</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { articleAPI, categoryAPI } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const categories = ref([])
const form = ref({
  title: '',
  content: '',
  coverImage: '',
  categoryId: null,
  status: 1
})

const rules = {
  title: [{ required: true, message: '请输入文章标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入文章内容', trigger: 'blur' }]
}

onMounted(async () => {
  try {
    const res = await categoryAPI.list()
    categories.value = res || []
  } catch (e) {
    console.error(e)
  }
})

const handlePublish = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    form.value.status = 1
    await articleAPI.create(form.value)
    ElMessage.success('发布成功')
    router.push('/')
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

const handleDraft = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    form.value.status = 0
    await articleAPI.create(form.value)
    ElMessage.success('草稿保存成功')
    router.push('/')
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.write-page { min-height: 100vh; background: #f5f5f5; }
.header { background: #fff; box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 20px 0; }
.container { max-width: 900px; margin: 0 auto; padding: 0 20px; }
.logo a { font-size: 24px; font-weight: bold; color: #333; text-decoration: none; }
.main { padding: 30px 0; }
.write-box { background: #fff; padding: 30px; border-radius: 8px; }
.write-box h2 { margin-bottom: 20px; }
</style>
