package com.vance.demo.pojo;

import java.util.HashMap;
import java.util.Map;

import com.vance.demo.enums.ResultCodeEnum;

/**
 * API共用回應物件
 */
public class ApiResult {
    /** URL path */
    private String path;
    /** 是否成功 */
    private Boolean success;
    /** 代碼 */
    private Integer code;
    /** 訊息 */
    private String message;
    /** timestamp */
    private Long timestamp;
    /** 回傳資料 */
    private Map<String, Object> data = new HashMap<>();

    public String getPath() {
        return path;
    }

    public ApiResult setPath(String path) {
        this.path = path;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public ApiResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public ApiResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public ApiResult setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public ApiResult setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }

    /**
     * 成功
     * 
     * @return
     */
    public static ApiResult success() {
        return result(ResultCodeEnum.SUCCESS);
    }

    /**
     * 失敗
     * 
     * @return
     */
    public static ApiResult fail() {
        return result(ResultCodeEnum.FAIL);
    }

    /**
     * 結果
     * 
     * @param result
     * @return
     */
    public static ApiResult result(ResultCodeEnum result) {
        return new ApiResult()
                .setTimestamp(System.currentTimeMillis())
                .setSuccess(result.getSuccess())
                .setCode(result.getCode())
                .setMessage(result.getMessage());
    }

    /**
     * 設定回應資料
     * 
     * @param map
     * @return
     */
    public ApiResult data(Map<String, Object> map) {
        return this.setData(map);
    }

    /**
     * 設定回應資料
     * 
     * @param key
     * @param value
     * @return
     */
    public ApiResult data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 自定義回應訊息
     * 
     * @param message 訊息
     * @return
     */
    public ApiResult message(String message) {
        return this.setMessage(message);
    }
}
