package com.crm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 会员实体
 * 
 * @author CRM Team
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("crm_member")
@Schema(description = "会员信息")
public class Member extends BaseEntity {
    
    @Schema(description = "会员编号")
    private String memberNo;
    
    @Schema(description = "姓名")
    private String name;
    
    @Schema(description = "手机号")
    private String phone;
    
    @Schema(description = "邮箱")
    private String email;
    
    @Schema(description = "性别(0-女 1-男 2-未知)")
    private Integer gender;
    
    @Schema(description = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    
    @Schema(description = "头像")
    private String avatar;
    
    @Schema(description = "会员等级ID")
    private Long levelId;
    
    @Schema(description = "积分")
    private Integer points;
    
    @Schema(description = "成长值")
    private Integer growthValue;
    
    @Schema(description = "余额")
    private BigDecimal balance;
    
    @Schema(description = "来源渠道")
    private String source;
    
    @Schema(description = "状态(0-禁用 1-正常)")
    private Integer status;
    
    @Schema(description = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;
    
    @Schema(description = "最后访问时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisitTime;
}
