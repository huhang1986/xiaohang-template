package com.xiaohang.template.core.processor.impl;

import java.util.Map;

import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateManager;
import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderException;
import com.xiaohang.template.core.render.RenderUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author xiaohanghu
 */
public class IncludeProcessor implements TagProcessor {

	public static final String PROPERTY_TEMPLATE_NAME = "templatename";

	@Override
	public TagPropertys parseTagPropertys(Map<String, Render> propertyRenders) {
		IncludePropertys propertys = new IncludePropertys();
		propertys.setTemplatename(propertyRenders.get(PROPERTY_TEMPLATE_NAME));

		return propertys;
	}

	@Override
	public void process(RenderContext renderContext, TagPropertys tagPropertys,
                        Render[] chindren) {
		IncludePropertys propertys = (IncludePropertys) tagPropertys;

		Render render = propertys.getTemplatename();
		Template template = null;
		String templateName = RenderUtils
				.getRenderResult(render, renderContext);

		TemplateManager templateManager = renderContext.getTemplateManager();
		template = getTemplateNotNUll(templateName, templateManager);

		template.render(renderContext.getAttributes(),
				renderContext.getWriter());
	}

	private Template getTemplateNotNUll(String templateName,
			TemplateManager templateManager) {
		if (StringUtils.isBlank(templateName)) {
			throw new RenderException("Template name must not be null!"
					+ templateName);
		}
		Template template = templateManager.getTemplate(templateName);
		if (template == null) {
			throw new RenderException("Can't find include template:  "
					+ templateName);
		}
		return template;
	}

}

class IncludePropertys implements TagPropertys {

	private Render templatename;

	public Render getTemplatename() {
		return templatename;
	}

	public void setTemplatename(Render templatename) {
		this.templatename = templatename;
	}

}