package com.vance.demo.enums;

import lombok.Getter;

/**
 * HTTP return Enum
 * 
 * @author Vance
 */
@Getter
public enum ResultCodeEnum {
    /** 成功 */
    SUCCESS(true, 200, "成功"),
    /** 請求失敗 */
    FAIL(false, 400, "請求失敗"),

    /** 未認證（簽名錯誤） */
    UNAUTHORIZED(false, 401, "未認證（簽名錯誤）"),
    /** 資源拒絕訪問 */
    FORBIDDEN(false, 403, "資源拒絕訪問"),
    /** 介面不存在 */
    NOT_FOUND(false, 404, "介面不存在"),
    /** 伺服器內部錯誤 */
    INTERNAL_SERVER_ERROR(false, 500, "伺服器內部錯誤"),

    /** 空指針異常 */
    NULL_POINT(false, 200001, "空指針異常"),
    /** 參數錯誤 */
    PARAM_ERROR(false, 200002, "參數錯誤"),

    /** 用戶不存在 */
    USER_NOT_FOUND(false, 300001, "用戶不存在");

    /**
     * 回應是否成功
     */
    private Boolean success;
    /**
     * 回應狀態碼
     */
    private Integer code;
    /**
     * 回應信息
     */
    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
