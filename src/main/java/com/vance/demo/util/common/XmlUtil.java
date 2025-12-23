package com.util;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import lombok.extern.slf4j.Slf4j;

/**
 * XML 處理工具類別
 *
 * <pre>
 * 主要功能：
 * 1. 提供 XML 字串與 Document 物件的轉換。
 * 2. 支援「大小寫不敏感」的標籤值提取。
 * 3. 自動處理命名空間 (Namespace) 前綴，透過 local-name() 定位。
 * </pre>
 *
 * @author Vance
 */
@Slf4j
public class XmlUtil {

	/** XPath 工廠實例，執行緒安全 */
	private static final XPathFactory XPATH_FACTORY = XPathFactory.newInstance();

	/** 用於 XPath translate 函式的大寫字母集 */
	private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/** 用於 XPath translate 函式的小寫字母集 */
	private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * 將 XML 字串解析為 W3C Document 物件
	 * <p>
	 * 安全性說明：已啟用特徵標籤防範 XXE (XML External Entity) 攻擊。
	 * </p>
	 *
	 * @param xml 原始 XML 字串
	 * @return 解析後的 Document 物件，若輸入為空白則回傳 null
	 * @throws Exception 當 XML 格式錯誤或解析失敗時拋出
	 */
	public static Document toDocument(String xml) throws Exception {
		if (StringUtils.isBlank(xml)) {
			return null;
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true); // 必須開啟以處理帶有 n: 前綴的標籤
		// [Security Fix] 防範 XXE 攻擊
		try {
			factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
		} catch (Exception e) {
			log.warn("無法設定 XML 安全特徵，請檢查 Parser 版本: {}", e.getMessage());
		}
		return factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * 建構大小寫不敏感且忽略命名空間的 XPath 比對式
	 *
	 * @param tagName      標籤名稱
	 * @param isDeepSearch 是否使用全域搜尋 (//)
	 * @return XPath 運算式
	 */
	private static String buildXPath(String tagName, boolean isDeepSearch) {
		// translate(local-name(), 'UPPER', 'lower') = 'target_lower'
		String predicate = String.format("*[translate(local-name(), '%s', '%s')='%s']", UPPER, LOWER,
				tagName.toLowerCase());
		return (isDeepSearch ? "//" : "") + predicate;
	}

	/**
	 * 獲取單一標籤內容 (全域搜尋)
	 *
	 * @param source  來源節點 (Document, Element 或 Node)
	 * @param tagName 標籤名稱
	 * @return 內容字串
	 */
	public static String getString(Node source, String tagName) {
		if (StringUtils.isBlank(tagName)) {
			return StringUtils.EMPTY;
		}
		try {
			XPath xpath = XPATH_FACTORY.newXPath();
			return CastUtil.cast(xpath.evaluate(buildXPath(tagName, true), source, XPathConstants.STRING));
		} catch (Exception e) {
			log.error("提取標籤 [{}] 失敗", tagName);
			return StringUtils.EMPTY;
		}
	}

	/**
	 * 批次提取標籤內容，回傳不分大小寫取值的 Map
	 *
	 * @param parentTagName 父標籤(若傳 null 或空白，則從 source 直接提取子項)
	 * @param source        來源節點
	 * @param tags          子標籤陣列
	 * @return 支援大小寫不敏感取值的 Map (基於 TreeMap)
	 */
	public static Map<String, String> getTagsValue(String parentTagName, Node source, String... tags) {
		// 使用 TreeMap 並指定 CASE_INSENSITIVE_ORDER 達到不分大小寫取值
		Map<String, String> result = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		if (Objects.isNull(source) || ArrayUtils.isEmpty(tags)) {
			return result;
		}
		try {
			XPath xpath = XPATH_FACTORY.newXPath(); // 在外部建立一次 XPath 物件
			// 1. 定位父節點
			Node parentNode = source;
			if (StringUtils.isNotBlank(parentTagName)) {
				String parentExpr = buildXPath(parentTagName, true);
				parentNode = (Node) xpath.evaluate(parentExpr, source, XPathConstants.NODE);
			}
			if (Objects.nonNull(parentNode)) {
				for (String tag : tags) {
					// 2. 在父節點下執行相對搜尋 (isDeepSearch = false)
					String childExpr = buildXPath(tag, false);
					String value = CastUtil.cast(xpath.evaluate(childExpr, parentNode, XPathConstants.STRING));
					result.put(tag, value);
				}
			} else {
				log.warn("找不到指定的父標籤: {}", parentTagName);
			}
		} catch (Exception e) {
			log.error("批次提取失敗 (Parent: {}): {}", parentTagName, e.getMessage());
		}
		return result;
	}

	/**
	 * 測試進入點：模擬各種標籤情況
	 */
	public static void main(String[] args) {
		try {
			// 1. 準備模擬資料：包含命名空間、不同大小寫、深層結構
			String mockXml = FileUtils.readFileToString(FileUtils.getFile("C:\\Users\\vance.JCS\\Desktop\\aa2.txt"),
					"UTF-8");
			log.info("=== 開始執行 SoapUtil 測試 ===");
			log.info("");
			// 2. 解析 Document
			Document doc = XmlUtil.toDocument(mockXml);
			// 3. 測試單一提取 (測試大小寫不敏感)
			log.info("--- 單一提取測試 ---");
			log.info("搜尋 'unique_key' (全小寫): {}", XmlUtil.getString(doc, "unique_key"));
			log.info("搜尋 'UNIQUE_KEY' (全大寫): {}", XmlUtil.getString(doc, "UNIQUE_KEY"));
			log.info("搜尋 'crR_REfereNce_ID' (大小寫混雜): {}", XmlUtil.getString(doc, "crR_REfereNce_ID"));
			log.info("");
			// 4. 測試批次提取 (測試父子結構)
			log.info("--- 批次提取測試 (Parameters) ---");
			// 這裡刻意混合大小寫傳入 tags 陣列
			Map<String, String> params = XmlUtil.getTagsValue("parameters", doc, "interface_name", "Party_Number",
					"error_code");
			params.forEach((k, v) -> log.info("標籤 [" + k + "] -> 值: " + v));
			log.info("");
			log.info("--- 測試回傳 Map 的大小寫不敏感取值 ---");
			log.info("取值測試 [INTERFACE_NAME]: {}", params.get("INTERFACE_NAME"));
			log.info("取值測試 [party_number]: {}", params.get("party_number"));
			log.info("取值測試 [pArTy_NuMbEr]: {}", params.get("pArTy_NuMbEr"));
			log.info("");
			log.info("Map==>{}", params);
			log.info("");
			log.info("=== 測試完成 ===");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
