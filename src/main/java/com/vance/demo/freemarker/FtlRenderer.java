package com.vance.demo.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import com.vance.demo.constant.Constant;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

/**
 * FreeMarker Loader
 * 
 * @author Vance
 */
public class FtlRenderer {

    /** FreeMarker version */
    private static final Version FM_VERSION = Configuration.VERSION_2_3_30;

    /** FTL 檔案根目錄位置 */
    private static final String FTL_ROOT = "/ftl";

    /** FTL 檔案編碼 */
    private static final String FTL_CHARSET = Constant.CharSet.UTF8;

    /** freemarker Configuration */
    private static final Configuration CFG;
    static {
        CFG = new Configuration(FM_VERSION);
        CFG.setClassForTemplateLoading(FtlRenderer.class, FTL_ROOT);
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
     * 私有建構子，防止實例化，此類為靜態工具類
     */
    private FtlRenderer() {
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
     * 處理模板並返回字串
     *
     * @param ftlNm FreeMarker 模板檔案名稱 (FTL 文件)
     * @param data  資料物件，用於模板渲染
     * @return 處理後的字串
     * @throws RuntimeException 如果模板加載或處理失敗
     */
    private static String renderTemplate(String ftlNm, Object data) {
        try (StringWriter out = new StringWriter()) {
            getTemplate(ftlNm).process(data, out);
            return out.toString();
        } catch (Exception e) {
            throw new RuntimeException("Template processing failed for: " + ftlNm, e);
        }
    }
}
