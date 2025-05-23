package com.vance.demo.freemarker.method;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vance.demo.util.common.Util;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;
import lombok.extern.slf4j.Slf4j;

/**
 * FreeMarker 自定義方法類，用於記錄輸入對象的屬性信息並返回空字符串。
 * <p>
 * 該類實現了
 * {@link TemplateMethodModelEx}，提供一個方法來處理單一輸入對象，記錄其類型信息（例如類名）以及大小（如果對象是序列）。
 * 主要用於調試或測試目的，執行後始終返回空字符串。若處理失敗，則返回異常訊息。
 * </p>
 *
 * @author Vance
 */
@Slf4j
public class VanceMethod implements TemplateMethodModelEx {
	/**
	 * FreeMarker 自定義方法，記錄輸入對象的屬性信息並返回空字符串。
	 * <p>
	 * 該方法接受單一參數，檢查其類型並記錄相關信息（例如類名、類型名稱）。若輸入是 {@link TemplateSequenceModel}，
	 * 則額外記錄其大小。方法主要用於調試目的，執行後返回空字符串。若處理過程中發生異常，則返回錯誤訊息。
	 * </p>
	 *
	 * @param args 包含單一參數的列表：
	 *             <ul>
	 *             <li>args.get(0) - 需要記錄屬性的對象，可以是 {@link TemplateSequenceModel}
	 *             或其他類型</li>
	 *             </ul>
	 * @return 空字符串 ({@link StringUtils#EMPTY})；若處理失敗則返回錯誤訊息
	 * @throws TemplateModelException 如果參數數量不為 1
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if (args.size() != 1) {
			throw new TemplateModelException("Wrong arguments");
		}
		try {
			Object obj = args.get(0);
			if (args.get(0) instanceof TemplateSequenceModel) {
				TemplateSequenceModel seq = Util.cast(args.get(0));
				log.debug("{}", seq.size());
				if (seq.size() == 0) {
					log.debug("空的");
				}
			}
			log.debug("{}", obj.getClass().getName());
			log.debug("{}", obj.getClass().getSimpleName());
			log.debug("{}", obj.getClass().getTypeName());
			return StringUtils.EMPTY;
		} catch (Exception e) {
			// 故意回傳錯誤訊息讓前端看到
			return e.getMessage();
		}
	}
}
