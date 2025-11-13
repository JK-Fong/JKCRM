# CRM 会员管理系统

## 项目简介

这是一个基于 Spring Boot + Vue 3 的企业级 CRM（客户关系管理）会员管理系统，涵盖了市面上常见的业务功能模块，包括会员管理、客户管理、销售管理、营销活动、数据统计等。

## 技术栈

### 后端技术

- **框架**: Spring Boot 3.2.0
- **数据库**: MySQL 8.0
- **ORM**: MyBatis-Plus 3.5.5
- **安全**: Spring Security + JWT
- **缓存**: Redis
- **API 文档**: Knife4j (Swagger)
- **工具库**: Lombok, Hutool

### 前端技术

- **框架**: Vue 3.4
- **UI 组件**: Element Plus 2.5
- **路由**: Vue Router 4.2
- **状态管理**: Pinia 2.1
- **构建工具**: Vite 5.0
- **图表**: ECharts 5.4
- **HTTP 客户端**: Axios 1.6

## 功能模块

### 1. 用户权限模块
- 系统用户管理
- 角色管理
- 权限管理
- 部门管理
- 登录认证（JWT）

### 2. 会员管理模块
- 会员信息管理（CRUD）
- 会员等级管理
- 会员标签管理
- 会员积分管理
- 会员成长值
- 会员生命周期分析

### 3. 客户管理模块
- 客户信息管理
- 客户分类管理
- 客户跟进记录
- 客户公海池
- 客户分配规则
- 客户转化漏斗

### 4. 销售管理模块
- 销售机会管理
- 销售线索管理
- 商机跟进
- 销售订单管理
- 合同管理
- 回款管理

### 5. 营销活动模块
- 营销活动管理
- 优惠券管理
- 积分商城
- 会员卡管理
- 短信/邮件营销
- 活动效果分析

### 6. 数据统计模块
- 会员数据统计
- 销售数据统计
- 客户转化分析
- 营销效果分析
- 业绩排行榜
- 数据可视化大屏

## 项目结构

```
crm-system/
├── src/
│   ├── main/
│   │   ├── java/com/crm/
│   │   │   ├── controller/      # 控制器层
│   │   │   ├── service/          # 业务逻辑层
│   │   │   ├── mapper/           # 数据访问层
│   │   │   ├── entity/           # 实体类
│   │   │   ├── dto/              # 数据传输对象
│   │   │   ├── vo/               # 视图对象
│   │   │   ├── config/           # 配置类
│   │   │   ├── common/           # 公共组件
│   │   │   └── utils/            # 工具类
│   │   └── resources/
│   │       ├── mapper/           # MyBatis XML
│   │       ├── application.yml   # 配置文件
│   │       └── db_init.sql       # 数据库初始化脚本
│   └── test/                     # 测试代码
├── frontend/                     # 前端项目
│   ├── src/
│   │   ├── views/                # 页面组件
│   │   ├── components/           # 公共组件
│   │   ├── api/                  # API 接口
│   │   ├── router/               # 路由配置
│   │   ├── store/                # 状态管理
│   │   └── utils/                # 工具函数
│   ├── package.json
│   └── vite.config.js
└── pom.xml                       # Maven 配置
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+
- Node.js 16+

### 后端启动

1. **创建数据库**

```bash
mysql -u root -p
```

执行 `src/main/resources/db_init.sql` 文件初始化数据库

2. **修改配置文件**

编辑 `src/main/resources/application.yml`，修改数据库和 Redis 连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/crm_system?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
  
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password
```

3. **编译运行**

```bash
# 编译项目
mvn clean package

# 运行项目
mvn spring-boot:run
```

或者直接运行主类 `com.crm.CrmSystemApplication`

4. **访问接口文档**

启动成功后访问: http://localhost:8080/api/doc.html

### 前端启动

1. **安装依赖**

```bash
cd frontend
npm install
# 或使用 pnpm
pnpm install
```

2. **启动开发服务器**

```bash
npm run dev
```

3. **访问系统**

浏览器访问: http://localhost:3000

### 默认账号

- 用户名: `admin`
- 密码: `admin123`

## API 接口

### 会员管理

- `GET /api/member/page` - 分页查询会员列表
- `GET /api/member/{id}` - 获取会员详情
- `POST /api/member` - 创建会员
- `PUT /api/member/{id}` - 更新会员
- `DELETE /api/member/{id}` - 删除会员
- `POST /api/member/{id}/points/add` - 增加积分
- `POST /api/member/{id}/points/deduct` - 扣减积分

