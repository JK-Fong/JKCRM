# CRM 系统部署指南

## 环境准备

### 1. 服务器要求

- **操作系统**: Linux (Ubuntu 20.04+ / CentOS 7+) 或 Windows Server
- **CPU**: 2 核心以上
- **内存**: 4GB 以上
- **硬盘**: 50GB 以上

### 2. 软件环境

#### Java 环境

```bash
# 安装 OpenJDK 17
sudo apt update
sudo apt install openjdk-17-jdk

# 验证安装
java -version
```

#### MySQL 数据库

```bash
# 安装 MySQL 8.0
sudo apt install mysql-server

# 启动 MySQL
sudo systemctl start mysql
sudo systemctl enable mysql

# 安全配置
sudo mysql_secure_installation
```

#### Redis 缓存

```bash
# 安装 Redis
sudo apt install redis-server

# 启动 Redis
sudo systemctl start redis
sudo systemctl enable redis

# 验证安装
redis-cli ping
```

#### Nginx Web 服务器

```bash
# 安装 Nginx
sudo apt install nginx

# 启动 Nginx
sudo systemctl start nginx
sudo systemctl enable nginx
```

## 数据库初始化

### 1. 创建数据库

```bash
# 登录 MySQL
mysql -u root -p

# 创建数据库
CREATE DATABASE crm_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 创建用户并授权
CREATE USER 'crm_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON crm_system.* TO 'crm_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. 导入数据表

```bash
# 导入初始化脚本
mysql -u crm_user -p crm_system < src/main/resources/db_init.sql
```

## 后端部署

### 1. 修改配置文件

编辑 `src/main/resources/application.yml`:

```yaml
server:
  port: 8080

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/crm_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: crm_user
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password

jwt:
  secret: your_jwt_secret_key_here_should_be_long_enough
  expiration: 86400000
```

### 2. 打包应用

```bash
# 清理并打包
mvn clean package -DskipTests

# 打包完成后，jar 文件位于 target/crm-system-1.0.0.jar
```

### 3. 创建启动脚本

创建 `start.sh`:

```bash
#!/bin/bash

APP_NAME=crm-system-1.0.0.jar
APP_HOME=/opt/crm-system

cd $APP_HOME

# 检查是否已运行
PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')
if [ -n "$PID" ]; then
    echo "Application is already running, PID: $PID"
    exit 1
fi

# 启动应用
nohup java -jar \
    -Xms512m \
    -Xmx1024m \
    -Dspring.profiles.active=prod \
    $APP_NAME > logs/app.log 2>&1 &

echo "Application started successfully"
```

创建 `stop.sh`:

```bash
#!/bin/bash

APP_NAME=crm-system-1.0.0.jar

PID=$(ps -ef | grep $APP_NAME | grep -v grep | awk '{print $2}')

if [ -z "$PID" ]; then
    echo "Application is not running"
    exit 1
fi

kill -15 $PID
echo "Application stopped successfully"
```

### 4. 部署应用

```bash
# 创建应用目录
sudo mkdir -p /opt/crm-system/logs

# 复制 jar 文件
sudo cp target/crm-system-1.0.0.jar /opt/crm-system/

