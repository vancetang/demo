package com.vance.demo.tool;

import java.io.File;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.DefaultFileComparator;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import com.vance.demo.util.Util;

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

    /** 存檔目錄 */
    private final String FILE_DIR = StringUtils
            .join(new String[] { SystemUtils.getUserDir().getAbsolutePath(), "src\\main\\resources\\data\\北市" }, "\\");

    /** 檔名前綴檔名 */
    private final String FILE_PREFIX_NAME = "北市政府行政機關辦公日曆表";

    /** 檔案編碼 */
    private final String FILE_ENCODING = "UTF8";

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

    public static void main(String[] args) {
        log.info("=========== 解析北市政府行政機關辦公日曆開始 ===========");
        try {
            new HolidayDataTool().execute();
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        log.info("=========== 解析北市政府行政機關辦公日曆結束 ===========");
    }

    public void execute() {
        // TODO 待完成
    }

    /**
     * 下載檔案
     * 
     * @param fileUrl  檔案網址路徑
     * @param destFile 目標檔案路徑
     * @throws Exception
     */
    private void download(String fileUrl, File destFile) throws Exception {
        // Download the file
        FileUtils.copyURLToFile(new URI(fileUrl).toURL(), destFile);
        log.info("檔案下載成功:{}", destFile.getAbsolutePath());
    }

    /**
     * 依檔案名稱排列取得最新一筆
     * 
     * @param prefix 前綴字(DATASET_OID)
     * @return
     */
    private File getFileOrderNameDesc(String prefix) {
        IOFileFilter filter = null;
        if (StringUtils.isNotBlank(prefix)) {
            filter = FileFilterUtils.prefixFileFilter(prefix);
        } else {
            filter = FileFilterUtils.fileFileFilter();
        }
        List<File> files = (List<File>) FileUtils.listFiles(FileUtils.getFile(FILE_DIR), filter,
                DirectoryFileFilter.DIRECTORY);
        // 依檔名排序(DEFAULT_COMPARATOR);依名稱反向排序(DEFAULT_REVERSE)
        Collections.sort(files, DefaultFileComparator.DEFAULT_REVERSE);
        for (File file : Util.emptyIfNull(files)) {
            // 僅會有一筆資料
            return file;
        }
        return null;
    }
}
