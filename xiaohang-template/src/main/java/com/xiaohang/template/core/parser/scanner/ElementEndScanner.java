package com.xiaohang.template.core.parser.scanner;

import com.xiaohang.template.core.parser.ParserException;
import com.xiaohang.template.core.parser.scanner.support.CharUtils;
import com.xiaohang.template.core.parser.scanner.support.CharsExcerpt;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.ElementEndTag;
import com.xiaohang.template.core.parser.scanner.token.Token;

/**
 * @author xiaohanghu
 * */
public class ElementEndScanner implements TokenScanner {

	private char endWord;

	public ElementEndScanner(char endWord) {
		this.endWord = endWord;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		String elementName = scnnerTagName(mnemonicReader);

		ElementEndTag elementTag = new ElementEndTag();
		elementTag.setName(elementName);

		boolean find = ScannerUtils.scnnerUntilEndWord(mnemonicReader, endWord);
		if (!find) {
			throw new ParserException("Can't find end word '" + endWord + "' !");
		}
		mnemonicReader.cleaMemory();

		return elementTag;

	}

	private String scnnerTagName(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (CharUtils.isWhitespace(ch) || ch == endWord) {
				mnemonicReader.back(1);
				CharsExcerpt charsExcerpt = mnemonicReader.getMemory();
				String result = charsExcerpt.toString();
				mnemonicReader.cleaMemory();
				return result;
			}
		}
		throw new ParserException("Scanner tag name exception!");
	}

}
