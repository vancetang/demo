package com.vance.demo.freemarker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vance.demo.support.Util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * freemarker 是否包含。
 */
public class ContainsMethod implements TemplateMethodModelEx {
	/**
	 * 是否包含
	 */
	public ContainsMethod() {
	};

	/**
	 * 值1是否包含在值2中 or 值2是否包含在值1中
	 * 
	 * @param String 值1
	 * @param String 值2
	 * @return true or false
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 2)
			throw new TemplateModelException("Wrong arguments");
		try {
			String value1 = Util.trim(args.get(0)).toUpperCase();
			String value2 = Util.trim(args.get(1)).toUpperCase();
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
