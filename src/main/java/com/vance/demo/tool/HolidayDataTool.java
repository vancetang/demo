package com.vance.demo.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vance.demo.constant.Constants;
import com.vance.demo.util.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 北市政府行政機關辦公日曆表資料工具
 * 
 * @author Vance
 */
@Slf4j
public class HolidayDataTool {

    /** 資料代號 */
    private static final String DATASET_RID = "964e936d-d971-4567-a467-aa67b930f98e";

    /** 資料來源 */
    private static final String FILE_URL = "https://data.taipei/api/frontstage/tpeod/dataset/resource.download?rid="
            + DATASET_RID;

    /**
     * CSV 欄位名稱常量
     */
    private static final class CsvCol {
        public static final String DATE = "Date";
        public static final String NAME = "name";
        public static final String IS_HOLIDAY = "isHoliday";
        public static final String HOLIDAY_CATEGORY = "holidayCategory";
        public static final String DESCRIPTION = "description";
    }

    /**
     * 主程式
     * 
     * @param args
     */
    public static void main(String[] args) {
        log.info("=========== 解析北市政府行政機關辦公日曆開始 ===========");
        int exitCode = 0;
        try {
            new HolidayDataTool().execute();
        } catch (Exception e) {
            log.error("Error: {}", ExceptionUtils.getStackTrace(e));
            exitCode = 1;
        }
        log.info("=========== 解析北市政府行政機關辦公日曆結束 ===========");
        System.exit(exitCode);
    }

    /**
     * 執行
     */
    public void execute() {
        log.info("● 資料來源:{}", FILE_URL);
        log.info("---------------------------------------------------");
        try (InputStream inputStream = this.download(FILE_URL);
                BOMInputStream bomIn = BOMInputStream.builder()
                        .setInputStream(inputStream)
                        .setInclude(false)
                        .get();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(bomIn, Constants.CharSet.UTF8));
                CSVParser parser = createCsvParser(reader)) {
            for (CSVRecord record : parser) {
                processRecord(record);
            }
        } catch (Exception e) {
            throw new RuntimeException("解析 CSV 失敗", e);
        }
    }

    /**
     * 建立 CSV 解析器
     * 
     * @param reader BufferedReader
     * @return CSVParser
     * @throws Exception
     */
    private CSVParser createCsvParser(BufferedReader reader) throws Exception {
        return CSVFormat.DEFAULT.builder()
                .setHeader() // 設置自動解析表頭
                .setTrim(true) // 啟用去除空白
                .setSkipHeaderRecord(true) // 跳過表頭行
                .get().parse(reader);
    }

    /**
     * 處理單條記錄
     * 
     * @param record CSV 檔案記錄
     */
    private void processRecord(CSVRecord record) {
        try {
            Date date = DateUtil.parseDate(record.get(CsvCol.DATE));
            String isHoliday = record.get(CsvCol.IS_HOLIDAY);
            String holidayName = record.get(CsvCol.NAME);
            String type = record.get(CsvCol.HOLIDAY_CATEGORY);
            String desc = record.get(CsvCol.DESCRIPTION);

            String text = determineHolidayText(isHoliday, type, holidayName, date, desc);
            log.info("{}({}) = {}; {}", DateUtil.formatDate(date, "yyyy-MM-dd"),
                    DateUtil.toWeek(date), isHoliday, text);
        } catch (IllegalArgumentException e) {
            log.warn("記錄處理失敗，缺少必要欄位: {}", e.getMessage());
        } catch (Exception e) {
            log.error("記錄處理異常: {}", ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * 判斷假期描述文字
     * 
     * @param isHoliday   是否為假期
     * @param type        假期類型
     * @param holidayName 假期名稱
     * @param date        日期
     * @param desc        假期描述
     */
    private String determineHolidayText(String isHoliday, String type, String holidayName, Date date, String desc) {
        String text = "";
        if (StringUtils.equals("是", isHoliday)) {
            if (StringUtils.equalsAny(type, "星期六、星期日", "星期日")) {
                // 單純周休
                text = "周休";
            } else if (DateUtil.isWeekoff(date)) {
                // 周休又遇到特殊日(不一定為國定假日)
                String tmp = StringUtils.firstNonBlank(holidayName, type);
                if (StringUtils.isNotBlank(tmp)) {
                    text = "周休 (" + tmp + ")";
                }
            } else {
                // 特殊休假日(補假或國定假日)
                text = StringUtils.firstNonBlank(holidayName, type);
            }
        } else {
            // 補行上班日 or 只紀念不放假
            if (StringUtils.equalsAny(type, "補行上班日")) {
                text = "補班日";
            } else {
                text = "只紀念不放假";
                if (StringUtils.isNotBlank(holidayName)) {
                    text += " (" + holidayName + ")";
                }
            }
        }
        if (StringUtils.isNotBlank(desc)) {
            text = StringUtils.join(text, "[", desc, "]");
        }
        return text;
    }

    /**
     * 從 URL 獲取 InputStream
     * 
     * @param fileUrl 檔案網址路徑
     * @return InputStream
     * @throws Exception
     */
    private InputStream download(String fileUrl) throws Exception {
        try {
            URL url = new URI(fileUrl).toURL();
            InputStream inputStream = url.openStream();
            log.info("成功獲取資料流:{}", fileUrl);
            return inputStream;
        } catch (Exception e) {
            log.error("獲取資料流失敗:{}", e.getMessage());
            throw e;
        }
    }
}
