package com.xiaohang.template.core.parser.scanner;

import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.Token;


/**
 * @author xiaohanghu
 * */
public class EmptyScanner implements TokenScanner {

	@Override
	public Token scanner(MnemonicReader mnemonicReader) {
		// ignore
		return null;
	}

}
