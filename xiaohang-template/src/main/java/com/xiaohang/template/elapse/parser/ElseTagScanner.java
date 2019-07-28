package com.xiaohang.template.elapse.parser;

import com.xiaohang.template.core.parser.ParserException;
import com.xiaohang.template.core.parser.scanner.ScannerUtils;
import com.xiaohang.template.core.parser.scanner.TokenScanner;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.ElementBeginTag;
import com.xiaohang.template.core.parser.scanner.token.Token;
import com.xiaohang.template.elapse.SupportedTagName;

/**
 * 解析forEach开始标签
 * 
 * @author xiaohanghu
 * */
public class ElseTagScanner implements TokenScanner {

	private char endWord;

	public ElseTagScanner(char endWord) {
		this.endWord = endWord;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		ElementBeginTag elementTag = new ElementBeginTag();
		elementTag.setName(SupportedTagName.ELSE);

		boolean find = ScannerUtils.scnnerUntilEndWord(mnemonicReader, endWord);
		if (!find) {
			throw new ParserException("Can't find end word '" + endWord + "' !");
		}

		mnemonicReader.cleaMemory();

		return elementTag;

	}

}
