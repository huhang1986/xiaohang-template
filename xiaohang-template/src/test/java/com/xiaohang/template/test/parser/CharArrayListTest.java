package com.xiaohang.template.test.parser;

import java.io.IOException;

import com.xiaohang.template.core.parser.scanner.support.CharArrayList;


public class CharArrayListTest {

	public static void main(String[] args) throws IOException {

		CharArrayList list = new CharArrayList(new char[] { 'a', 'b', 'c' });
		
		list.removeRange(0, list.size());
		
		System.out.println(list);

	}

}
