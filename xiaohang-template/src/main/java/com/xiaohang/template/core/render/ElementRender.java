package com.xiaohang.template.core.render;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xiaohang.template.core.TemplateEngine;
import com.xiaohang.template.core.dom.Element;
import com.xiaohang.template.core.dom.Node;
import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;


/**
 * @author xiaohanghu
 * */
public class ElementRender implements Render {

	private Element element = null;
	private TagProcessor processor = null;
	private Render[] children = null;

	private TagPropertys tagPropertys;

	public ElementRender(Element element, TagProcessor processor,
			TemplateEngine templateEngine) {
		this.element = element;
		this.processor = processor;
		children = createChildRenders(element.getChildren(), templateEngine);

		buildPropertyRenders(templateEngine);

	}

	protected void buildPropertyRenders(TemplateEngine templateEngine) {
		Map<String, Object> propertys = element.getPropertys();
		/** key is lowerCase */
		Map<String, Render> propertyRenders = new HashMap<String, Render>();
		for (Entry<String, Object> entry : propertys.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			Render render = null;
			if (value instanceof Node) {
				Node node = (Node) value;
				render = RenderUtils.buildRender(node, templateEngine);
			} else if (value instanceof List) {
				@SuppressWarnings("unchecked")
				List<Node> nodes = (List<Node>) value;
				render = RenderUtils.buildRender(nodes, templateEngine);
			}
			key = key.toLowerCase();
			propertyRenders.put(key, render);
		}
		tagPropertys = this.processor.parseTagPropertys(propertyRenders);
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public TagPropertys getTagPropertys() {
		return tagPropertys;
	}

	@Override
	public void render(RenderContext renderContext) {
		try {
			doRender(renderContext);
		} catch (IOException e) {
			throw new RenderException("Render element [" + element.toString()
					+ "] throw exception!", e);
		}
	}

	public void doRender(RenderContext renderContext) throws IOException {
		processor.process(renderContext, tagPropertys, children);
	}

	protected Render[] createChildRenders(List<Node> children,
			TemplateEngine templateEngine) {
		Render[] renders = RenderUtils.buildRendersArray(children,
				templateEngine);
		return renders;
	}

}
