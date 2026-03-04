# Molt Blog

个人博客系统 - Vue 3 + Spring Boot 后端

## 技术栈

### 前端
- Vue 3 + Vite
- Vue Router 4
- Pinia (状态管理)
- Axios (HTTP 请求)
- Marked (Markdown 解析)

### 后端
- Java 17 + Spring Boot 3.2
- Spring Security + JWT
- H2 内存数据库
- JPA / Hibernate

## 快速开始

### 前端
```bash
cd frontend
npm install
npm run dev
```

### 后端
```bash
cd backend
mvn spring-boot:run
```

## 默认管理员账号

- **用户名：** `admin`
- **密码：** `admin123`

## 数据库

使用 H2 内存数据库，启动后可访问：
- **H2 Console：** http://localhost:8080/h2-console
- **JDBC URL：** `jdbc:h2:file:./data/blog`
- **用户名：** `sa`
- **密码：** 空

## 开发端口

- 前端：http://localhost:5173 (Vite) 或 http://localhost:3000
- 后端：http://localhost:8080
- H2 Console：http://localhost:8080/h2-console

## 后端 API 接口

### 认证
- `POST /api/auth/login` - 登录
- `POST /api/auth/logout` - 登出
- `GET /api/auth/me` - 获取当前用户

### 博客
- `GET /api/posts` - 获取文章列表
- `GET /api/posts/:id` - 获取文章详情
- `POST /api/posts` - 创建文章 (需认证)
- `PUT /api/posts/:id` - 更新文章 (需认证)
- `DELETE /api/posts/:id` - 删除文章 (需认证)

## 数据结构

### 用户
```json
{
  "id": 1,
  "username": "admin",
  "role": "admin"
}
```

### 文章
```json
{
  "id": 1,
  "title": "文章标题",
  "content": "Markdown 内容",
  "excerpt": "摘要",
  "tags": ["Java", "Spring"],
  "published": true,
  "author": "Caro",
  "createdAt": "2024-01-01T00:00:00Z",
  "updatedAt": "2024-01-01T00:00:00Z"
}
```

## 目录结构

```
src/
├── api/            # API 请求
├── components/     # 公共组件
├── router/         # 路由配置
├── stores/         # Pinia 状态管理
├── styles/         # 全局样式
└── views/          # 页面组件
    └── admin/      # 管理后台页面
```