package com.xiaohang.template.elapse.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.parser.ParserException;
import com.xiaohang.template.core.parser.scanner.ScannerUtils;
import com.xiaohang.template.core.parser.scanner.TokenScanner;
import com.xiaohang.template.core.parser.scanner.support.CharUtils;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.ElementBeginTag;
import com.xiaohang.template.core.parser.scanner.token.ExpressionToken;
import com.xiaohang.template.core.parser.scanner.token.TextToken;
import com.xiaohang.template.core.parser.scanner.token.Token;
import com.xiaohang.template.core.processor.impl.IncludeProcessor;
import com.xiaohang.template.elapse.SupportedTagName;

/**
 * 解析include开始标签
 * 
 * include userlist:#userList
 * 
 * @author xiaohanghu
 * */
public class IncludeBeginTagScanner implements TokenScanner {

	private char endWord;

	public IncludeBeginTagScanner(char endWord) {
		this.endWord = endWord;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		Token templateName = scannerTemplateName(mnemonicReader);

		Map<String, List<Token>> propertys = new HashMap<String, List<Token>>();
		addProperty(propertys, IncludeProcessor.PROPERTY_TEMPLATE_NAME,
				templateName);

		ElementBeginTag elementTag = new ElementBeginTag();
		elementTag.setName(SupportedTagName.INCLUDE);
		elementTag.setPropertys(propertys);

		boolean find = ScannerUtils.scnnerUntilEndWord(mnemonicReader, endWord);
		if (!find) {
			throw new ParserException("Can't find end word '" + endWord + "' !");
		}

		mnemonicReader.cleaMemory();

		return elementTag;

	}

	protected void addProperty(Map<String, List<Token>> propertys, String name,
			Token token) {
		List<Token> itemsList = new LinkedList<Token>();
		itemsList.add(token);
		propertys.put(name, itemsList);
	}

	private String scnnerUntilWhitespaceOrEnd(MnemonicReader mnemonicReader) {
		return ScannerUtils.readUntilEndWordOrWhitespace(mnemonicReader,
				endWord);
	}

	protected Token scannerTemplateName(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (ch == endWord) {
				break;
			}
			if (CharUtils.isWhitespace(ch)) {
				continue;
			}
			if ('"' == ch) {
				mnemonicReader.cleaMemory();
				String text = ScannerUtils.readUntilEndWordOrWhitespace(
						mnemonicReader, '"');
				TextToken token = new TextToken();
				token.setContent(text);
				return token;
			} else {
				mnemonicReader.back(1);
				mnemonicReader.cleaMemory();
				String text = scnnerUntilWhitespaceOrEnd(mnemonicReader);
				ExpressionToken token = new ExpressionToken();
				token.setContent(text);
				return token;
			}
		}
		throw new ParserException("Scanner templateName exception!");
	}

}
