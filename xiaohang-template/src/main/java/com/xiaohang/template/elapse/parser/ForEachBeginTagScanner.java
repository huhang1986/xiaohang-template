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
import com.xiaohang.template.core.processor.impl.ForEachProcessor;
import com.xiaohang.template.elapse.SupportedTagName;

/**
 * 解析forEach开始标签
 * 
 * @author xiaohanghu
 * */
public class ForEachBeginTagScanner implements TokenScanner {

	private char endWord;

	private char[] AS = "as".toCharArray();

	public ForEachBeginTagScanner(char endWord) {
		this.endWord = endWord;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		Token items = scannerItems(mnemonicReader);
		Token varName = scannerVarName(mnemonicReader);

		Map<String, List<Token>> propertys = new HashMap<String, List<Token>>();
		addProperty(propertys, ForEachProcessor.PROPERTY_ITEMS_NAME, items);
		addProperty(propertys, ForEachProcessor.PROPERTY_VAR_NAME, varName);

		ElementBeginTag elementTag = new ElementBeginTag();
		elementTag.setName(SupportedTagName.FOREACH);
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

	protected Token scannerItems(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (ch == endWord) {
				break;
			}
			// if (ch == ElapseKeywords.VARIABLE_START) {
			// mnemonicReader.cleaMemory();
			// String content = scnnerUntilWhitespaceOrEnd(mnemonicReader);
			// ExpressionToken token = new ExpressionToken();
			// token.setContent(content);
			// return token;
			// }
			if (CharUtils.isWhitespace(ch)) {
				continue;
			}
			mnemonicReader.back(1);
			mnemonicReader.cleaMemory();
			String content = scnnerUntilWhitespaceOrEnd(mnemonicReader);
			ExpressionToken token = new ExpressionToken();
			token.setContent(content);
			return token;
		}
		throw new ParserException("Scanner items exception!");
	}

	private String scnnerUntilWhitespaceOrEnd(MnemonicReader mnemonicReader) {
		return ScannerUtils.readUntilEndWordOrWhitespace(mnemonicReader,
				endWord);
	}

	protected Token scannerVarName(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			if (ScannerUtils.matchWord(mnemonicReader, AS)) {
				mnemonicReader.forward(AS.length);
				continue;
			}
			char ch = (char) i;
			if (ch == endWord) {
				break;
			}
			if (CharUtils.isWhitespace(ch)) {
				continue;
			}
			mnemonicReader.back(1);
			mnemonicReader.cleaMemory();
			String itemName = scnnerUntilWhitespaceOrEnd(mnemonicReader);
			TextToken token = new TextToken();
			token.setContent(itemName);
			return token;
		}
		throw new ParserException("Scanner items exception!");
	}

}
