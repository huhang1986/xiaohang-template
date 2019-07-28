package com.xiaohang.template.core;

import java.io.InputStream;
import java.io.Reader;

import org.apache.commons.jexl2.JexlEngine;
import com.xiaohang.template.core.processor.TagProcessorManager;

/**
 * TemplateEngine 使用各组件，生产模板
 * 
 * @author xiaohanghu
 * 
 * @date: 2011-12-29
 */
public interface TemplateEngine {

	/**
	 * create template
	 * 
	 * @param inputStream
	 * @param encoding
	 * @return Template
	 * */
	Template createTemplate(InputStream inputStream, String encoding);

	/**
	 * create template
	 * 
	 * @param text
	 * @return Template
	 * */
	Template createTemplate(String text);

	/**
	 * create template
	 * 
	 * @param reader
	 * @return Template
	 * */
	Template createTemplate(Reader reader);

	/**
	 * get jexlEngine
	 * 
	 * @return JexlEngine
	 * */
	JexlEngine getJexlEngine();

	/**
	 * get tagProcessorManager
	 * 
	 * @return TagProcessorManager
	 * */
	TagProcessorManager getTagProcessorManager();

}
