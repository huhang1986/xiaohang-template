package com.xiaohang.template.core;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;

import org.apache.commons.jexl2.JexlEngine;
import com.xiaohang.template.core.dom.Node;
import com.xiaohang.template.core.parser.DomParser;
import com.xiaohang.template.core.processor.TagProcessorManager;
import com.xiaohang.template.core.render.EmptyRender;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderUtils;
import com.xiaohang.template.core.support.IOUtils;
import com.xiaohang.template.core.template.SimpleTemplate;


/**
 * @author xiaohanghu
 * */
public class EmptyTemplateEngine implements TemplateEngine {

	private TagProcessorManager tagProcessorManager;

	private JexlEngine jexlEngine;

	protected DomParser domParser;

	protected List<Node> parse(Reader reader) {
		List<Node> nodes = domParser.parse(reader);
		return nodes;
	}

	protected Render createRender(Reader reader) {
		List<Node> nodes = parse(reader);
		return createRender(nodes);
	}

	protected Render createRender(List<Node> nodes) {
		return RenderUtils.buildRender(nodes, this);
	}

	public TagProcessorManager getTagProcessorManager() {
		return tagProcessorManager;
	}

	public void setTagProcessorManager(TagProcessorManager tagProcessorManager) {
		this.tagProcessorManager = tagProcessorManager;
	}

	public JexlEngine getJexlEngine() {
		return jexlEngine;
	}

	public void setJexlEngine(JexlEngine jexlEngine) {
		this.jexlEngine = jexlEngine;
	}

	public DomParser getDomParser() {
		return domParser;
	}

	public void setDomParser(DomParser domParser) {
		this.domParser = domParser;
	}

	public Template createTemplate(InputStream inputStream, String encoding) {
		return createTemplate(IOUtils.createrResder(inputStream, encoding));
	}

	public Template createTemplate(String text) {
		return createTemplate(IOUtils.createrResder(text));
	}

	public Template createTemplate(Reader reader) {
		Render render = createRender(reader);
		if (null == render) {
			render = new EmptyRender();
		}
		return new SimpleTemplate(render);
	}
}
