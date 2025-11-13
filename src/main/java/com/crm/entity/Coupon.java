package com.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 优惠券实体
 * 
 * @author CRM Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_coupon")
@Schema(description = "优惠券")
public class Coupon extends BaseEntity {
    
    @Schema(description = "优惠券名称")
    private String couponName;
    
    @Schema(description = "类型(1-满减券 2-折扣券 3-代金券)")
    private Integer couponType;
    
    @Schema(description = "优惠金额/折扣")
    private BigDecimal discountValue;
    
    @Schema(description = "最低消费")
    private BigDecimal minAmount;
    
    @Schema(description = "发行总量")
    private Integer totalCount;
    
    @Schema(description = "已使用数量")
    private Integer usedCount;
    
    @Schema(description = "有效天数")
    private Integer validDays;
    
    @Schema(description = "状态(0-禁用 1-启用)")
    private Integer status;
}
