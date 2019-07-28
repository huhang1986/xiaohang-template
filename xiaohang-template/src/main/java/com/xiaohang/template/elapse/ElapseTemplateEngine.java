package com.xiaohang.template.elapse;

import java.util.HashMap;
import java.util.Map;

import com.xiaohang.template.core.EmptyTemplateEngine;
import com.xiaohang.template.core.parser.DefaultDomParser;
import com.xiaohang.template.core.parser.DomParser;
import com.xiaohang.template.core.parser.scanner.CommentScanner;
import com.xiaohang.template.core.parser.scanner.ElementEndScanner;
import com.xiaohang.template.core.parser.scanner.ExpressionScanner;
import com.xiaohang.template.core.parser.scanner.TokenScanner;
import com.xiaohang.template.core.processor.TagProcessorManager;
import org.apache.commons.jexl2.JexlEngine;
import com.xiaohang.template.core.processor.impl.DefaultTagProcessorManager;
import com.xiaohang.template.core.processor.impl.ElseIfProcessor;
import com.xiaohang.template.core.processor.impl.ForEachProcessor;
import com.xiaohang.template.core.processor.impl.IfProcessor;
import com.xiaohang.template.core.processor.impl.IncludeProcessor;
import com.xiaohang.template.core.processor.impl.SetProcessor;
import com.xiaohang.template.elapse.parser.AssignTagScanner;
import com.xiaohang.template.elapse.parser.ElapseKeywords;
import com.xiaohang.template.elapse.parser.ElseIfTagScanner;
import com.xiaohang.template.elapse.parser.ElseTagScanner;
import com.xiaohang.template.elapse.parser.ForEachBeginTagScanner;
import com.xiaohang.template.elapse.parser.IfBeginTagScanner;
import com.xiaohang.template.elapse.parser.IncludeBeginTagScanner;


/**
 * @author xiaohanghu
 * */
public class ElapseTemplateEngine extends EmptyTemplateEngine {

	public ElapseTemplateEngine() {
		TagProcessorManager tagProcessorManager = new DefaultTagProcessorManager();

		tagProcessorManager.addProcessor(SupportedTagName.INCLUDE,
				new IncludeProcessor());
		tagProcessorManager
				.addProcessor(
						com.xiaohang.template.core.processor.SupportedTagName.ELSE_IF,
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
		// jexlEngine.setLenient(false);// �׳��쳣JexlException
		this.setJexlEngine(jexlEngine);
		this.setDomParser(createDomParser());
	}

	protected DomParser createDomParser() {

		Map<String, TokenScanner> keywords = new HashMap<String, TokenScanner>();

		/** end tag */
		keywords.put(ElapseKeywords.END_TAG_START, new ElementEndScanner(
				ElapseKeywords.TAG_STOP));

		/** expression */
		keywords.put(ElapseKeywords.EXPRESSION_START, new ExpressionScanner(
				ElapseKeywords.EXPRESSION_STOP));

		/** comment */
		keywords.put(ElapseKeywords.COMMENT_TAG_START, new CommentScanner(
				ElapseKeywords.COMMENT_TAG_STOP));

		/** include */
		keywords.put(ElapseKeywords.BEGIN_TAG_START + SupportedTagName.INCLUDE,
				new IncludeBeginTagScanner(ElapseKeywords.TAG_STOP));

		/** if */
		keywords.put(ElapseKeywords.BEGIN_TAG_START + SupportedTagName.IF,
				new IfBeginTagScanner(ElapseKeywords.TAG_STOP));

		/** else if */
		keywords.put(ElapseKeywords.BEGIN_TAG_START + SupportedTagName.ELSE_IF,
				new ElseIfTagScanner(ElapseKeywords.TAG_STOP));

		/** else */
		keywords.put(ElapseKeywords.BEGIN_TAG_START + SupportedTagName.ELSE,
				new ElseTagScanner(ElapseKeywords.TAG_STOP));

		/** for each */
		keywords.put(ElapseKeywords.BEGIN_TAG_START + SupportedTagName.FOREACH,
				new ForEachBeginTagScanner(ElapseKeywords.TAG_STOP));

		/** set */
		keywords.put(ElapseKeywords.BEGIN_TAG_START + SupportedTagName.SET,
				new AssignTagScanner(ElapseKeywords.TAG_STOP));

		return new DefaultDomParser(keywords);
	}
}
