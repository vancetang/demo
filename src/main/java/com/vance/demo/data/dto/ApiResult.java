package com.vance.demo.data.dto;

import java.util.HashMap;
import java.util.Map;

import com.vance.demo.enums.ResultCodeEnum;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * API共用回應物件
 * 
 * @author Vance
 */
@Data
@Accessors(chain = true)
public class ApiResult {
    /**
     * URL路徑
     */
    private String path;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 代碼
     */
    private Integer code;

    /**
     * 訊息
     */
    private String message;

    /**
     * timestamp（時間戳，單位為毫秒）
     */
    private Long timestamp;

    /**
     * 回傳資料
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 創建成功的回應
     * 
     * @return 新建的成功狀態 ApiResult 實例
     */
    public static ApiResult success() {
        return result(ResultCodeEnum.SUCCESS);
    }

    /**
     * 創建失敗的回應
     * 
     * @return 新建的失敗狀態 ApiResult 實例
     */
    public static ApiResult fail() {
        return result(ResultCodeEnum.FAIL);
    }

    /**
     * 根據 ResultCodeEnum 創建回應結果
     * 
     * @param result 用於設置回應的結果枚舉
     * @return 新建的 ApiResult 實例，包含預設值
     */
    public static ApiResult result(ResultCodeEnum result) {
        return new ApiResult()
                .setTimestamp(System.currentTimeMillis())
                .setSuccess(result.getSuccess())
                .setCode(result.getCode())
                .setMessage(result.getMessage());
    }

    /**
     * 設定回應資料（使用整個 Map）
     * 
     * @param map 要設置的資料 Map
     * @return 當前實例，用於鏈式調用
     */
    public ApiResult data(Map<String, Object> map) {
        this.data = new HashMap<>(map);
        return this;
    }

    /**
     * 設定回應資料（單個鍵值對）
     * 
     * @param key   資料的鍵
     * @param value 資料的值
     * @return 當前實例，用於鏈式調用
     */
    public ApiResult data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 自定義回應訊息
     * 
     * @param message 自定義的訊息內容
     * @return 當前實例，用於鏈式調用
     */
    public ApiResult message(String message) {
        this.setMessage(message);
        return this;
    }
}