package com.vance.demo.util.freemarker.method;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import com.vance.demo.util.Util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * freemarker escapeXml (StringEscapeUtils.escapeXml11)
 */
public class EscapeXmlMethod implements TemplateMethodModelEx {
	/**
	 * 去null&前後空白
	 */
	public EscapeXmlMethod() {
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
			String result = StringEscapeUtils.escapeXml11(Util.trim(args.get(0)));
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
