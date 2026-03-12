# 🌍 Travel Blog - 旅游博客系统

> 前后端分离项目 (Vue3 + Spring Boot + MySQL)

## 📋 项目简介

一个功能完善的旅游博客系统，支持文章发布、分类管理、评论互动等功能。

## 🛠 技术栈

### 前端
- **Vue 3** - 渐进式前端框架
- **Vite** - 构建工具
- **Pinia** - 状态管理
- **Vue Router** - 路由管理
- **Axios** - HTTP 请求

### 后端
- **Java** - 编程语言
- **Spring Boot** - 后端框架
- **MyBatis-Plus** - ORM 框架
- **MySQL** - 数据库

## 📁 项目结构

```
travel-blog/
├── frontend/          # Vue3 前端项目
│   ├── src/
│   │   ├── views/    # 页面组件
│   │   ├── components/# 公共组件
│   │   ├── api/      # API 接口
│   │   ├── router/   # 路由配置
│   │   ├── store/    # 状态管理
│   │   └── assets/   # 静态资源
│   └── ...
│
├── backend/          # Spring Boot 后端项目
│   ├── src/main/java/
│   │   ├── controller/  # 控制器
│   │   ├── service/    # 业务逻辑
│   │   ├── mapper/     # 数据访问
│   │   ├── entity/     # 实体类
│   │   ├── dto/        # 数据传输对象
│   │   └── config/     # 配置类
│   └── resources/
│       └── mapper/     # MyBatis 映射文件
│
└── docs/             # 项目文档
    ├── API.md         # 接口文档
    ├── database.md    # 数据库设计
    └── deploy.md      # 部署文档
```

## 🚀 快速开始

### 前端启动

```bash
cd frontend
npm install
npm run dev
```

### 后端启动

```bash
cd backend
# 配置数据库连接
./mvnw spring-boot:run
```

## 👥 团队成员

| 角色 | 姓名 | 职责 |
|------|------|------|
| PM | - | 项目管理、需求分析 |
| 前端开发 | - | Vue3 开发 |
| 后端开发 | - | Java API 开发 |
| Reviewer | - | 代码审核 |

## 🔀 Git 工作流

```
main (生产分支)
  ↑
develop (开发主分支)
  ↑
  ├── feature/xxx (新功能分支)
  ├── bugfix/xxx (修复分支)
  └── hotfix/xxx (紧急修复分支)
```

### 分支命名规范
- 功能分支: `feature/<issue-id>-<功能名>`
- 修复分支: `bugfix/<issue-id>-<问题描述>`
- 热修复: `hotfix/<issue-id>-<问题描述>`

### PR 流程
1. 从 `develop` 创建分支
2. 开发完成后提交 PR
3. 至少一人 Review 通过
4. 合并到 `develop`

## 📄 许可证

MIT License
