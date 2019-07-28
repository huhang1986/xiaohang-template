package com.xiaohang.template.core.parser.scanner;

import com.xiaohang.template.core.parser.ParserException;
import com.xiaohang.template.core.parser.scanner.support.CharsExcerpt;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.ExpressionToken;
import com.xiaohang.template.core.parser.scanner.token.Token;

/**
 * @author xiaohanghu
 * */
public class ExpressionScanner implements TokenScanner {

	private char endWord;

	public ExpressionScanner(char endWord) {
		this.endWord = endWord;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		String content = scnnerContent(mnemonicReader);

		ExpressionToken token = new ExpressionToken();
		token.setContent(content);

		return token;

	}

	private String scnnerContent(MnemonicReader mnemonicReader) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (ch == endWord) {
				CharsExcerpt charsExcerpt = mnemonicReader.getMemory();
				charsExcerpt.setEndIndex(charsExcerpt.getEndIndex() - 1);
				String result = charsExcerpt.toString();
				mnemonicReader.cleaMemory();
				return result;
			}
		}
		throw new ParserException("Scanner expression exception!");
	}

}
