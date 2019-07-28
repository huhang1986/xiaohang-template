package com.xiaohang.template.core.parser.scanner;

import com.xiaohang.template.core.parser.ParserException;
import com.xiaohang.template.core.parser.scanner.support.CharsExcerpt;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.CommentToken;
import com.xiaohang.template.core.parser.scanner.token.Token;

/**
 * @author xiaohanghu
 * */
public class CommentScanner implements TokenScanner {

	public char[] endTag;
	private String endTagString;

	public CommentScanner(String endTag) {
		super();
		this.endTag = endTag.toCharArray();
		this.endTagString = endTag;
	}

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {

		CommentToken commentToken = new CommentToken();

		for (int i = 0; i != -1; i = mnemonicReader.read()) {
			boolean match = ScannerUtils.matchWord(mnemonicReader, endTag);
			if (match) {
				CharsExcerpt charsExcerpt = mnemonicReader.getMemory();
				commentToken.setContent(charsExcerpt.toString());

				mnemonicReader.forward(endTag.length);
				mnemonicReader.cleaMemory();

				return commentToken;
			}
		}

		throw new ParserException("Scanner comment can't find end tag \""
				+ endTagString + "\" !");

	}

}
