package com.vance.demo.freemarker;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.util.CastUtils;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateSequenceModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VanceMethod implements TemplateMethodModelEx {
	public VanceMethod() {
	};

	/**
	 * @param Object list
	 * @param Number colspan size
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
				TemplateSequenceModel seq = CastUtils.cast(args.get(0));
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
