package com.xiaohang.template.core;

/**
 * Template manager
 * 
 * @author xiaohanghu
 */
public interface TemplateManager {

	/**
	 * @param name
	 * @param template
	 */
	void addTemplate(String name, Template template);

	/**
	 * @param name
	 */
	void deleteTemplate(String name);

	/**
	 * 
	 * @param name
	 * @param template
	 *            动态更新
	 * @return
	 */
	void updateTemplate(String name, Template template);

	/**
	 * @param name
	 */
	Template getTemplate(String name);
}
