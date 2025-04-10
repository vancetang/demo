package com.vance.demo.constant;

/**
 * Constant class
 * 
 * @author Vance
 * @since 2020/3/20
 */
public final class Constant {

    /**
     * 防止實例化
     */
    private Constant() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    /**
     * 空格
     */
    public static final char CHAR_SPACE = ' ';
    /**
     * 全形空格
     */
    public static final char CHAR_FULL_SPACE = '　';

    /**
     * 字符集
     */
    public static class CharSet {
        public static final String UTF8 = "UTF-8"; // UTF-8
        public static final String BIG5 = "BIG5"; // BIG5
    }
}