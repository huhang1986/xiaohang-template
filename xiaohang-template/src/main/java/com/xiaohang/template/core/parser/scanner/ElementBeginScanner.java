package com.xiaohang.template.core.parser.scanner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.parser.ParserException;
import com.xiaohang.template.core.parser.XmlKeywords;
import com.xiaohang.template.core.parser.scanner.support.CharNode;
import com.xiaohang.template.core.parser.scanner.support.CharNodeUtils;
import com.xiaohang.template.core.parser.scanner.support.CharUtils;
import com.xiaohang.template.core.parser.scanner.support.CharsExcerpt;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.ElementBeginTag;
import com.xiaohang.template.core.parser.scanner.token.ElementClosedTag;
import com.xiaohang.template.core.parser.scanner.token.Token;

/**
 * @author xiaohanghu
 * */
public class ElementBeginScanner implements TokenScanner {

	private PropertyValueScanner propertyValueScanner;
	{
		Map<String, Object> keywords = new HashMap<String, Object>();
		keywords.put(XmlKeywords.EXPRESSION_START, new ExpressionScanner(
				XmlKeywords.EXPRESSION_STOP));
		keywords.put("'", new EmptyScanner());
		keywords.put("\"", new EmptyScanner());
		propertyValueScanner = new PropertyValueScanner(keywords);
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		String elementName = scnnerTagName(mnemonicReader);
		Map<String, List<Token>> propertys = scannerElementPropertys(mnemonicReader);

		ElementBeginTag elementTag = null;
		mnemonicReader.back(1);
		char ch = (char) mnemonicReader.read();
		if (ch == '/') {
			elementTag = new ElementClosedTag();
			mnemonicReader.read();
		} else {
			elementTag = new ElementBeginTag();
		}
		mnemonicReader.cleaMemory();
		elementTag.setName(elementName);
		elementTag.setPropertys(propertys);

		return elementTag;

	}

	private String scnnerTagName(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (CharUtils.isWhitespace(ch) || ch == '>') {
				mnemonicReader.back(1);
				CharsExcerpt charsExcerpt = mnemonicReader.getMemory();
				String result = charsExcerpt.toString();
				mnemonicReader.cleaMemory();
				return result;
			}
		}
		throw new ParserException("Scanner tag name exception!");
	}

	private Map<String, List<Token>> scannerElementPropertys(
			MnemonicReader mnemonicReader) {

		Map<String, List<Token>> result = new HashMap<String, List<Token>>();

		for (;;) {
			String propertyName = scannerPropertyName(mnemonicReader);
			if (null == propertyName) {
				break;
			}
			List<Token> value = scannerPropertyValue(mnemonicReader);

			result.put(propertyName, value);
		}

		return result;
	}

	/**
	 * 查找xml标签的 属性名
	 * 
	 * */
	private String scannerPropertyName(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (CharUtils.isWhitespace(ch)) {
				continue;
			}
			if (ch == '>' || ch == '/') {
				return null;
			}
			break;
		}
		mnemonicReader.back(1);
		mnemonicReader.cleaMemory();
		mnemonicReader.read();

		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (CharUtils.isWhitespace(ch) || ch == '=') {
				mnemonicReader.back(1);
				CharsExcerpt charsExcerpt = mnemonicReader.getMemory();
				String result = charsExcerpt.toString();
				mnemonicReader.cleaMemory();
				return result;
			}
		}
		throw new ParserException("Scanner property name exception!");
	}

	/**
	 * 查找xml标签的 属性值
	 * */
	private List<Token> scannerPropertyValue(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (CharUtils.isWhitespace(ch) || ch == '=') {
				continue;
			}
			break;
		}
		// mnemonicReader.read();
		mnemonicReader.cleaMemory();

		List<Token> result = propertyValueScanner.scanner(mnemonicReader);
		return result;
	}

	class PropertyValueScanner {

		private List<CharNode> keywordNodes = null;
		private TextScanner textScanner;

		public PropertyValueScanner(Map<String, Object> keywords) {

			this.keywordNodes = CharNodeUtils.buildCharTree(keywords);
			textScanner = new TextScanner(keywordNodes);
			// this.endChar = endChar;

		}

		public List<Token> scanner(MnemonicReader mnemonicReader) {

			List<Token> result = new LinkedList<Token>();

			for (;;) {
				int i = mnemonicReader.read();
				if (i == -1) {
					break;
				}
				char ch = (char) i;
				if (ch == '\'' || ch == '"') {
					break;
				}
				mnemonicReader.back(1);

				CharNode charNode = ScannerUtils.matchCharNode(mnemonicReader,
						keywordNodes);
				boolean sotp = false;
				if (null == charNode) {// 没有关键字，则处理文本
					ScannerResult scannerResult = textScanner
							.scanner(mnemonicReader);
					Token token = scannerResult.getToken();
					result.add(token);
					charNode = scannerResult.getNextKeywordNode();
					if (null != charNode) {
						sotp = useTokenScanner(charNode, mnemonicReader, result);
					}
				} else {
					sotp = useTokenScanner(charNode, mnemonicReader, result);
				}
				if (sotp) {
					break;
				}

			}
			mnemonicReader.cleaMemory();
			return result;
		}

		protected boolean useTokenScanner(CharNode charNode,
				MnemonicReader mnemonicReader, List<Token> result) {
			mnemonicReader.forward(charNode.getFullValue().length);
			mnemonicReader.cleaMemory();
			TokenScanner tokenScanner = (TokenScanner) charNode.getUserData();
			Token token = tokenScanner.scanner(mnemonicReader);
			if (tokenScanner instanceof EmptyScanner) {
				return true;
			}
			result.add(token);
			return false;
		}

	}
}
