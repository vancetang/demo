package com.vance.demo.freemarker.method;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vance.demo.util.common.StringUtil;
import com.vance.demo.util.common.Util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * FreeMarker 自定義方法類，用於檢查兩個字符串之間的包含關係。
 * <p>
 * 該類實現了 {@link TemplateMethodModelEx}，提供一個方法來判斷第一個字符串是否包含第二個字符串，
 * 或第二個字符串是否包含第一個字符串。處理過程中會去除字符串前後空白並轉為大寫進行比較，
 * 支持處理 null 或空值的情況。
 * </p>
 *
 * @author Vance
 */
public class ContainsMethod implements TemplateMethodModelEx {
	/**
	 * FreeMarker 自定義方法，檢查第一個字符串是否包含第二個字符串，或第二個字符串是否包含第一個字符串。
	 * <p>
	 * 該方法會將輸入的兩個字符串去除前後空白並轉為大寫後進行比較。如果任一字符串為 null 或空，則根據具體情況返回結果。
	 * 若任一輸入無法處理（例如非字符串對象），則返回 null。
	 * </p>
	 *
	 * @param args 包含兩個參數的列表：
	 *             <ul>
	 *             <li>args.get(0) - 第一個字符串值</li>
	 *             <li>args.get(1) - 第二個字符串值</li>
	 *             </ul>
	 * @return {@code true} 如果兩個字符串相等，或其中一個包含另一個；{@code false} 如果不相等且互不包含；
	 *         {@code null} 如果處理過程中發生異常（例如輸入無法轉換為字符串）
	 * @throws TemplateModelException 如果參數數量不為 2
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 2) {
			throw new TemplateModelException("Wrong arguments");
		}
		try {
			String value1 = StringUtil.trim(args.get(0)).toUpperCase();
			String value2 = StringUtil.trim(args.get(1)).toUpperCase();
			if (StringUtils.equals(value1, value2)) {
				return true;
			}
			// freemarker exception string
			if (Util.isFreemarkerEmptyString(value1) || Util.isFreemarkerEmptyString(value2)) {
				return false;
			}
			if (!StringUtils.equals(StringUtils.EMPTY, value2)) {
				if (value1.contains(value2)) {
					return true;
				}
			}
			if (!StringUtils.equals(StringUtils.EMPTY, value1)) {
				if (value2.contains(value1)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			// 其他轉換錯誤皆轉成null
			return null;
		}
	}
}
