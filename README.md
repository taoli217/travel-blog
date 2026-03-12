# 🌍 Travel Blog - 旅游博客系统

> 前后端分离项目 (Vue3 + Spring Boot + MySQL) | **Version 1.0.0**

## 📋 项目简介

一个功能完善的旅游博客系统，支持文章发布、分类管理、评论互动等功能。

## 🛠 技术栈

### 前端
- **Vue 3** - 渐进式前端框架
- **Vite** - 构建工具
- **Pinia** - 状态管理
- **Vue Router** - 路由管理
- **Axios** - HTTP 请求
- **Element Plus** - UI 组件库
- **Markdown-it** - Markdown 解析

### 后端
- **Java 17** - 编程语言
- **Spring Boot 3.2** - 后端框架
- **MyBatis-Plus** - ORM 框架
- **MySQL 8.0** - 数据库

## 📁 项目结构

```
travel-blog/
├── frontend/          # Vue3 前端项目
│   ├── src/
│   │   ├── views/    # 页面组件
│   │   ├── components/# 公共组件
│   │   ├── api/      # API 接口
│   │   ├── router/   # 路由配置
│   │   └── store/    # 状态管理
│   └── ...
│
├── backend/          # Spring Boot 后端项目
│   ├── src/main/java/com/travelblog/
│   │   ├── controller/  # 控制器
│   │   ├── service/    # 业务逻辑
│   │   ├── mapper/     # 数据访问
│   │   ├── entity/     # 实体类
│   │   ├── dto/        # 数据传输对象
│   │   └── config/     # 配置类
│   └── resources/
│       └── application.yml
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

访问 http://localhost:3000

### 后端启动

```bash
# 1. 创建数据库
mysql -u root -p < docs/database.md

# 2. 配置数据库连接 (修改 backend/src/main/resources/application.yml)

# 3. 启动后端
cd backend
./mvnw spring-boot:run
```

服务端口: http://localhost:8080

## 📦 功能列表

- [x] 用户注册/登录
- [x] 文章列表/详情
- [x] 文章发布/编辑/删除
- [x] 文章分类
- [x] 文章标签
- [x] 评论功能
- [x] 点赞功能
- [x] 用户头像
- [x] Markdown 支持

## 📝 API 接口

详见 [API.md](docs/API.md)

## 📊 数据库设计

详见 [database.md](docs/database.md)

## 👥 团队成员

| 角色 | 职责 |
|------|------|
| PM | 项目管理、需求分析 |
| 前端开发 | Vue3 开发 |
| 后端开发 | Java API 开发 |
| Reviewer | 代码审核 |

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

## 📄 许可证

MIT License

---

**Copyright © 2024 Travel Blog**
