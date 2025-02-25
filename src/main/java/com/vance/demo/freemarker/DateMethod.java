package com.vance.demo.freemarker;

import java.util.Date;
import java.util.List;

import com.vance.demo.support.DateUtil;
import com.vance.demo.support.Util;

import freemarker.template.SimpleDate;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * freemarker 使用之西元年，傳入參數為日期欄位，或者是日期文字串。
 */
public class DateMethod implements TemplateMethodModelEx {
	private String type = null;

	public DateMethod() {
	};

	/**
	 * @param type 類型
	 */
	public DateMethod(String type) {
		this.type = type;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		// 可傳入日期欄位，或者是日期文字串
		if (args.size() != 1 && args.size() != 2) {
			throw new TemplateModelException("Wrong arguments");
		}
		try {
			if (args.get(0) == null) {
				return "";
			}
			Date date = null;
			// freemarker 自己的日期物件
			if (args.get(0) instanceof SimpleDate) {
				date = ((SimpleDate) args.get(0)).getAsDate();
			} else {
				date = DateUtil.parseDate(Util.trim(args.get(0)));
			}
			switch (this.type) {
				case "isFull":
					return DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				case "isFormat":
					return DateUtil.formatDate(date, Util.trim(args.get(1)));
			}
			return DateUtil.formatDate(date, "yyyy-MM-dd");
		} catch (Exception e) {
			// 其他轉換錯誤皆轉成Java錯誤訊息，不然null會造成ftl錯誤
			return e.getMessage();
		}
	}
}
