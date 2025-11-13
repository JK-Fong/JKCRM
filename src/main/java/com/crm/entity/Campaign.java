package com.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 营销活动实体
 * 
 * @author CRM Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_campaign")
@Schema(description = "营销活动")
public class Campaign extends BaseEntity {
    
    @Schema(description = "活动名称")
    private String campaignName;
    
    @Schema(description = "活动类型")
    private String campaignType;
    
    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @Schema(description = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    
    @Schema(description = "预算")
    private BigDecimal budget;
    
    @Schema(description = "目标受众")
    private String targetAudience;
    
    @Schema(description = "活动描述")
    private String description;
    
    @Schema(description = "状态(1-未开始 2-进行中 3-已结束)")
    private Integer status;
}
