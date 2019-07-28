package com.xiaohang.template.core;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

/**
 * @author xiaohanghu
 */
public class ConcurrentTemplateManager implements TemplateManager {

	private ConcurrentHashMap<String, Template> templates = new ConcurrentHashMap<String, Template>();

	/**
	 * 模板名称不容许重复。
	 */
	@Override
	public void addTemplate(String name, Template template) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException(
					"Template name must not be blank!");
		}
		if (null == template) {
			throw new IllegalArgumentException("Template must not be null!");
		}

		// name = name.toLowerCase();

		if (templates.containsKey(name)) {
			throw new IllegalArgumentException("Duplicate template name ["
					+ name + "] .");
		}

		if (template instanceof TemplateManagerSetter) {
			((TemplateManagerSetter) template).setTemplateManager(this);
		}
		templates.putIfAbsent(name, template);

	}

	@Override
	public void deleteTemplate(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException(
					"Template name must not be blank!");
		}
		// name = name.toLowerCase();
		templates.remove(name);

	}

	@Override
	public Template getTemplate(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException(name
					+ " template  must not be blank!");
		}
		// name = name.toLowerCase();
		return templates.get(name);
	}

	@Override
	public void updateTemplate(String name, Template template) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException(
					"Template name must not be blank!");
		}
		if (null == template) {
			throw new IllegalArgumentException("Template must not be null!");
		}

		// name = name.toLowerCase();

		if (template instanceof TemplateManagerSetter) {
			((TemplateManagerSetter) template).setTemplateManager(this);
		}
		templates.put(name, template);
	}

	@Override
	public String toString() {
		return templates.toString();
	}

}
