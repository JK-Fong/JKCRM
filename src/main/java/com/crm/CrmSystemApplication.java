package com.crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * CRM系统启动类
 * 
 * @author CRM Team
 * @since 2024-01-01
 */
@SpringBootApplication
@MapperScan("com.crm.mapper")
@EnableCaching
@EnableAsync
@EnableScheduling
public class CrmSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmSystemApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("CRM会员管理系统启动成功！");
        System.out.println("接口文档地址: http://localhost:8080/api/doc.html");
        System.out.println("========================================\n");
    }
}
