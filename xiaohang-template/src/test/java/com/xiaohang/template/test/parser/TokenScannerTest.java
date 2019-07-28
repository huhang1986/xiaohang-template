package com.xiaohang.template.test.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.parser.scanner.ScannerUtils;
import com.xiaohang.template.core.parser.scanner.support.CharArrayList;
import com.xiaohang.template.core.parser.scanner.support.CharNode;
import com.xiaohang.template.core.parser.scanner.support.CharNodeUtils;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;


/**
 * @author xiaohanghu
 * */
public class TokenScannerTest {

	public static void main(String[] args) throws IOException {

		char[] chars = new char[] { 'A', 'B', 'C' };
		CharArrayList charArrayList = new CharArrayList(chars);
		String str = new String(chars);

		long start = System.currentTimeMillis();

		for (int i = 0; i < 10000000; i++) {
			// new String(chars);
			// char c = chars[2];
			char c = charArrayList.get(2);
		}

		long end = System.currentTimeMillis();

		System.out.println(end - start);

		Map<String, Object> keywords = new HashMap<String, Object>();
		keywords.put("${", "userDate__${");
		keywords.put("${forarr", "userDate__${forarr");
		keywords.put("ABC", "userDate__ABC");
		keywords.put("AB", "userDate__AB");
//		keywords.put("ABDE", "userDate__ABDE");

		List<CharNode> charNodes = CharNodeUtils.buildCharTree(keywords);

		String text = "${for";
		Reader reader = new StringReader(text);

		MnemonicReader mnemonicReader = new MnemonicReader(reader);
		
//		System.out.println((char)mnemonicReader.read());
//		System.out.println((char)mnemonicReader.read());

		CharNode charNode = ScannerUtils.matchCharNode(mnemonicReader,
				charNodes);

		if (null != charNode) {
			char[] fullValue = charNode.getFullValue();
			if (null != fullValue) {
				System.out.println(fullValue);
			}
			System.out.println(charNode.getUserData());
		}

	}

	public static Reader getReader(InputStream inputStream, String encoding)
			throws UnsupportedEncodingException {
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, encoding);
		Reader reader = new BufferedReader(inputStreamReader);
		return reader;
	}

}
