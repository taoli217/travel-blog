# Travel Blog API Documentation

###

## 接口文档 基础信息
- **基础URL**: `/api/v1`
- **认证方式**: JWT Token
- **返回格式**: JSON

---

## 用户模块

### 1. 用户注册
- **POST** `/users/register`
- **请求体**:
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "nickname": "string"
}
```
- **响应**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "userId": 1,
    "username": "test",
    "nickname": "测试用户"
  }
}
```

### 2. 用户登录
- **POST** `/users/login`
- **请求体**:
```json
{
  "username": "string",
  "password": "string"
}
```
- **响应**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "userId": 1,
    "username": "test",
    "nickname": "测试用户",
    "avatar": "https://..."
  }
}
```

### 3. 获取用户信息
- **GET** `/users/{id}`
- **响应**:
```json
{
  "code": 200,
  "data": {
    "id": 1,
    "username": "test",
    "nickname": "测试用户",
    "avatar": "https://...",
    "bio": "个人简介",
    "createdAt": "2024-01-01T00:00:00Z"
  }
}
```

---

## 文章模块

### 1. 文章列表
- **GET** `/articles`
- **查询参数**:
  - `page`: 页码 (默认 1)
`: 每页数量  - `size (默认 10)
  - `categoryId`: 分类ID (可选)
  - `tagId`: 标签ID (可选)
  - `keyword`: 搜索关键词 (可选)
- **响应**:
```json
{
  "code": 200,
  "data": {
    "records": [
      {
        "id": 1,
        "title": "日本东京之旅",
        "summary": "东京五日游...",
        "coverImage": "https://...",
        "category": "日本",
        "tags": ["东京", "美食"],
        "author": {
          "id": 1,
          "nickname": "测试用户",
          "avatar": "https://..."
        },
        "viewCount": 100,
        "likeCount": 10,
        "commentCount": 5,
        "createdAt": "2024-01-01T00:00:00Z"
      }
    ],
    "total": 100,
    "page": 1,
    "size": 10
  }
}
```

### 2. 文章详情
- **GET** `/articles/{id}`
- **响应**:
```json
{
  "code": 200,
  "data": {
    "id": 1,
    "title": "日本东京之旅",
    "content": "## 正文内容...",
    "coverImage": "https://...",
    "category": {
      "id": 1,
      "name": "日本"
    },
    "tags": [
      {"id": 1, "name": "东京"},
      {"id": 2, "name": "美食"}
    ],
    "author": {
      "id": 1,
      "nickname": "测试用户",
      "avatar": "https://..."
    },
    "viewCount": 100,
    "likeCount": 10,
    "isLiked": false,
    "createdAt": "2024-01-01T00:00:00Z",
    "updatedAt": "2024-01-01T00:00:00Z"
  }
}
```

### 3. 创建文章 (需认证)
- **POST** `/articles`
- **请求头**: `Authorization: Bearer <token>`
- **请求体**:
```json
{
  "title": "日本东京之旅",
  "content": "## 正文内容...",
  "coverImage": "https://...",
  "categoryId": 1,
  "tagIds": [1, 2]
}
```
- **响应**:
```json
{
  "code": 200,
  "message": "发布成功",
  "data": {
    "id": 1
  }
}
```

### 4. 更新文章 (需认证，作者本人)
- **PUT** `/articles/{id}`
- **请求体**: 同创建

### 5. 删除文章 (需认证，作者本人)
- **DELETE** `/articles/{id}`
- **响应**:
```json
{
  "code": 200,
  "message": "删除成功"
}
```

---

## 分类模块

### 1. 分类列表
- **GET** `/categories`
- **响应**:
```json
{
  "code": 200,
  "data": [
    {"id": 1, "name": "日本", "articleCount": 10},
    {"id": 2, "name": "泰国", "articleCount": 5}
  ]
}
```

### 2. 创建分类 (需认证)
- **POST** `/categories`
- **请求体**: `{"name": "日本", "description": "日本旅游相关文章"}`

---

## 标签模块

### 1. 标签列表
- **GET** `/tags`
- **响应**:
```json
{
  "code": 200,
  "data": [
    {"id": 1, "name": "东京", "articleCount": 10},
    {"id": 2, "name": "美食", "articleCount": 8}
  ]
}
```

---

## 评论模块

### 1. 文章评论列表
- **GET** `/articles/{id}/comments`
- **查询参数**: `page`, `size`
- **响应**:
```json
{
  "code": 200,
  "data": {
    "records": [
      {
        "id": 1,
        "content": "写得真好！",
        "user": {
          "id": 1,
          "nickname": "游客",
          "avatar": "https://..."
        },
        "replies": [
          {
            "id": 2,
            "content": "谢谢支持",
            "user": {...},
            "createdAt": "..."
          }
        ],
        "createdAt": "2024-01-01T00:00:00Z"
      }
    ]
  }
}
```

### 2. 添加评论 (需认证)
- **POST** `/articles/{id}/comments`
- **请求体**: `{"content": "写得真好！", "parentId": null}`

---

## 点赞模块

### 1. 点赞/取消点赞
- **POST** `/articles/{id}/like`
- **请求头**: `Authorization: Bearer <token>`
- **响应**:
```json
{
  "code": 200,
  "message": "点赞成功",
  "data": {
    "isLiked": true,
    "likeCount": 11
  }
}
```

---

## 错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未登录或Token过期 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |
