package com.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单实体
 * 
 * @author CRM Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_order")
@Schema(description = "订单信息")
public class Order extends BaseEntity {
    
    @Schema(description = "订单号")
    private String orderNo;
    
    @Schema(description = "客户ID")
    private Long customerId;
    
    @Schema(description = "会员ID")
    private Long memberId;
    
    @Schema(description = "订单金额")
    private BigDecimal amount;
    
    @Schema(description = "优惠金额")
    private BigDecimal discountAmount;
    
    @Schema(description = "实付金额")
    private BigDecimal actualAmount;
    
    @Schema(description = "订单状态(1-待支付 2-已支付 3-已发货 4-已完成 5-已取消)")
    private Integer status;
}
