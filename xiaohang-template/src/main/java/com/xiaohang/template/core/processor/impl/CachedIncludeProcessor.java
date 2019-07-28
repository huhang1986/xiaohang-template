package com.xiaohang.template.core.processor.impl;

import java.util.Map;

import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateManager;
import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderUtils;
import com.xiaohang.template.core.render.TextRender;
import org.apache.commons.lang.StringUtils;

/**
 * @author xiaohanghu
 */
public class CachedIncludeProcessor implements TagProcessor {

	public static final String PROPERTY_TEMPLATE_NAME = "templatename";

	@Override
	public TagPropertys parseTagPropertys(Map<String, Render> propertyRenders) {
		CachedIncludePropertys propertys = new CachedIncludePropertys();
		propertys.setTemplatename(propertyRenders.get(PROPERTY_TEMPLATE_NAME));

		return propertys;
	}

	@Override
	public void process(RenderContext renderContext, TagPropertys tagPropertys,
                        Render[] chindren) {
		CachedIncludePropertys propertys = (CachedIncludePropertys) tagPropertys;

		Render render = propertys.getTemplatename();
		Template template = null;

		TemplateManager templateManager = renderContext.getTemplateManager();
		if (render instanceof TextRender) {
			template = getTemplate((TextRender) render, propertys,
					templateManager);
		} else {
			String templateName = RenderUtils.getRenderResult(
					propertys.getTemplatename(), renderContext);

			template = getTemplateNotNUll(templateName, templateManager);
		}

		template.render(renderContext.getAttributes(),
				renderContext.getWriter());
	}

	private Template getTemplateNotNUll(String templateName,
			TemplateManager templateManager) {
		if (StringUtils.isBlank(templateName)) {
			throw new IllegalStateException("Template name must not be null!"
					+ templateName);
		}
		Template template = templateManager.getTemplate(templateName);
		if (template == null) {
			throw new IllegalStateException("Can't find include template:  "
					+ templateName);
		}
		return template;
	}

	private Template getTemplate(TextRender templatenameRender,
			CachedIncludePropertys propertys, TemplateManager templateManager) {
		Template template = propertys.template;
		if (null == template) {
			synchronized (propertys) { // 1
				template = propertys.template; // 2
				if (template == null) {
					String templateName = templatenameRender.getText()
							.getContent();
					template = getTemplateNotNUll(templateName, templateManager);
					propertys.template = template;
				}
			}
		}
		return template;
	}

}

class CachedIncludePropertys implements TagPropertys {

	private Render templatename;

	Template template;

	public Render getTemplatename() {
		return templatename;
	}

	public void setTemplatename(Render templatename) {
		this.templatename = templatename;
	}

}