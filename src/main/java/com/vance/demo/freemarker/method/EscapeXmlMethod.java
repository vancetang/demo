package com.vance.demo.freemarker.method;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import com.vance.demo.util.common.StringUtil;
import com.vance.demo.util.common.Util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * FreeMarker 自定義方法類，用於將字符串進行 XML 轉義處理。
 * <p>
 * 該類實現了 {@link TemplateMethodModelEx}，使用 Apache Commons Text 的
 * {@link StringEscapeUtils#escapeXml11(String)}
 * 將輸入字符串轉換為 XML 安全的格式（例如轉義特殊字符如 &lt;、&gt; 等）。處理過程中會去除字符串前後空白，若結果為空則返回空字符串。
 * 若轉換失敗，則返回異常訊息。
 * </p>
 *
 * @author Vance
 */
public class EscapeXmlMethod implements TemplateMethodModelEx {
	/**
	 * FreeMarker 自定義方法，將輸入字符串進行 XML 轉義處理。
	 * <p>
	 * 該方法接受單一字符串參數，經過去除前後空白後，使用 {@link StringEscapeUtils#escapeXml11(String)} 進行
	 * XML 轉義。
	 * 若輸入為 null 或轉義後結果為空，則返回空字符串；若處理過程中發生異常，則返回錯誤訊息。
	 * </p>
	 *
	 * @param args 包含單一參數的列表：
	 *             <ul>
	 *             <li>args.get(0) - 需要進行 XML 轉義的字符串</li>
	 *             </ul>
	 * @return XML 轉義後的字符串；若輸入為 null 或結果為空則返回空字符串；若處理失敗則返回錯誤訊息
	 * @throws TemplateModelException 如果參數數量不為 1
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		try {
			String result = StringEscapeUtils.escapeXml11(StringUtil.trim(args.get(0)));
			if (Util.isFreemarkerEmptyString(result)) {
				result = StringUtils.EMPTY;
			}
			return result;
		} catch (Exception e) {
			// 故意回傳錯誤訊息讓前端看到
			return e.getMessage();
		}
	}
}
