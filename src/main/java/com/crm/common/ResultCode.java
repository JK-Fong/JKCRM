package com.crm.common;

import lombok.Getter;

/**
 * 返回状态码枚举
 * 
 * @author CRM Team
 */
@Getter
public enum ResultCode {
    
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    
    /**
     * 失败
     */
    FAIL(400, "操作失败"),
    
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "未认证，请先登录"),
    
    /**
     * 无权限
     */
    FORBIDDEN(403, "无权限访问"),
    
    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),
    
    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    
    /**
     * 参数校验失败
     */
    VALIDATE_FAILED(1001, "参数校验失败"),
    
    /**
     * 用户名或密码错误
     */
    LOGIN_FAILED(1002, "用户名或密码错误"),
    
    /**
     * Token无效
     */
    TOKEN_INVALID(1003, "Token无效或已过期"),
    
    /**
     * 用户已存在
     */
    USER_EXIST(1004, "用户已存在"),
    
    /**
     * 数据已存在
     */
    DATA_EXIST(1005, "数据已存在"),
    
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(1006, "数据不存在"),
    
    /**
     * 操作频繁
     */
    OPERATION_FREQUENT(1007, "操作过于频繁，请稍后再试");
    
    private final Integer code;
    private final String message;
    
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
