package com.xiaohang.template.core.render;

import java.util.LinkedList;
import java.util.List;

import com.xiaohang.template.core.TemplateEngine;
import com.xiaohang.template.core.dom.Element;
import com.xiaohang.template.core.dom.Expression;
import com.xiaohang.template.core.dom.Node;
import com.xiaohang.template.core.dom.Text;
import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagProcessorManager;
import com.xiaohang.template.core.processor.TagProcessorUtils;
import com.xiaohang.template.core.support.StringWriter;


/**
 * @author xiaohanghu
 * */
public class RenderUtils {

	public static Render createRenderCluster(List<Render> renders) {
		return new RenderCluster(renders);
	}

	public static Render buildRender(Node node, TemplateEngine templateEngine) {

		if (node instanceof Text) {
			Render textRender = new TextRender((Text) node);
			return textRender;
		}
		if (node instanceof Expression) {
			ExpressionRender expressionrRender = new ExpressionRender(
					(Expression) node, templateEngine);
			return expressionrRender;
		}
		if (node instanceof Element) {
			Element element = (Element) node;
			String elementName = element.getName();
			TagProcessorManager processorManager = templateEngine
					.getTagProcessorManager();
			TagProcessor processor = processorManager.getProcessor(elementName);
			if (null == processor) {// set default processor
				processor = TagProcessorUtils.getDefaultProcessor();
			}
			ElementRender elementRender = new ElementRender(element, processor,
					templateEngine);
			return elementRender;
		}

		return null;
	}

	/**
	 * */
	public static List<Render> buildRenders(List<Node> nodes,
			TemplateEngine templateEngine) {
		if (null == nodes) {
			return null;
		}
		List<Render> renders = new LinkedList<Render>();
		for (Node node : nodes) {
			Render render = buildRender(node, templateEngine);
			if (null != render) {
				renders.add(render);
			}
		}
		return renders;
	}

	/**
	 * */
	public static Render[] buildRendersArray(List<Node> nodes,
			TemplateEngine templateEngine) {
		List<Render> renders = buildRenders(nodes, templateEngine);
		if (null == renders || renders.isEmpty()) {
			return null;
		}
		return renders.toArray(new Render[renders.size()]);
	}

	/**
	 * */
	public static Render buildRender(List<Node> nodes,
			TemplateEngine templateEngine) {
		if (null == nodes || nodes.isEmpty()) {
			return null;
		}
		List<Render> renders = buildRenders(nodes, templateEngine);
		if (renders.size() == 1) {
			return renders.get(0);
		}
		return createRenderCluster(renders);
	}

	public static void render(List<Render> renders, RenderContext renderContext) {
		if (null == renders) {
			return;
		}
		for (Render render : renders) {
			render.render(renderContext);
		}
	}

	public static void render(Render[] renders, RenderContext renderContext) {
		if (null == renders) {
			return;
		}
		for (Render render : renders) {
			render.render(renderContext);
		}
	}

	public static Object getRenderResultObject(Render render,
			RenderContext renderContext) {

		if (null == render) {
			return null;
		}
		if (render instanceof TextRender) {
			TextRender textRender = (TextRender) render;
			return textRender.getText().getContent();
		}
		if (render instanceof ExpressionRender) {
			ExpressionRender expressionrRender = (ExpressionRender) render;
			Object value = expressionrRender.evaluate(renderContext);
			if (null == value) {
				return null;
			}
			return value;
		}

		StringWriter stringWriter = new StringWriter();

		RenderContext currentContext = new RenderContext();
		currentContext.setAttributes(renderContext.getAttributes());
		currentContext.setTemplateEngine(renderContext.getTemplateEngine());
		currentContext.setTemplateManager(renderContext.getTemplateManager());
		currentContext.setWriter(stringWriter);

		render.render(currentContext);

		String result = stringWriter.getBuffer().toString();

		if (result.length() == 0) {
			return null;
		}

		return stringWriter.getBuffer().toString();
	}

	public static String getRenderResult(Render render,
			RenderContext renderContext) {

		Object result = getRenderResultObject(render, renderContext);
		if (null == result) {
			return null;
		}
		return result.toString();
	}

}
