package com.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户实体
 * 
 * @author CRM Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_customer")
@Schema(description = "客户信息")
public class Customer extends BaseEntity {
    
    @Schema(description = "客户编号")
    private String customerNo;
    
    @Schema(description = "客户名称")
    private String customerName;
    
    @Schema(description = "客户类型(1-个人 2-企业)")
    private Integer customerType;
    
    @Schema(description = "所属行业")
    private String industry;
    
    @Schema(description = "客户级别")
    private String level;
    
    @Schema(description = "联系电话")
    private String phone;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "地址")
    private String address;
    
    @Schema(description = "负责人ID")
    private Long ownerId;
    
    @Schema(description = "状态(1-潜在客户 2-意向客户 3-成交客户 4-流失客户)")
    private Integer status;
    
    @Schema(description = "来源")
    private String source;
}
