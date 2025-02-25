package com.vance.demo.freemarker.method;

import java.util.Date;
import java.util.List;

import com.vance.demo.util.common.DateUtil;
import com.vance.demo.util.common.StringUtil;

import freemarker.template.SimpleDate;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 * FreeMarker 自定義方法類，用於將日期欄位或日期字符串格式化為指定格式的字符串。
 * <p>
 * 該類實現了 {@link TemplateMethodModelEx}，支援將輸入的日期（可以是 FreeMarker 的
 * {@link SimpleDate} 對象或日期字符串）
 * 轉換為指定格式的字符串輸出。根據構造函數中指定的類型（type），可以返回不同格式的日期字符串，例如完整格式、客製化格式或預設的
 * "yyyy-MM-dd"。
 * 若輸入無效，則返回錯誤訊息。
 * </p>
 *
 * @author Vance
 */
public class DateMethod implements TemplateMethodModelEx {
	private String type = null;

	/**
	 * 預設構造函數，初始化一個不指定格式類型的日期處理實例。
	 * <p>
	 * 使用此構造函數創建的實例，在執行 {@link #exec(List)} 時，若未指定其他格式，
	 * 將預設返回 "yyyy-MM-dd" 格式的日期字符串。
	 * </p>
	 */
	public DateMethod() {
		// nothing to do....
	};

	/**
	 * 帶格式類型的構造函數，初始化一個指定格式類型的日期處理實例。
	 * <p>
	 * 使用此構造函數創建的實例，在執行 {@link #exec(List)} 時，會根據指定的類型返回對應格式的日期字符串。
	 * 有效類型包括：
	 * <ul>
	 * <li>"isFull" - 返回完整格式 "yyyy-MM-dd HH:mm:ss"</li>
	 * <li>"isFormat" - 使用第二個參數指定的自訂格式</li>
	 * </ul>
	 * 若類型無效或未指定，則預設返回 "yyyy-MM-dd" 格式。
	 * </p>
	 *
	 * @param type 格式類型，決定日期輸出的格式
	 */
	public DateMethod(String type) {
		this.type = type;
	}

	/**
	 * FreeMarker 自定義方法，將輸入的日期欄位或日期字符串格式化為指定格式的字符串。
	 * <p>
	 * 該方法接受一或兩個參數：第一個參數為日期（可以是 {@link SimpleDate} 或日期字符串），第二個參數（可選）為自訂格式字符串。
	 * 根據類的構造函數中指定的類型（type），返回不同格式的日期字符串。如果未指定類型，預設返回 "yyyy-MM-dd" 格式。
	 * 若輸入為 null，返回空字符串；若處理失敗，返回異常訊息。
	 * </p>
	 *
	 * @param args 包含一或兩個參數的列表：
	 *             <ul>
	 *             <li>args.get(0) - 日期欄位（{@link SimpleDate}）或日期字符串（例如
	 *             "2023-01-01"）</li>
	 *             <li>args.get(1) - （可選）自訂格式字符串（例如 "yyyy/MM/dd"），僅在類型為 "isFormat"
	 *             時使用</li>
	 *             </ul>
	 * @return 格式化後的日期字符串；若輸入為 null 則返回空字符串；若處理失敗則返回錯誤訊息
	 * @throws TemplateModelException 如果參數數量不是 1 或 2
	 */
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
				date = DateUtil.parseDate(StringUtil.trim(args.get(0)));
			}
			switch (this.type) {
				case "isFull":
					return DateUtil.formatDate(date, "yyyy-MM-dd HH:mm:ss");
				case "isFormat":
					return DateUtil.formatDate(date, StringUtil.trim(args.get(1)));
			}
			return DateUtil.formatDate(date, "yyyy-MM-dd");
		} catch (Exception e) {
			// 其他轉換錯誤皆轉成Java錯誤訊息，不然null會造成ftl錯誤
			return e.getMessage();
		}
	}
}
