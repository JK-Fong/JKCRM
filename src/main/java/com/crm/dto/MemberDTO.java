package com.crm.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 会员DTO
 * 
 * @author CRM Team
 */
@Data
@Schema(description = "会员数据传输对象")
public class MemberDTO implements Serializable {
    
    @Schema(description = "姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;
    
    @Schema(description = "手机号")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @Schema(description = "性别(0-女 1-男 2-未知)")
    private Integer gender;
    
    @Schema(description = "生日")
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
}