### 数据统计

- `GET /api/statistics/dashboard` - 获取首页统计数据
- `GET /api/statistics/member/trend` - 获取会员增长趋势
- `GET /api/statistics/sales/trend` - 获取销售趋势
- `GET /api/statistics/customer/funnel` - 获取客户转化漏斗
- `GET /api/statistics/performance/ranking` - 获取业绩排行榜

## 数据库设计

### 核心表结构

- `sys_user` - 系统用户表
- `sys_role` - 角色表
- `crm_member` - 会员表
- `crm_member_level` - 会员等级表
- `crm_member_tag` - 会员标签表
- `crm_member_points_log` - 积分记录表
- `crm_customer` - 客户表
- `crm_customer_follow` - 客户跟进记录表
- `crm_opportunity` - 销售机会表
- `crm_order` - 订单表
- `crm_contract` - 合同表
- `crm_campaign` - 营销活动表
- `crm_coupon` - 优惠券表

详细的数据库设计请参考 `src/main/resources/db_init.sql`

## 系统特性

### 1. 统一返回结果封装

```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "timestamp": 1234567890
}
```

### 2. 全局异常处理

- 业务异常处理
- 参数校验异常处理
- 系统异常处理

### 3. 接口文档自动生成

使用 Knife4j 自动生成 API 文档，支持在线调试

### 4. 数据自动填充

- 创建时间自动填充
- 更新时间自动填充
- 创建人/更新人自动填充

### 5. 逻辑删除

使用 MyBatis-Plus 的逻辑删除功能，数据不会真正删除

### 6. 分页查询

统一的分页查询封装，支持排序

### 7. Redis 缓存

热点数据缓存，提升系统性能

## 开发规范

### 后端规范

1. **命名规范**
   - 类名：大驼峰命名法（PascalCase）
   - 方法名：小驼峰命名法（camelCase）
   - 常量：全大写下划线分隔（UPPER_SNAKE_CASE）

2. **分层规范**
   - Controller 层：只做参数校验和结果返回
   - Service 层：业务逻辑处理
   - Mapper 层：数据访问

3. **注释规范**
   - 类和方法必须添加注释
   - 复杂逻辑必须添加行内注释

### 前端规范

1. **组件命名**
   - 组件文件名：大驼峰命名法
   - 组件标签：短横线命名法

2. **代码风格**
   - 使用 Composition API
   - 使用 `<script setup>` 语法糖

## 部署说明

### 后端部署

1. **打包**

```bash
mvn clean package -DskipTests
```

2. **运行**

```bash
java -jar target/crm-system-1.0.0.jar
```

### 前端部署

1. **构建**

```bash
cd frontend
npm run build
```

2. **部署**

将 `dist` 目录部署到 Nginx 或其他 Web 服务器

### Nginx 配置示例

```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    location / {
        root /path/to/dist;
        try_files $uri $uri/ /index.html;
    }
    
    location /api/ {
        proxy_pass http://localhost:8080/api/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 系统截图

### 数据看板
展示系统核心数据统计，包括会员数、客户数、订单数、销售额等关键指标，以及各类数据趋势图表。

### 会员管理
完整的会员信息管理功能，支持会员的增删改查、积分管理、等级管理等操作。

### 客户管理
客户信息管理、客户跟进记录、客户公海池等功能。

### 销售管理
销售机会管理、订单管理、合同管理等功能。

### 营销活动
营销活动管理、优惠券管理等功能。

## 常见问题

### 1. 数据库连接失败

检查 MySQL 是否启动，配置文件中的数据库连接信息是否正确。

### 2. Redis 连接失败

检查 Redis 是否启动，配置文件中的 Redis 连接信息是否正确。

### 3. 前端跨域问题

开发环境已配置代理，生产环境需要在 Nginx 中配置跨域。

### 4. 接口 404

检查后端是否启动，context-path 是否配置正确（默认为 `/api`）。

## 更新日志

### v1.0.0 (2024-01-01)

- 初始版本发布
- 实现会员管理模块
- 实现客户管理模块
- 实现销售管理模块
- 实现营销活动模块
- 实现数据统计模块

## 技术支持

如有问题，请提交 Issue 或联系开发团队。

## 开源协议

本项目采用 Apache 2.0 开源协议。

## 贡献指南

欢迎提交 Pull Request 或 Issue。

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 提交 Pull Request

## 作者

CRM Team

## 致谢

感谢所有开源项目的贡献者。
