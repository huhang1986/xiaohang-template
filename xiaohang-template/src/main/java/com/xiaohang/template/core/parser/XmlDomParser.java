package com.xiaohang.template.core.parser;

import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.dom.Node;
import com.xiaohang.template.core.parser.scanner.CommentScanner;
import com.xiaohang.template.core.parser.scanner.ElementBeginScanner;
import com.xiaohang.template.core.parser.scanner.ElementEndScanner;
import com.xiaohang.template.core.parser.scanner.ExpressionScanner;
import com.xiaohang.template.core.parser.scanner.TokenScanner;

/**
 * 
 * 将文本解析为dom树
 * 
 * @author xiaohanghu
 * */
public class XmlDomParser implements DomParser {

	private DomParser domParser;

	public static final String DEFAULT_NAME_SPACE = "c";

	public XmlDomParser() {
		this(DEFAULT_NAME_SPACE);
	}

	public XmlDomParser(String nameSpace) {
		String elementBeginTagWord = XmlKeywords.BEGIN_TAG_START + nameSpace
				+ XmlKeywords.NAME_SPACE_SEPARATOR;
		String elementEndTagWord = XmlKeywords.END_TAG_START + nameSpace
				+ XmlKeywords.NAME_SPACE_SEPARATOR;
		Map<String, TokenScanner> keywords = new HashMap<String, TokenScanner>();
		keywords.put(elementBeginTagWord, new ElementBeginScanner());
		keywords.put(elementEndTagWord, new ElementEndScanner(
				XmlKeywords.TAG_STOP));
		keywords.put(XmlKeywords.EXPRESSION_START, new ExpressionScanner(
				XmlKeywords.EXPRESSION_STOP));
		keywords.put(XmlKeywords.COMMENT_TAG_START, new CommentScanner(
				XmlKeywords.COMMENT_TAG_STOP));

		domParser = new DefaultDomParser(keywords);
	}

	@Override
	public List<Node> parse(Reader reader) {
		return domParser.parse(reader);
	}

}
