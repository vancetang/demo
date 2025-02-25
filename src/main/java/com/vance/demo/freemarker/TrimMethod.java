package com.vance.demo.freemarker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vance.demo.support.Util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * freemarker 去null&前後空白。
 */
public class TrimMethod implements TemplateMethodModelEx {
	/**
	 * 去null&前後空白
	 */
	public TrimMethod() {
	};

	/**
	 * @param String 值
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		try {
			String result = Util.trim(args.get(0));
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
