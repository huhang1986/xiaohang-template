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
import com.xiaohang.template.core.processor.impl.SetProcessor;
import com.xiaohang.template.elapse.SupportedTagName;

/**
 * 解析forEach开始标签
 * 
 * @author xiaohanghu
 * */
public class AssignTagScanner implements TokenScanner {

	private char endWord;

	private char EQUAL = '=';

	public AssignTagScanner(char endWord) {
		this.endWord = endWord;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		Token var = scannerVar(mnemonicReader);
		Token value = scannerValue(mnemonicReader);

		Map<String, List<Token>> propertys = new HashMap<String, List<Token>>();
		addProperty(propertys, SetProcessor.PROPERTY_VAR_NAME, var);
		addProperty(propertys, SetProcessor.PROPERTY_VALUE_NAME, value);

		ElementBeginTag elementTag = new ElementBeginTag();
		elementTag.setName(SupportedTagName.SET);
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

	protected Token scannerVar(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (ch == endWord) {
				break;
			}
			if (!CharUtils.isWhitespace(ch)) {
				mnemonicReader.back(1);
				mnemonicReader.cleaMemory();
				String content = ScannerUtils.readUntilEndWordOrWhitespace(
						mnemonicReader, EQUAL);
				TextToken token = new TextToken();
				token.setContent(content);
				return token;
			}
		}
		throw new ParserException("Scanner items exception!");
	}

	protected Token scannerValue(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (ch == endWord) {
				break;
			}
			if (ch == EQUAL || CharUtils.isWhitespace(ch)) {
				continue;
			}
			if (ch == '\'' || ch == '"') {
				mnemonicReader.cleaMemory();
				String valueContent = ScannerUtils.readUntilEndWord(
						mnemonicReader, ch);
				TextToken token = new TextToken();
				token.setContent(valueContent);
				return token;
			} else {
				mnemonicReader.back(1);
				mnemonicReader.cleaMemory();
				String valueContent = ScannerUtils.readUntilEndWord(
						mnemonicReader, endWord);
				ExpressionToken token = new ExpressionToken();
				token.setContent(valueContent);
				return token;
			}

		}
		throw new ParserException("Scanner items exception!");
	}

}
