# 📊 数据库设计文档

## 数据库名: `travel_blog`

---

## 表结构

### 1. 用户表 (user)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 用户ID |
| username | VARCHAR(50) | UNIQUE, NOT NULL | 用户名 |
| password | VARCHAR(255) | NOT NULL | 密码(加密存储) |
| nickname | VARCHAR(50) | NOT NULL | 昵称 |
| email | VARCHAR(100) | UNIQUE | 邮箱 |
| avatar | VARCHAR(500) | DEFAULT NULL | 头像URL |
| bio | VARCHAR(500) | DEFAULT NULL | 个人简介 |
| role | TINYINT | DEFAULT 0 | 角色: 0-普通用户, 1-管理员 |
| status | TINYINT | DEFAULT 1 | 状态: 0-禁用, 1-正常 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE | 更新时间 |

---

### 2. 文章表 (article)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 文章ID |
| title | VARCHAR(200) | NOT NULL | 标题 |
| content | LONGTEXT | NOT NULL | 正文内容(Markdown) |
| summary | VARCHAR(500) | DEFAULT NULL | 文章摘要 |
| cover_image | VARCHAR(500) | DEFAULT NULL | 封面图URL |
| view_count | INT | DEFAULT 0 | 浏览量 |
| like_count | INT | DEFAULT 0 | 点赞数 |
| comment_count | INT | DEFAULT 0 | 评论数 |
| user_id | BIGINT | FOREIGN KEY | 作者ID |
| category_id | BIGINT | FOREIGN KEY | 分类ID |
| status | TINYINT | DEFAULT 1 | 状态: 0-草稿, 1-已发布 |
| is_featured | TINYINT | DEFAULT 0 | 是否推荐: 0-否, 1-是 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE | 更新时间 |

---

### 3. 分类表 (category)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 分类ID |
| name | VARCHAR(50) | UNIQUE, NOT NULL | 分类名称 |
| description | VARCHAR(200) | DEFAULT NULL | 分类描述 |
| sort | INT | DEFAULT 0 | 排序权重 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE | 更新时间 |

---

### 4. 标签表 (tag)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 标签ID |
| name | VARCHAR(50) | UNIQUE, NOT NULL | 标签名称 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |

---

### 5. 文章标签关联表 (article_tag)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| article_id | BIGINT | FOREIGN KEY | 文章ID |
| tag_id | BIGINT | FOREIGN KEY | 标签ID |
| PRIMARY KEY | (article_id, tag_id) | | 复合主键 |

---

### 6. 评论表 (comment)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 评论ID |
| content | VARCHAR(1000) | NOT NULL | 评论内容 |
| user_id | BIGINT | FOREIGN KEY | 评论用户ID |
| article_id | BIGINT | FOREIGN KEY | 文章ID |
| parent_id | BIGINT | DEFAULT NULL | 父评论ID(用于回复) |
| reply_to_id | BIGINT | DEFAULT NULL | 回复目标用户ID |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| updated_at | DATETIME | DEFAULT CURRENT_TIMESTAMP ON UPDATE | 更新时间 |

---

### 7. 点赞表 (like)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | 点赞ID |
| user_id | BIGINT | FOREIGN KEY | 点赞用户ID |
| article_id | BIGINT | FOREIGN KEY | 文章ID |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |
| UNIQUE KEY | (user_id, article_id) | | 同一用户只能点赞一次 |

---

### 8. JWT令牌表 (jwt_token)

| 字段名 | 类型 | 约束 | 说明 |
|--------|------|------|------|
| id | BIGINT | PRIMARY KEY, AUTO_INCREMENT | ID |
| user_id | BIGINT | FOREIGN KEY, UNIQUE | 用户ID |
| token | TEXT | NOT NULL | JWT令牌 |
| expired_at | DATETIME | NOT NULL | 过期时间 |
| created_at | DATETIME | DEFAULT CURRENT_TIMESTAMP | 创建时间 |

---

## ER 关系图

