package com.vance.demo.util.common;

import lombok.experimental.UtilityClass;

/**
 * 轉型工具
 * 
 * @author Vance
 */
@UtilityClass
public class CastUtil {

    /**
     * 強制轉型，通常用於忽略編譯器類型檢查。
     * 
     * @param <T>    期望返回的值的類型
     * @param object 要轉型的物件
     * @return 轉型後的物件
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object object) {
        return (T) object;
    }

}
