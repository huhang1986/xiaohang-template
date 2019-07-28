package com.xiaohang.template.core.processor.impl;

import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagProcessorManager;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaohanghu
 */
public class DefaultTagProcessorManager implements TagProcessorManager {

	private Map<String, TagProcessor> processors = new HashMap<String, TagProcessor>();

	@Override
	public void addProcessor(String name, TagProcessor processor) {
		name = StringUtils.trim(name);
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException(
					"processor name must not be blank!");
		}
		if (processor == null) {
			throw new IllegalArgumentException("processor is must not be null!");
		}
		name = name.toLowerCase();
		if (processors.containsKey(name)) {
			throw new IllegalArgumentException("Duplicate processor name ["
					+ name + "] .");
		}
		processors.put(name, processor);
	}

	@Override
	public TagProcessor getProcessor(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException(
					"processor name must not be blank!");
		}
		name = name.toLowerCase();
		return processors.get(name);
	}

}
