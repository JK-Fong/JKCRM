package com.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 销售机会实体
 * 
 * @author CRM Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_opportunity")
@Schema(description = "销售机会")
public class Opportunity extends BaseEntity {
    
    @Schema(description = "机会名称")
    private String opportunityName;
    
    @Schema(description = "客户ID")
    private Long customerId;
    
    @Schema(description = "预计金额")
    private BigDecimal amount;
    
    @Schema(description = "销售阶段")
    private String stage;
    
    @Schema(description = "成功概率(%)")
    private Integer probability;
    
    @Schema(description = "预计成交日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expectedDate;
    
    @Schema(description = "负责人ID")
    private Long ownerId;
    
    @Schema(description = "状态(1-进行中 2-已赢单 3-已输单)")
    private Integer status;
}
