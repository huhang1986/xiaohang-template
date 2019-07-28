package com.xiaohang.template.core.parser.scanner;

import java.util.List;

import com.xiaohang.template.core.parser.scanner.support.CharNode;
import com.xiaohang.template.core.parser.scanner.support.CharsExcerpt;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;
import com.xiaohang.template.core.parser.scanner.token.TextToken;


public class TextScanner {

	private List<CharNode> keywordNodes = null;

	public TextScanner(List<CharNode> keywordNodes) {
		this.keywordNodes = keywordNodes;
	}

	public ScannerResult scanner(MnemonicReader mnemonicReader) {

		ScannerResult scannerResult = new ScannerResult();

		CharNode charNode = null;
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {

			charNode = ScannerUtils.matchCharNode(mnemonicReader, keywordNodes);

			if (null != charNode) {// 扫描到下一个关键字
				scannerResult.setNextKeywordNode(charNode);
				break;
			}

		}

		CharsExcerpt charsExcerpt = mnemonicReader.getMemory();
		String text = charsExcerpt.toString();
		mnemonicReader.cleaMemory();
		if (null != charNode) {
			charsExcerpt.setEndIndex(charsExcerpt.getEndIndex());
		}

		TextToken textToken = new TextToken();
		textToken.setContent(text);

		scannerResult.setToken(textToken);

		return scannerResult;
	}
}
