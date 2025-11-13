-- CRM会员管理系统数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS crm_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE crm_system;

-- ========================================
-- 系统用户权限表
-- ========================================

-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(50) COMMENT '邮箱',
    dept_id BIGINT COMMENT '部门ID',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用 1-启用)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    update_by BIGINT COMMENT '更新人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除(0-未删除 1-已删除)',
    INDEX idx_username (username),
    INDEX idx_phone (phone),
    INDEX idx_dept_id (dept_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- 角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    description VARCHAR(200) COMMENT '描述',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用 1-启用)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ========================================
-- 会员管理表
-- ========================================

-- 会员表
CREATE TABLE IF NOT EXISTS crm_member (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    member_no VARCHAR(50) NOT NULL UNIQUE COMMENT '会员编号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    phone VARCHAR(20) NOT NULL COMMENT '手机号',
    email VARCHAR(50) COMMENT '邮箱',
    gender TINYINT COMMENT '性别(0-女 1-男 2-未知)',
    birthday DATE COMMENT '生日',
    avatar VARCHAR(200) COMMENT '头像',
    level_id BIGINT COMMENT '会员等级ID',
    points INT DEFAULT 0 COMMENT '积分',
    growth_value INT DEFAULT 0 COMMENT '成长值',
    balance DECIMAL(10,2) DEFAULT 0.00 COMMENT '余额',
    source VARCHAR(20) COMMENT '来源渠道',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用 1-正常)',
    register_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    last_visit_time DATETIME COMMENT '最后访问时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    update_by BIGINT COMMENT '更新人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_member_no (member_no),
    INDEX idx_phone (phone),
    INDEX idx_level_id (level_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员表';

-- 会员等级表
CREATE TABLE IF NOT EXISTS crm_member_level (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    level_name VARCHAR(50) NOT NULL COMMENT '等级名称',
    level_code VARCHAR(20) NOT NULL UNIQUE COMMENT '等级编码',
    growth_value INT DEFAULT 0 COMMENT '所需成长值',
    discount_rate DECIMAL(3,2) DEFAULT 1.00 COMMENT '折扣率',
    privileges TEXT COMMENT '特权说明',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员等级表';

-- 会员标签表
CREATE TABLE IF NOT EXISTS crm_member_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    tag_type VARCHAR(20) COMMENT '标签类型',
    description VARCHAR(200) COMMENT '描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员标签表';

-- 会员标签关联表
CREATE TABLE IF NOT EXISTS crm_member_tag_relation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    member_id BIGINT NOT NULL COMMENT '会员ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_member_id (member_id),
    INDEX idx_tag_id (tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员标签关联表';

-- 积分记录表
CREATE TABLE IF NOT EXISTS crm_member_points_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    member_id BIGINT NOT NULL COMMENT '会员ID',
    points INT NOT NULL COMMENT '积分变动',
    type TINYINT NOT NULL COMMENT '类型(1-增加 2-减少)',
    reason VARCHAR(200) COMMENT '原因',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_member_id (member_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='积分记录表';

-- ========================================
-- 客户管理表
-- ========================================

-- 客户表
CREATE TABLE IF NOT EXISTS crm_customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    customer_no VARCHAR(50) NOT NULL UNIQUE COMMENT '客户编号',
    customer_name VARCHAR(100) NOT NULL COMMENT '客户名称',
    customer_type TINYINT COMMENT '客户类型(1-个人 2-企业)',
    industry VARCHAR(50) COMMENT '所属行业',
    level VARCHAR(20) COMMENT '客户级别',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(50) COMMENT '邮箱',
    address VARCHAR(200) COMMENT '地址',
    owner_id BIGINT COMMENT '负责人ID',
    status TINYINT DEFAULT 1 COMMENT '状态(1-潜在客户 2-意向客户 3-成交客户 4-流失客户)',
    source VARCHAR(20) COMMENT '来源',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    update_by BIGINT COMMENT '更新人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_customer_no (customer_no),
    INDEX idx_owner_id (owner_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- 客户跟进记录表
CREATE TABLE IF NOT EXISTS crm_customer_follow (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    follow_type VARCHAR(20) COMMENT '跟进方式(电话/邮件/拜访等)',
    content TEXT COMMENT '跟进内容',
    next_time DATETIME COMMENT '下次跟进时间',
    user_id BIGINT COMMENT '跟进人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_customer_id (customer_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户跟进记录表';

-- 客户公海池表
CREATE TABLE IF NOT EXISTS crm_customer_pool (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    reason VARCHAR(200) COMMENT '进入原因',
    enter_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '进入时间',
    status TINYINT DEFAULT 1 COMMENT '状态(1-公海中 2-已领取)',
    INDEX idx_customer_id (customer_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户公海池表';

-- ========================================
-- 销售管理表
-- ========================================

-- 销售线索表
CREATE TABLE IF NOT EXISTS crm_leads (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    leads_name VARCHAR(100) NOT NULL COMMENT '线索名称',
    contact_name VARCHAR(50) COMMENT '联系人',
    phone VARCHAR(20) COMMENT '电话',
    email VARCHAR(50) COMMENT '邮箱',
    source VARCHAR(20) COMMENT '来源',
    status TINYINT DEFAULT 1 COMMENT '状态(1-新线索 2-已联系 3-已转化 4-已放弃)',
    owner_id BIGINT COMMENT '负责人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_owner_id (owner_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售线索表';

-- 销售机会表
CREATE TABLE IF NOT EXISTS crm_opportunity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    opportunity_name VARCHAR(100) NOT NULL COMMENT '机会名称',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    amount DECIMAL(12,2) COMMENT '预计金额',
    stage VARCHAR(20) COMMENT '销售阶段',
    probability INT COMMENT '成功概率(%)',
    expected_date DATE COMMENT '预计成交日期',
    owner_id BIGINT COMMENT '负责人ID',
    status TINYINT DEFAULT 1 COMMENT '状态(1-进行中 2-已赢单 3-已输单)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_customer_id (customer_id),
    INDEX idx_owner_id (owner_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售机会表';

-- 订单表
CREATE TABLE IF NOT EXISTS crm_order (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    order_no VARCHAR(50) NOT NULL UNIQUE COMMENT '订单号',
    customer_id BIGINT COMMENT '客户ID',
    member_id BIGINT COMMENT '会员ID',
    amount DECIMAL(12,2) NOT NULL COMMENT '订单金额',
    discount_amount DECIMAL(12,2) DEFAULT 0.00 COMMENT '优惠金额',
    actual_amount DECIMAL(12,2) NOT NULL COMMENT '实付金额',
    status TINYINT DEFAULT 1 COMMENT '订单状态(1-待支付 2-已支付 3-已发货 4-已完成 5-已取消)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_order_no (order_no),
    INDEX idx_customer_id (customer_id),
    INDEX idx_member_id (member_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 合同表
CREATE TABLE IF NOT EXISTS crm_contract (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    contract_no VARCHAR(50) NOT NULL UNIQUE COMMENT '合同编号',
    contract_name VARCHAR(100) NOT NULL COMMENT '合同名称',
    customer_id BIGINT NOT NULL COMMENT '客户ID',
    amount DECIMAL(12,2) NOT NULL COMMENT '合同金额',
    start_date DATE COMMENT '开始日期',
    end_date DATE COMMENT '结束日期',
    owner_id BIGINT COMMENT '负责人ID',
    status TINYINT DEFAULT 1 COMMENT '状态(1-草稿 2-审批中 3-已生效 4-已终止)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_contract_no (contract_no),
    INDEX idx_customer_id (customer_id),
    INDEX idx_owner_id (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同表';

-- ========================================
-- 营销活动表
-- ========================================

-- 营销活动表
CREATE TABLE IF NOT EXISTS crm_campaign (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    campaign_name VARCHAR(100) NOT NULL COMMENT '活动名称',
    campaign_type VARCHAR(20) COMMENT '活动类型',
    start_time DATETIME COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    budget DECIMAL(12,2) COMMENT '预算',
    target_audience TEXT COMMENT '目标受众',
    description TEXT COMMENT '活动描述',
    status TINYINT DEFAULT 1 COMMENT '状态(1-未开始 2-进行中 3-已结束)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by BIGINT COMMENT '创建人ID',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='营销活动表';

-- 优惠券表
CREATE TABLE IF NOT EXISTS crm_coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    coupon_name VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    coupon_type TINYINT COMMENT '类型(1-满减券 2-折扣券 3-代金券)',
    discount_value DECIMAL(10,2) COMMENT '优惠金额/折扣',
    min_amount DECIMAL(10,2) COMMENT '最低消费',
    total_count INT COMMENT '发行总量',
    used_count INT DEFAULT 0 COMMENT '已使用数量',
    valid_days INT COMMENT '有效天数',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用 1-启用)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 会员优惠券表
CREATE TABLE IF NOT EXISTS crm_member_coupon (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    member_id BIGINT NOT NULL COMMENT '会员ID',
    coupon_id BIGINT NOT NULL COMMENT '优惠券ID',
    status TINYINT DEFAULT 1 COMMENT '状态(1-未使用 2-已使用 3-已过期)',
    receive_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
    use_time DATETIME COMMENT '使用时间',
    expire_time DATETIME COMMENT '过期时间',
    INDEX idx_member_id (member_id),
    INDEX idx_coupon_id (coupon_id),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会员优惠券表';

-- ========================================
-- 初始化数据
-- ========================================

-- 插入默认管理员用户
INSERT INTO sys_user (username, password, real_name, phone, email, status) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '系统管理员', '13800138000', 'admin@crm.com', 1);

-- 插入会员等级
INSERT INTO crm_member_level (level_name, level_code, growth_value, discount_rate, privileges) VALUES
('普通会员', 'NORMAL', 0, 1.00, '基础会员权益'),
('银卡会员', 'SILVER', 1000, 0.95, '95折优惠，生日礼品'),
('金卡会员', 'GOLD', 5000, 0.90, '9折优惠，专属客服，生日礼品'),
('钻石会员', 'DIAMOND', 10000, 0.85, '85折优惠，专属客服，优先发货，生日礼品');

-- 插入会员标签
INSERT INTO crm_member_tag (tag_name, tag_type, description) VALUES
('高价值客户', 'VALUE', '消费金额高的客户'),
('活跃用户', 'ACTIVITY', '经常访问的用户'),
('沉睡用户', 'ACTIVITY', '长时间未访问的用户'),
('新用户', 'LIFECYCLE', '新注册的用户'),
('流失预警', 'RISK', '有流失风险的用户');
