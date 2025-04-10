package com.vance.demo.exception;

/**
 * 當用戶不存在時拋出此異常
 * <p>
 * 當用戶不存在時，拋出此異常，用於全局異常處理。
 * </p>
 * 
 * @author Vance
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
