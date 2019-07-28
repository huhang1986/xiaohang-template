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
import com.xiaohang.template.core.parser.scanner.token.Token;
import com.xiaohang.template.core.processor.impl.IfProcessor;
import com.xiaohang.template.elapse.SupportedTagName;


/**
 * 解析forEach开始标签
 * 
 * @author xiaohanghu
 * */
public class IfBeginTagScanner implements TokenScanner {

	private char endWord;

	public IfBeginTagScanner(char endWord) {
		this.endWord = endWord;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		Token content = scannerContent(mnemonicReader);

		Map<String, List<Token>> propertys = new HashMap<String, List<Token>>();
		addProperty(propertys, IfProcessor.PROPERTY_IF_CONDITION, content);

		ElementBeginTag elementTag = new ElementBeginTag();
		elementTag.setName(getTagName());
		elementTag.setPropertys(propertys);

		boolean find = ScannerUtils.scnnerUntilEndWord(mnemonicReader, endWord);
		if (!find) {
			throw new ParserException("Can't find end word '" + endWord + "' !");
		}

		mnemonicReader.cleaMemory();

		return elementTag;

	}

	protected String getTagName() {
		return SupportedTagName.IF;
	}

	protected void addProperty(Map<String, List<Token>> propertys, String name,
			Token token) {
		List<Token> itemsList = new LinkedList<Token>();
		itemsList.add(token);
		propertys.put(name, itemsList);
	}

	protected Token scannerContent(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (ch == endWord) {
				break;
			}
			// if (ch == ElapseKeywords.VARIABLE_START) {
			// mnemonicReader.cleaMemory();
			// String content = ScannerUtils.readUntilEndWord(mnemonicReader,
			// endWord);
			// ExpressionToken token = new ExpressionToken();
			// token.setContent(content);
			// return token;
			// }
			//
			if (CharUtils.isWhitespace(ch)) {
				continue;
			}
			mnemonicReader.back(1);
			mnemonicReader.cleaMemory();
			String content = ScannerUtils.readUntilEndWord(mnemonicReader,
					endWord);
			ExpressionToken token = new ExpressionToken();
			token.setContent(content);
			return token;
		}
		throw new ParserException("Scanner items exception!");
	}

}
