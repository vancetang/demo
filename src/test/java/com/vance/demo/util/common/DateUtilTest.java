package com.vance.demo.util.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    public void testParseDate() throws Exception {
        // Test null and empty input
        assertNull(DateUtil.parseDate(null));
        assertNull(DateUtil.parseDate(""));
        assertNull(DateUtil.parseDate("   "));

        // Test yyyy-MM-dd HH:mm:ss format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expected = sdf.parse("2023-12-25 13:45:30");
        assertEquals(expected, DateUtil.parseDate("2023-12-25 13:45:30"));

        // Test yyyy/MM/dd format
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        expected = sdf.parse("2023/12/25");
        assertEquals(expected, DateUtil.parseDate("2023/12/25"));

        // Test yyyy-MM-dd format
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        expected = sdf.parse("2023-12-25");
        assertEquals(expected, DateUtil.parseDate("2023-12-25"));

        // Test yyyyMMdd format
        sdf = new SimpleDateFormat("yyyyMMdd");
        expected = sdf.parse("20231225");
        assertEquals(expected, DateUtil.parseDate("20231225"));

        // Test yyyy/MM/dd HH:mm:ss format
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        expected = sdf.parse("2023/12/25 13:45:30");
        assertEquals(expected, DateUtil.parseDate("2023/12/25 13:45:30"));

        // Test yyyyMMddHHmmss format
        sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        expected = sdf.parse("20231225134530");
        assertEquals(expected, DateUtil.parseDate("20231225134530"));

        // Test invalid format
        // assertNull(DateUtil.parseDate("2023-13-45"));
        // assertNull(DateUtil.parseDate("invalid date"));
    }
}