package com.vance.demo.util.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.vance.demo.constant.Constant;
import com.vance.demo.freemarker.method.ContainsMethod;
import com.vance.demo.freemarker.method.DateMethod;
import com.vance.demo.freemarker.method.EscapeXmlMethod;
import com.vance.demo.freemarker.method.TrimMethod;
import com.vance.demo.freemarker.method.VanceMethod;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/**
 * FreeMarker 模板工具類，提供與 FreeMarker 模板相關的操作。
 * <p>
 * 該類是一個靜態工具類，通過靜態方法提供 FreeMarker 模板的處理功能，例如模板渲染。
 * 初始化時配置了 FreeMarker 的環境（版本、模板路徑、編碼等），並註冊了多個自定義方法（例如日期格式化、字符串處理等）。
 * 支持將模板與數據對象結合，生成字符串輸出，並可選擇是否移除換行符號。若模板加載或處理失敗，會拋出運行時異常。
 * </p>
 *
 * @author Vance
 */
public class FtlUtil {

    /** FreeMarker version */
    private static final Version FM_VERSION = Configuration.VERSION_2_3_34;

    /** FTL 檔案根目錄位置 */
    private static final String FTL_ROOT = "/ftl";

    /** FTL 檔案編碼 */
    private static final String FTL_CHARSET = Constant.CharSet.UTF8;

    /** freemarker Configuration */
    private static final Configuration CFG;
    static {
        CFG = new Configuration(FM_VERSION);
        CFG.setClassForTemplateLoading(FtlUtil.class, FTL_ROOT);
        CFG.setDefaultEncoding(FTL_CHARSET);
        // 預設數值資料:不做任何處理，原欲設為number，會自動加上comma
        CFG.setNumberFormat("computer");
        // 每 60 秒檢查模板更新
        CFG.setTemplateUpdateDelayMilliseconds(TimeUnit.SECONDS.toMillis(60));
        // Date related methods =========================================
        CFG.setSharedVariable("t_date", new DateMethod());
        CFG.setSharedVariable("t_fulldate", new DateMethod("isFull"));
        CFG.setSharedVariable("t_formatdate", new DateMethod("isFormat"));
        // String utility methods =======================================
        // 去空白method
        CFG.setSharedVariable("t_trim", new TrimMethod());
        // 是否包含
        CFG.setSharedVariable("t_contains", new ContainsMethod());
        // XML的escape tool
        CFG.setSharedVariable("t_escapeXml", new EscapeXmlMethod());
        // Custom methods
        CFG.setSharedVariable("t_vance", new VanceMethod());
    }

    /**
     * 私有構造函數，防止實例化。
     * <p>
     * 此類設計為靜態工具類，所有功能通過靜態方法提供，因此禁止外部實例化。
     * </p>
     */
    private FtlUtil() {
        // Prevent instantiation
    }

    /**
     * 取得模板處理後的字串（預設移除換行符號）
     * 
     * @param ftlNm FreeMarker 模板檔案名稱 (FTL 文件)
     * @param data  資料物件，用於模板渲染
     * @return 處理後的字串
     */
    public static String getString(String ftlNm, Object data) {
        return getString(ftlNm, data, true);
    }

    /**
     * 取得模板處理後的字串
     * 
     * @param ftlNm  FreeMarker 模板檔案名稱 (FTL 文件)
     * @param data   資料物件，用於模板渲染
     * @param remove 是否移除換行符號
     * @return 處理後的字串
     */
    public static String getString(String ftlNm, Object data, boolean remove) {
        String str = renderTemplate(ftlNm, data);
        if (remove) {
            return str.replaceAll("\\r\\n", "");
        }
        return str;
    }

    /**
     * 獲取 FreeMarker 模板物件
     *
     * @param ftlNm FreeMarker 模板檔案名稱 (FTL 文件)
     * @return FreeMarker 模板物件
     * @throws RuntimeException 如果模板加載失敗
     */
    private static Template getTemplate(String ftlNm) {
        try {
            return CFG.getTemplate(ftlNm);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load template: " + ftlNm, e);
        }
    }

    /**
     * 處理模板並返回字串。
     * <p>
     * 該方法將給定的 FreeMarker 模板檔案與資料物件結合，生成渲染後的字串。
     * 資料物件可以是 {@link Map}、JavaBean 或 {@code null}。
     * </p>
     *
     * @param ftlNm FreeMarker 模板檔案名稱 (FTL 文件)
     * @param data  資料物件，用於模板渲染，可以是 {@link Map}、JavaBean 或 {@code null}
     * @return 渲染後的字串
     * @throws RuntimeException 如果模板加載或處理失敗，或資料型態無效（不是 {@link Map}、JavaBean 或
     *                          {@code null}）
     */
    private static String renderTemplate(String ftlNm, Object data) {
        if (!isValidDataModel(data)) {
            throw new RuntimeException("資料型態只能是 Map or JavaBean: " + data.getClass().getName());
        }
        try (StringWriter out = new StringWriter()) {
            getTemplate(ftlNm).process(data, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Template processing failed for: " + ftlNm, e);
        }
    }

    /**
     * 檢查給定的物件是否為有效的 FreeMarker 資料模型。
     * <p>
     * 該方法驗證資料物件是否可以作為 FreeMarker 的資料模型，包括 {@link Map}、JavaBean 或 {@code null}。
     * </p>
     *
     * @param data 要檢查的資料物件
     * @return {@code true} 如果物件是 {@link Map}、JavaBean 或 {@code null}，否則返回
     *         {@code false}
     */
    private static boolean isValidDataModel(Object data) {
        if (Objects.isNull(data)) {
            return true; // null 值也算有效
        }
        // 檢查是否為 Map
        if (data instanceof Map) {
            return true;
        }
        // 排除基本型別、包裝類別、String 等非 JavaBean 的類型
        Class<?> clazz = data.getClass();
        return !clazz.isPrimitive() && // 不是基本型別
                !(data instanceof String) && // 不是 String
                !(data instanceof Number) && // 不是 Number（如 Integer, BigDecimal）
                !(data instanceof Boolean) && // 不是 Boolean
                !(data instanceof Character); // 不是 Character
    }
}