# 复制启动脚本
sudo cp start.sh stop.sh /opt/crm-system/
sudo chmod +x /opt/crm-system/*.sh

# 启动应用
cd /opt/crm-system
./start.sh
```

### 5. 配置系统服务（可选）

创建 `/etc/systemd/system/crm-system.service`:

```ini
[Unit]
Description=CRM System Service
After=network.target

[Service]
Type=simple
User=root
WorkingDirectory=/opt/crm-system
ExecStart=/usr/bin/java -jar /opt/crm-system/crm-system-1.0.0.jar
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
```

启用服务:

```bash
sudo systemctl daemon-reload
sudo systemctl enable crm-system
sudo systemctl start crm-system
sudo systemctl status crm-system
```

## 前端部署

### 1. 构建前端

```bash
cd frontend

# 安装依赖
npm install

# 构建生产版本
npm run build

# 构建完成后，静态文件位于 dist/ 目录
```

### 2. 配置 Nginx

创建 `/etc/nginx/sites-available/crm-system`:

```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    # 前端静态文件
    location / {
        root /var/www/crm-system;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    # 后端 API 代理
    location /api/ {
        proxy_pass http://localhost:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        
        # 超时设置
        proxy_connect_timeout 60s;
        proxy_send_timeout 60s;
        proxy_read_timeout 60s;
    }
    
    # Gzip 压缩
    gzip on;
    gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
    gzip_min_length 1000;
}
```

### 3. 部署前端文件

```bash
# 创建网站目录
sudo mkdir -p /var/www/crm-system

# 复制构建文件
sudo cp -r frontend/dist/* /var/www/crm-system/

# 设置权限
sudo chown -R www-data:www-data /var/www/crm-system
sudo chmod -R 755 /var/www/crm-system

# 启用站点
sudo ln -s /etc/nginx/sites-available/crm-system /etc/nginx/sites-enabled/

# 测试配置
sudo nginx -t

# 重启 Nginx
sudo systemctl restart nginx
```

## HTTPS 配置（推荐）

### 使用 Let's Encrypt 免费证书

```bash
# 安装 Certbot
sudo apt install certbot python3-certbot-nginx

# 获取证书并自动配置 Nginx
sudo certbot --nginx -d your-domain.com

# 自动续期
sudo certbot renew --dry-run
```

Nginx 配置会自动更新为:

```nginx
server {
    listen 443 ssl http2;
    server_name your-domain.com;
    
    ssl_certificate /etc/letsencrypt/live/your-domain.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/your-domain.com/privkey.pem;
    
    # 其他配置同上...
}

server {
    listen 80;
    server_name your-domain.com;
    return 301 https://$server_name$request_uri;
}
```

## 监控和日志

### 1. 应用日志

```bash
# 查看应用日志
tail -f /opt/crm-system/logs/app.log

# 查看 Nginx 访问日志
tail -f /var/log/nginx/access.log

# 查看 Nginx 错误日志
tail -f /var/log/nginx/error.log
```

### 2. 系统监控

```bash
# 查看 Java 进程
jps -l

# 查看内存使用
free -h

# 查看磁盘使用
df -h

# 查看 CPU 使用
top
```

## 备份策略

### 1. 数据库备份

创建备份脚本 `/opt/backup/backup_db.sh`:

```bash
#!/bin/bash

BACKUP_DIR=/opt/backup/mysql
DATE=$(date +%Y%m%d_%H%M%S)
DB_NAME=crm_system
DB_USER=crm_user
DB_PASS=your_password

mkdir -p $BACKUP_DIR

mysqldump -u$DB_USER -p$DB_PASS $DB_NAME | gzip > $BACKUP_DIR/${DB_NAME}_${DATE}.sql.gz

# 删除 7 天前的备份
find $BACKUP_DIR -name "*.sql.gz" -mtime +7 -delete

echo "Database backup completed: ${DB_NAME}_${DATE}.sql.gz"
```

### 2. 定时备份

```bash
# 添加定时任务
crontab -e

# 每天凌晨 2 点执行备份
0 2 * * * /opt/backup/backup_db.sh
```

## 性能优化

### 1. JVM 参数优化

```bash
java -jar \
    -Xms1024m \
    -Xmx2048m \
    -XX:+UseG1GC \
    -XX:MaxGCPauseMillis=200 \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/opt/crm-system/logs/heapdump.hprof \
    crm-system-1.0.0.jar
```

### 2. MySQL 优化

编辑 `/etc/mysql/mysql.conf.d/mysqld.cnf`:

```ini
[mysqld]
# 连接数
max_connections = 200

# 缓冲池大小（建议设置为物理内存的 70-80%）
innodb_buffer_pool_size = 2G

# 日志文件大小
innodb_log_file_size = 256M

# 查询缓存
query_cache_size = 64M
query_cache_type = 1
```

### 3. Redis 优化

编辑 `/etc/redis/redis.conf`:

```ini
# 最大内存
maxmemory 512mb

# 内存淘汰策略
maxmemory-policy allkeys-lru

# 持久化
save 900 1
save 300 10
save 60 10000
```

## 故障排查

### 1. 应用无法启动

```bash
# 查看日志
tail -100 /opt/crm-system/logs/app.log

# 检查端口占用
netstat -tlnp | grep 8080

# 检查 Java 进程
ps -ef | grep java
```

### 2. 数据库连接失败

```bash
# 测试数据库连接
mysql -u crm_user -p -h localhost crm_system

# 检查 MySQL 状态
sudo systemctl status mysql

# 查看 MySQL 错误日志
sudo tail -100 /var/log/mysql/error.log
```

### 3. Redis 连接失败

```bash
# 测试 Redis 连接
redis-cli ping

# 检查 Redis 状态
sudo systemctl status redis

# 查看 Redis 日志
sudo tail -100 /var/log/redis/redis-server.log
```

## 安全建议

1. **修改默认端口**: 不要使用默认的 8080 端口
2. **配置防火墙**: 只开放必要的端口（80, 443）
3. **定期更新**: 及时更新系统和依赖包
4. **强密码策略**: 使用强密码并定期更换
5. **SSL/TLS**: 生产环境必须使用 HTTPS
6. **备份策略**: 定期备份数据库和重要文件
7. **日志审计**: 定期检查系统日志
8. **限流防护**: 配置接口限流防止攻击

## 扩展部署

### 负载均衡

使用 Nginx 配置多个后端实例:

```nginx
upstream crm_backend {
    server 127.0.0.1:8080 weight=1;
    server 127.0.0.1:8081 weight=1;
    server 127.0.0.1:8082 weight=1;
}

server {
    location /api/ {
        proxy_pass http://crm_backend/api/;
        # 其他配置...
    }
}
```

### 数据库主从复制

配置 MySQL 主从复制实现读写分离，提升性能和可用性。

### Redis 集群

配置 Redis 集群或哨兵模式，提高缓存的可用性。

## 联系支持

如遇到部署问题，请联系技术支持团队。
