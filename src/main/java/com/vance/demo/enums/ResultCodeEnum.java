package com.vance.demo.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    SUCCESS(true, 200, "成功"),
    FAIL(false, 400, "請求失敗"),

    NOT_FOUND(false, 404, "介面不存在"),
    FORBIDDEN(false, 403, "資源拒絕訪問"),
    UNAUTHORIZED(false, 401, "未認證（簽名錯誤）"),

    INTERNAL_SERVER_ERROR(false, 500, "伺服器內部錯誤"),

    NULL_POINT(false, 200002, "空指針異常"),
    PARAM_ERROR(false, 200001, "參數錯誤");

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
