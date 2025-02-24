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
    private enum CsvCol {
        DATE("Date"),
        NAME("name"),
        IS_HOLIDAY("isHoliday"),
        HOLIDAY_CATEGORY("holidayCategory"),
        DESCRIPTION("description");

        private final String value;

        CsvCol(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
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
                        new InputStreamReader(bomIn, Constants.charset.UTF8));
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader() // 設置自動解析表頭
                        .setTrim(true) // 啟用去除空白
                        .setSkipHeaderRecord(true) // 跳過表頭行
                        .get().parse(reader);) {
            for (CSVRecord record : parser) {
                processRecord(record);
            }
        } catch (Exception e) {
            throw new RuntimeException("解析 CSV 失敗", e);
        }
    }

    /**
     * 處理單條記錄
     * 
     * @param record CSV 檔案記錄
     */
    private void processRecord(CSVRecord record) {
        try {
            Date date = DateUtil.parseDate(record.get(CsvCol.DATE.getValue()));
            String isHoliday = record.get(CsvCol.IS_HOLIDAY.getValue());
            String holidayName = record.get(CsvCol.NAME.getValue());
            String type = record.get(CsvCol.HOLIDAY_CATEGORY.getValue());
            String desc = record.get(CsvCol.DESCRIPTION.getValue());

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
        if (StringUtils.equals("是", isHoliday)) {
            if (StringUtils.equalsAny(type, "星期六、星期日", "星期日")) {
                return "周休";
            }
            if (DateUtil.isWeekoff(date)) {
                String tmp = StringUtils.firstNonBlank(holidayName, type);
                return StringUtils.isBlank(tmp) ? "周休" : "周休 (" + tmp + ")";
            }
            return StringUtils.firstNonBlank(holidayName, type);
        }
        if (StringUtils.equalsAny(type, "補行上班日")) {
            return "補班日";
        }
        String text = "只紀念不放假";
        if (StringUtils.isNotBlank(holidayName)) {
            text += " (" + holidayName + ")";
        }
        if (StringUtils.isNotBlank(desc)) {
            text += " [" + desc + "]";
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
