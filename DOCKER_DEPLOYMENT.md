# CRM 系统 Docker 部署指南

本项目提供了完整的 Docker 部署方案，可以实现一键部署后端服务、前端服务、MySQL 数据库和 Redis 缓存。

## 部署架构

- **crm-backend**: Spring Boot 后端服务 (基于 OpenJDK 11)
- **crm-frontend**: Vue 前端服务 (基于 Nginx)
- **mysql**: MySQL 8.0 数据库
- **redis**: Redis 7.0 缓存

## 部署步骤

### 1. 环境准备

确保您的服务器上已安装 Docker 和 Docker Compose。

```bash
# 安装 Docker (以 Ubuntu 为例)
sudo apt update
sudo apt install docker.io
sudo systemctl start docker
sudo systemctl enable docker

# 安装 Docker Compose (v2.x)
sudo apt install docker-compose-plugin
# 或者使用 curl 安装 v1.x
# sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
# sudo chmod +x /usr/local/bin/docker-compose
```

### 2. 获取项目代码

如果您已将项目克隆到本地，请跳过此步骤。

```bash
# 克隆项目 (假设您已配置好 gh cli)
gh repo clone JK-Fong/JKCRM
cd JKCRM
```

### 3. 构建项目 (已完成)

在执行 Docker 部署前，需要确保后端 Jar 包和前端静态文件已构建完成。

```bash
# 进入项目根目录
cd /path/to/JKCRM

# 后端构建 (已完成)
# mvn clean package -DskipTests

# 前端构建 (已完成)
# cd frontend
# npm install
# npm run build
# cd ..
```

### 4. 部署配置

#### 4.1. 数据库初始化

`docker-compose.yml` 文件已配置自动执行 `src/main/resources/db_init.sql` 脚本进行数据库初始化。

#### 4.2. 配置文件修改

请根据您的实际情况修改 `docker-compose.yml` 中的敏感信息：

- **MySQL 密码**: `MYSQL_ROOT_PASSWORD` 和 `MYSQL_PASSWORD`
- **Redis 密码**: `redis-server --requirepass` 后的密码

### 5. 启动服务

在项目根目录（`docker-compose.yml` 所在目录）执行以下命令：

```bash
# 后台启动所有服务
docker compose up -d --build
```

- `--build`：强制重新构建镜像，确保使用最新的代码。
- `-d`：后台运行。

### 6. 验证部署

服务启动后，您可以通过以下地址访问：

- **前端系统**: `http://localhost:80` (或 `http://您的服务器IP`)
- **后端 API**: `http://localhost:8080/api/member/page`
- **API 文档**: `http://localhost:80/api/doc.html` (通过 Nginx 代理)

### 7. 停止和清理

```bash
# 停止所有服务
docker compose stop

# 停止并删除容器、网络、卷
docker compose down -v
```

## Dockerfile 说明

### 1. 后端 Dockerfile (`Dockerfile.backend`)

```dockerfile
# 使用 OpenJDK 11 作为基础镜像
FROM openjdk:11-jdk-slim

# 设置工作目录
WORKDIR /app

# 将构建好的 jar 包复制到容器中
COPY target/crm-system-1.0.0.jar app.jar

# 暴露后端服务端口
EXPOSE 8080

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. 前端 Dockerfile (`Dockerfile.frontend`)

```dockerfile
# 使用官方 Nginx 镜像作为基础镜像
FROM nginx:stable-alpine

# 将前端构建产物复制到 Nginx 默认的静态文件目录
COPY frontend/dist /usr/share/nginx/html

# 复制自定义的 Nginx 配置文件
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露 Nginx 默认端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]
```

## docker-compose.yml 说明

`docker-compose.yml` 文件定义了四个服务：`mysql`、`redis`、`crm-backend` 和 `crm-frontend`。

- **网络**: 所有服务都在 Docker 内部网络中，可以通过服务名互相访问（例如 `crm-backend` 通过 `mysql:3306` 访问数据库）。
- **持久化**: MySQL 和 Redis 的数据通过 `volumes` 挂载到本地目录，确保数据不会丢失。
- **健康检查**: MySQL 服务配置了 `healthcheck`，确保后端服务在数据库完全启动并健康后才启动。
- **Nginx 代理**: `crm-frontend` 服务中的 Nginx 配置了 `/api/` 路径的反向代理，将 API 请求转发给 `crm-backend:8080`。

## 常见问题

### 1. 数据库连接失败

- **检查**: 确保 `docker-compose.yml` 中 `crm-backend` 的数据库连接信息与 `mysql` 服务中的环境变量一致。
- **注意**: 后端连接地址必须是 `jdbc:mysql://mysql:3306/...`，使用服务名 `mysql` 而不是 `localhost`。

### 2. 无法访问前端页面

- **检查**: 确保宿主机的 80 端口没有被占用。
- **检查**: 确保 `crm-frontend` 容器已启动。

### 3. API 接口 404

- **检查**: 确保 `crm-backend` 容器已启动。
- **检查**: 确保 `nginx.conf` 中的 `proxy_pass` 地址正确（`http://crm-backend:8080/api/`）。

## 总结

使用此 Docker 部署方案，您可以轻松地在任何支持 Docker 的环境中部署和运行 CRM 系统。
