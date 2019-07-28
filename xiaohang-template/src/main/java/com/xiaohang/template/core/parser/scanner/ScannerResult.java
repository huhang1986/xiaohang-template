package com.xiaohang.template.core.parser.scanner;

import com.xiaohang.template.core.parser.scanner.support.CharNode;
import com.xiaohang.template.core.parser.scanner.token.Token;


/**
 * @author xiaohanghu
 * */
public class ScannerResult {

	private Token token;
	private CharNode nextKeywordNode;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public CharNode getNextKeywordNode() {
		return nextKeywordNode;
	}

	public void setNextKeywordNode(CharNode nextKeywordNode) {
		this.nextKeywordNode = nextKeywordNode;
	}

}