```
┌─────────────┐       ┌─────────────┐
│    user     │       │   article   │
├─────────────┤       ├─────────────┤
│ id (PK)     │◄──────│ user_id (FK)│
│ username    │       │ id (PK)     │
│ password    │       │ title       │
│ nickname    │       │ content     │
│ email       │       │ category_id │
│ avatar      │       │ ...         │
│ ...         │       └──────┬──────┘
└─────────────┘              │
                            │
       ┌────────────────────┼────────────────────┐
       │                    │                    │
       ▼                    ▼                    ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│  category   │    │   comment   │    │    like     │
├─────────────┤    ├─────────────┤    ├─────────────┤
│ id (PK)     │    │ id (PK)     │    │ id (PK)     │
│ name        │    │ article_id  │    │ user_id     │
│ description │    │ user_id     │    │ article_id  │
│ ...         │    │ parent_id   │    │ ...         │
└─────────────┘    └──────┬──────┘    └─────────────┘
                          │
                          ▼
                 ┌─────────────┐
                 │     tag     │
                 ├─────────────┤
                 │ id (PK)     │
                 │ name        │
                 └──────┬──────┘
                        │
                        ▼
                 ┌─────────────┐
                 │ article_tag │
                 ├─────────────┤
                 │ article_id  │
                 │ tag_id      │
                 └─────────────┘
```

---

## 索引设计

| 表名 | 索引名 | 字段 | 类型 |
|------|--------|------|------|
| user | idx_username | username | UNIQUE |
| user | idx_email | email | UNIQUE |
| article | idx_category_id | category_id | INDEX |
| article | idx_user_id | user_id | INDEX |
| article | idx_created_at | created_at | INDEX |
| article | idx_status | status | INDEX |
| comment | idx_article_id | article_id | INDEX |
| comment | idx_parent_id | parent_id | INDEX |
| like | idx_user_article | user_id, article_id | UNIQUE |
| article_tag | idx_article_id | article_id | INDEX |
| article_tag | idx_tag_id | tag_id | INDEX |

---

## 初始化SQL

```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS travel_blog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE travel_blog;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像',
  `bio` VARCHAR(500) DEFAULT NULL COMMENT '个人简介',
  `role` TINYINT DEFAULT 0 COMMENT '角色: 0-普通用户, 1-管理员',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 分类表
CREATE TABLE IF NOT EXISTS `category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `description` VARCHAR(200) DEFAULT NULL COMMENT '分类描述',
  `sort` INT DEFAULT 0 COMMENT '排序',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分类表';

-- 标签表
CREATE TABLE IF NOT EXISTS `tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '标签名称',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- 文章表
CREATE TABLE IF NOT EXISTS `article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL COMMENT '标题',
  `content` LONGTEXT NOT NULL COMMENT '正文',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT '摘要',
  `cover_image` VARCHAR(500) DEFAULT NULL COMMENT '封面图',
  `view_count` INT DEFAULT 0 COMMENT '浏览量',
  `like_count` INT DEFAULT 0 COMMENT '点赞数',
  `comment_count` INT DEFAULT 0 COMMENT '评论数',
  `user_id` BIGINT NOT NULL COMMENT '作者ID',
  `category_id` BIGINT DEFAULT NULL COMMENT '分类ID',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0-草稿, 1-已发布',
  `is_featured` TINYINT DEFAULT 0 COMMENT '是否推荐',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_created_at` (`created_at`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 文章标签关联表
CREATE TABLE IF NOT EXISTS `article_tag` (
  `article_id` BIGINT NOT NULL,
  `tag_id` BIGINT NOT NULL,
  PRIMARY KEY (`article_id`, `tag_id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签关联表';

-- 评论表
CREATE TABLE IF NOT EXISTS `comment` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(1000) NOT NULL COMMENT '评论内容',
  `user_id` BIGINT NOT NULL COMMENT '评论用户',
  `article_id` BIGINT NOT NULL COMMENT '文章ID',
  `parent_id` BIGINT DEFAULT NULL COMMENT '父评论ID',
  `reply_to_id` BIGINT DEFAULT NULL COMMENT '回复目标用户ID',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_article_id` (`article_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 点赞表
CREATE TABLE IF NOT EXISTS `like` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `article_id` BIGINT NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_article` (`user_id`, `article_id`),
  KEY `idx_article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='点赞表';

-- JWT令牌表
CREATE TABLE IF NOT EXISTS `jwt_token` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `token` TEXT NOT NULL,
  `expired_at` DATETIME NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='JWT令牌表';
```
