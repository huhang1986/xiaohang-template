package com.xiaohang.template.core.render;

import java.io.Writer;
import java.util.Map;

import com.xiaohang.template.core.DefaultTemplateEngine;
import com.xiaohang.template.core.TemplateManager;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.MapContext;

/**
 * @author xiaohanghu
 */
public class RenderContext {
	private TemplateManager templateManager;
	private DefaultTemplateEngine templateEngine;
	private Map<String, Object> attributes;
	private JexlContext jexlContext;
	private Writer writer;

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.jexlContext = new MapContext(attributes);
	}

	public JexlContext getJexlContext() {
		return jexlContext;
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public TemplateManager getTemplateManager() {

		return templateManager;
	}

	public void setTemplateManager(TemplateManager templateManager) {
		this.templateManager = templateManager;
	}

	public DefaultTemplateEngine getTemplateEngine() {
		return templateEngine;
	}

	public void setTemplateEngine(DefaultTemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

}
