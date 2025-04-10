package com.vance.demo.freemarker.method;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vance.demo.util.common.StringUtil;
import com.vance.demo.util.common.Util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * FreeMarker 自定義方法，將輸入字符串去除前後空白並處理 null 值。
 * 
 * @author Vance
 */
public class TrimMethod implements TemplateMethodModelEx {
	/**
	 * FreeMarker 自定義方法，將輸入字符串去除前後空白並處理 null 值
	 * 
	 * @param args 單一參數，預期為字符串
	 * @return 處理後的字符串，若輸入為 null 或空，則返回空字符串("")
	 * @throws TemplateModelException 如果參數數量不正確
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		try {
			String result = StringUtil.trim(args.get(0));
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
