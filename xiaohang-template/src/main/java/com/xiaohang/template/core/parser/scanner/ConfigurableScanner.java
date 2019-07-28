package com.xiaohang.template.core.parser.scanner;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.parser.scanner.support.CharNode;
import com.xiaohang.template.core.parser.scanner.support.CharNodeUtils;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.Token;

/**
 * ConfigurableScanner 可以指定词根和扫描器。
 * 
 * @author xiaohanghu
 * */
public class ConfigurableScanner {

	private List<CharNode> keywordNodes = null;
	private TextScanner textScanner;

	public ConfigurableScanner(Map<String, TokenScanner> keywords) {

		this.keywordNodes = CharNodeUtils.buildCharTree(keywords);
		textScanner = new TextScanner(keywordNodes);

	}

	public List<Token> scanner(Reader reader) {
		try {
			return doScanner(reader);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public List<Token> doScanner(Reader reader) {

		MnemonicReader mnemonicReader = new MnemonicReader(reader);
		List<Token> result = new LinkedList<Token>();

		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {

			mnemonicReader.back(1);

			CharNode charNode = ScannerUtils.matchCharNode(mnemonicReader,
					keywordNodes);

			if (null == charNode) {// 没有关键字，则处理文本
				ScannerResult scannerResult = textScanner
						.scanner(mnemonicReader);
				Token token = scannerResult.getToken();
				result.add(token);
				charNode = scannerResult.getNextKeywordNode();
				if (null != charNode) {
					useTokenScanner(charNode, mnemonicReader, result);
				}
			} else {
				useTokenScanner(charNode, mnemonicReader, result);
			}

		}
		return result;
	}

	protected void useTokenScanner(CharNode charNode,
			MnemonicReader mnemonicReader, List<Token> result) {
		mnemonicReader.forward(charNode.getFullValue().length);
		mnemonicReader.cleaMemory();
		TokenScanner tokenScanner = (TokenScanner) charNode.getUserData();
		Token token = tokenScanner.scanner(mnemonicReader);
		result.add(token);
	}

}
