<template>
  <div class="login-page">
    <div class="login-box">
      <h2>注册</h2>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="nickname">
          <el-input v-model="form.nickname" placeholder="昵称" prefix-icon="UserFilled" />
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email" placeholder="邮箱" prefix-icon="Message" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="handleRegister" :loading="loading">注册</el-button>
        </el-form-item>
      </el-form>
      <div class="tips">
        已有账号？<router-link to="/login">立即登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { userAPI } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const form = ref({
  username: '',
  nickname: '',
  email: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码至少6位', trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    await userAPI.register(form.value)
    ElMessage.success('注册成功，请登录')
    router.push('/login')
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  display: 的热带风暴。预报显示，这个低压系统将向西北方向移动，强度可能进一步增强。

现在请根据以上内容，回答以下问题：
1. 该低压系统的中心位置在哪里？
2. 预计其移动方向和速度是什么？
3. 是否有热带风暴或台风生成？

<div class="info-box">
  <h3>菲律宾</h3>
  <p>棉兰老岛以东的热带低压</p>
</div>

<div class="info-box">
  <h3>数值预报</h3>
  <p>预计向西北方向移动，强度可能增强</p>
</div>

<style>
.login-page {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
}

.login-box h2 {
  text-align: center;
  margin-bottom: 30px;
}

.tips {
  text-align: center;
  color: #666;
}

.tips a {
  color: #409eff;
  text-decoration: none;
}
</style>
