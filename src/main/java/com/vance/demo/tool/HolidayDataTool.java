package com.vance.demo.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Date;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.input.BOMInputStream;
import org.apache.commons.lang3.StringUtils;

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
    private final String DATASET_RID = "964e936d-d971-4567-a467-aa67b930f98e";

    /** 資料來源 */
    private final String FILE_URL = "https://data.taipei/api/frontstage/tpeod/dataset/resource.download?rid="
            + DATASET_RID;

    /**
     * CSV欄位名稱
     */
    interface CsvCol {
        String 日期 = "Date";

        String 名稱 = "name";

        String 是否放假 = "isHoliday";

        String 類別 = "holidayCategory";

        String 說明 = "description";
    }

    /**
     * 主程式
     * 
     * @param args
     */
    public static void main(String[] args) {
        log.info("=========== 解析北市政府行政機關辦公日曆開始 ===========");
        try {
            new HolidayDataTool().execute();
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        log.info("=========== 解析北市政府行政機關辦公日曆結束 ===========");
    }

    /**
     * 執行
     */
    public void execute() {
        log.info("● 資料來源:{}", FILE_URL);
        log.info("---------------------------------------------------");
        try (InputStream inputStream = download(FILE_URL);
                BOMInputStream bomIn = BOMInputStream.builder()
                        .setInputStream(inputStream)
                        .setInclude(false)
                        .get();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(bomIn, Constants.charset.UTF8))) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader() // 設置自動解析表頭
                    .setTrim(true) // 啟用去除空白
                    .setSkipHeaderRecord(true) // 跳過表頭行
                    .get();

            CSVParser parser = csvFormat.parse(reader);
            parser.getRecords().forEach(record -> {
                Date date = DateUtil.parseDate(record.get(CsvCol.日期));
                String isHoliday = record.get(CsvCol.是否放假);
                String holidayName = record.get(CsvCol.名稱);
                String type = record.get(CsvCol.類別);
                String desc = record.get(CsvCol.說明);
                String text = null;
                if (StringUtils.equals("是", isHoliday)) {
                    if (StringUtils.equalsAny(type, "星期六、星期日", "星期日")) {
                        text = "周休";
                    } else if (DateUtil.isWeekoff(date)) {
                        text = "周休";
                        String tmp = StringUtils.firstNonBlank(holidayName, type);
                        if (StringUtils.isNotBlank(tmp)) {
                            text = StringUtils.join(text, " (", tmp, ")");
                        }
                    } else {
                        text = StringUtils.firstNonBlank(holidayName, type);
                    }
                } else {
                    if (StringUtils.equalsAny(type, "補行上班日")) {
                        text = "補班日";
                    } else {
                        text = "只紀念不放假";
                        if (StringUtils.isNotBlank(holidayName)) {
                            text = StringUtils.join(text, "(", holidayName, ")");
                        }
                    }
                }
                if (StringUtils.isNotBlank(desc)) {
                    text = StringUtils.join(text, "[", desc, "]");
                }
                log.info("{}({}) ={};{}", DateUtil.formatDate(date, "yyyy-MM-dd"),
                        DateUtil.toWeek(date),
                        isHoliday, text);
            });
        } catch (Exception e) {
            throw new RuntimeException("解析 CSV 失敗", e);
        }
    }

    /**
     * 從 URL 獲取 InputStream
     * 
     * @param fileUrl 檔案網址路徑
     * @return InputStream
     */
    private InputStream download(String fileUrl) {
        try {
            InputStream inputStream = new URI(fileUrl).toURL().openStream();
            log.info("成功獲取資料流:{}", fileUrl);
            return inputStream;
        } catch (Exception e) {
            log.error("獲取資料流失敗:{}", e.getMessage());
            throw new RuntimeException("無法下載檔案", e);
        }
    }
}
