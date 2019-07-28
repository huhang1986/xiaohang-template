package com.xiaohang.template.test.parser;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.parser.scanner.ConfigurableScanner;
import com.xiaohang.template.core.parser.scanner.ElementBeginScanner;
import com.xiaohang.template.core.parser.scanner.ElementEndScanner;
import com.xiaohang.template.core.parser.scanner.ExpressionScanner;
import com.xiaohang.template.core.parser.scanner.TokenScanner;
import com.xiaohang.template.core.parser.scanner.token.Token;


public class DefaultScannerTest {

	public static void main(String[] args) throws Exception {

		String text = "asdfasdfasdf<th>${age}</th><#:if name='heihei${test}' > </#:if>";
		Reader reader = new StringReader(text);

		Map<String, TokenScanner> keywords = new HashMap<String, TokenScanner>();
		keywords.put("<#:", new ElementBeginScanner());
		keywords.put("</#:", new ElementEndScanner('>'));
		keywords.put("${", new ExpressionScanner('}'));

		ConfigurableScanner defaultScanner = new ConfigurableScanner(keywords);

		List<Token> result = defaultScanner.scanner(reader);
		System.out.println(result);

		// List<CharNode> keywordNodes = CharNodeUtils.buildCharTree(keywords);
		// MnemonicReader mnemonicReader = new MnemonicReader(reader);
		// mnemonicReader.read();
		// mnemonicReader.back(1);
		// CharNode charNode = ScannerUtils.matchCharNode(mnemonicReader,
		// keywordNodes);
		// System.out.println(charNode);
	}

}
