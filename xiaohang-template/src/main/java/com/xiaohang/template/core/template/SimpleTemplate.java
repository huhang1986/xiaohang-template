package com.xiaohang.template.core.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateManager;
import com.xiaohang.template.core.TemplateManagerSetter;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderException;


/**
 * @author xiaohanghu
 */
public class SimpleTemplate implements Template, TemplateManagerSetter {

	private Render render = null;

	private TemplateManager templateManager;

	public SimpleTemplate(Render render) {
		this.render = render;
	}

	public TemplateManager getTemplateManager() {
		return templateManager;
	}

	public void setTemplateManager(TemplateManager templateManager) {
		this.templateManager = templateManager;
	}

	@Override
	public void render(Map<String, Object> attributes, Writer writer) {
		RenderContext renderContext = new RenderContext();
		renderContext.setTemplateManager(templateManager);
		renderContext.setWriter(writer);
		renderContext.setAttributes(attributes);

		render.render(renderContext);

		try {
			writer.flush();
		} catch (IOException e) {
			throw new RenderException("Writer flush throw IOException!", e);
		}

	}

}
