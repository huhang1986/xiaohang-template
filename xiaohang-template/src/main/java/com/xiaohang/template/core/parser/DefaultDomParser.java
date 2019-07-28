package com.xiaohang.template.core.parser;

import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.xiaohang.template.core.dom.Element;
import com.xiaohang.template.core.dom.Expression;
import com.xiaohang.template.core.dom.Node;
import com.xiaohang.template.core.dom.Text;
import org.apache.commons.lang.StringUtils;
import com.xiaohang.template.core.parser.scanner.ConfigurableScanner;
import com.xiaohang.template.core.parser.scanner.TokenScanner;
import com.xiaohang.template.core.parser.scanner.token.ElementBeginTag;
import com.xiaohang.template.core.parser.scanner.token.ElementEndTag;
import com.xiaohang.template.core.parser.scanner.token.ElementTag;
import com.xiaohang.template.core.parser.scanner.token.ExpressionToken;
import com.xiaohang.template.core.parser.scanner.token.TextToken;
import com.xiaohang.template.core.parser.scanner.token.Token;

/**
 * 
 * 将文本解析为dom树
 * 
 * @author xiaohanghu
 * */
public class DefaultDomParser implements DomParser {

	private ConfigurableScanner scanner = null;

	public DefaultDomParser(Map<String, TokenScanner> keywords) {
		scanner = new ConfigurableScanner(keywords);
	}

	public List<Node> parse(Reader reader) {

		List<Token> tokens = scanner.scanner(reader);

		List<Node> nodes = parseTokens(tokens);

		return nodes;
	}

	private List<Node> parseTokens(List<Token> tokens) {
		Iterator<Token> iterator = tokens.iterator();
		return findUntil(iterator).getNodes();
	}

	/**
	 * 查找直到下一个闭合标签
	 * 
	 * 该方法不识别ElementClosedTag
	 * */
	private FindResult findUntil(Iterator<Token> iterator) {
		List<Node> nodes = new LinkedList<Node>();
		while (iterator.hasNext()) {
			Token token = iterator.next();
			if (token instanceof TextToken) {

				TextToken textToken = (TextToken) token;
				Node node = createText(textToken);
				nodes.add(node);

			} else if (token instanceof ExpressionToken) {

				ExpressionToken elToken = (ExpressionToken) token;
				Node node = createExpression(elToken);
				nodes.add(node);

			} else if (token instanceof ElementTag) {
				if (token instanceof ElementBeginTag) {
					ElementBeginTag elementBeginTag = (ElementBeginTag) token;
					FindResult result = findUntil(iterator);
					boolean match = StringUtils.equalsIgnoreCase(
							elementBeginTag.getName(), result.getEndTagName());
					Node node = null;
					if (match) {
						node = createElement(elementBeginTag, result.getNodes());
						nodes.add(node);
					} else {
						node = createElement(elementBeginTag);
						nodes.add(node);
						nodes.addAll(result.getNodes());
						result.setNodes(nodes);
						return result;
					}
				} else if (token instanceof ElementEndTag) {
					ElementEndTag elementEndTag = (ElementEndTag) token;
					String endTagName = elementEndTag.getName();
					FindResult result = new FindResult();
					result.setNodes(nodes);
					result.setEndTagName(endTagName);
					return result;
				}
			}
		}
		FindResult result = new FindResult();
		result.setNodes(nodes);
		return result;
	}

	private Map<String, Object> parsePropertys(
			Map<String, List<Token>> propertys) {

		Map<String, Object> result = new HashMap<String, Object>();
		if (null == propertys) {
			return result;
		}

		for (Entry<String, List<Token>> entry : propertys.entrySet()) {
			String key = entry.getKey();
			List<Token> value = entry.getValue();
			if (null == value) {
				result.put(key, null);
				continue;
			}
			List<Node> nodes = this.parseTokens(value);
			if (nodes.size() == 1) {
				Node node = nodes.get(0);
				result.put(key, node);
				continue;
			}
			result.put(key, nodes);
		}

		return result;
	}

	private Element createElement(ElementBeginTag tag) {
		Element element = new Element();
		element.setName(tag.getName());
		Map<String, Object> nodes = parsePropertys(tag.getPropertys());
		element.setPropertys(nodes);
		return element;
	}

	private Element createElement(ElementBeginTag tag, List<Node> children) {
		Element element = new Element();
		element.setName(tag.getName());
		Map<String, Object> nodes = parsePropertys(tag.getPropertys());
		element.setPropertys(nodes);
		element.setChildren(children);
		return element;
	}

	private Text createText(TextToken textToken) {
		Text text = new Text();
		text.setContent(textToken.getContent());
		return text;
	}

	private Expression createExpression(ExpressionToken elToken) {
		Expression expression = new Expression();
		expression.setContent(elToken.getContent());
		return expression;
	}

}

class FindResult {

	private List<Node> nodes;
	private String endTagName;

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public String getEndTagName() {
		return endTagName;
	}

	public void setEndTagName(String endTagName) {
		this.endTagName = endTagName;
	}

}
