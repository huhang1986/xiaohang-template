package com.xiaohang.template.core;

import org.apache.commons.jexl2.JexlEngine;
import com.xiaohang.template.core.parser.XmlDomParser;
import com.xiaohang.template.core.processor.SupportedTagName;
import com.xiaohang.template.core.processor.TagProcessorManager;
import com.xiaohang.template.core.processor.impl.DefaultTagProcessorManager;
import com.xiaohang.template.core.processor.impl.ElseIfProcessor;
import com.xiaohang.template.core.processor.impl.ForEachProcessor;
import com.xiaohang.template.core.processor.impl.IfProcessor;
import com.xiaohang.template.core.processor.impl.IncludeProcessor;
import com.xiaohang.template.core.processor.impl.SetProcessor;

/**
 * TemplateEngine 使用各组件，生产模板
 * 
 * @author xiaohanghu
 * */
public class DefaultTemplateEngine extends EmptyTemplateEngine {

	public DefaultTemplateEngine() {
		TagProcessorManager tagProcessorManager = new DefaultTagProcessorManager();

		tagProcessorManager.addProcessor(SupportedTagName.INCLUDE,
				new IncludeProcessor());
		tagProcessorManager.addProcessor(SupportedTagName.ELSE_IF,
				new ElseIfProcessor());
		tagProcessorManager
				.addProcessor(SupportedTagName.IF, new IfProcessor());
		tagProcessorManager.addProcessor(SupportedTagName.FOREACH,
				new ForEachProcessor());
		tagProcessorManager.addProcessor(SupportedTagName.SET,
				new SetProcessor());

		this.setTagProcessorManager(tagProcessorManager);
		JexlEngine jexlEngine = new JexlEngine();
		jexlEngine.setDebug(false);
//		jexlEngine.setLenient(false);// 抛出异常JexlException
		this.setJexlEngine(jexlEngine);
		this.setDomParser(new XmlDomParser());
	}

}
