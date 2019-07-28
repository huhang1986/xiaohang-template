package com.xiaohang.template.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.xiaohang.template.core.DefaultTemplateEngine;


/**
 * @author xiaohanghu
 * */
public class MainTest {

	static String text = "asdfasdfasdf<th>${age}</th><#:if value= '234' age='657' >asdf<#:if name='heihei' > </#:if>sadf</#:if> <#:bean name = '' /> dsfsadf";
	static char[] chars = text.toCharArray();
	static byte[] bytes = text.getBytes();
	static int count = 100;

	public static void main(String[] args) throws Exception {

		// StringWriter stringWriter = new StringWriter();
		// System.out.println(StringUtils.isBlank(stringWriter.toString()));

		List<Integer> linkedList = new LinkedList<Integer>();
		List<Integer> arrayList = new ArrayList<Integer>();
		int[] array = new int[1000];
		for (int i = 0; i < 1000; i++) {
			linkedList.add(i);
			arrayList.add(i);
			array[i] = i;
		}
		for (int i = 0; i < 10000; i++) {
			for (Integer j : linkedList) {
				// j++;
			}
		}
		for (int i = 0; i < 10000; i++) {
			for (Integer j : arrayList) {
				// j++;
			}
		}
		long startL = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			for (Integer j : linkedList) {
				// j++;
			}
		}
		long endL = System.nanoTime();

		long startA = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			for (Integer j : arrayList) {
				// j++;
			}
		}
		long endA = System.nanoTime();
		System.out.println("LinkedList for each :\t\t" + (endL - startL));
		System.out.println("ArrayList for each :\t\t" + (endA - startA));

		long startA1 = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			// for (int j = 0; j < arrayList.size(); j++) {
			for (int j = 0, len = arrayList.size(); j < len; j++) {
				int l = arrayList.get(j);
				// l++;
			}
		}
		long endA1 = System.nanoTime();
		System.out.println("ArrayList index iterator :\t" + (endA1 - startA1));

		long startArr = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			for (int j = 0, len = array.length; j < len; j++) {
				int l = array[j];
				// l++;
			}
		}
		long endArr = System.nanoTime();
		System.out.println("Array index iterator:\t\t" + (endArr - startArr));

		long startArr1 = System.nanoTime();
		for (int i = 0; i < 10000; i++) {
			for (int j : array) {
				// l++;
			}
		}
		long endArr1 = System.nanoTime();
		System.out.println("Array for each:\t\t\t" + (endArr1 - startArr1));

		// System.out.print(text);
		// System.out.print(chars);
		// System.out.write(bytes);
		// System.out.println();
		//
		// Writer writer = new PrintWriter(System.out);
		//
		// testByte();
		// testString();
		// testChar();

	}

	public static void testString() {
		long start = System.nanoTime();
		for (int i = 0; i < count; i++) {
			System.out.print(text);
		}
		long end = System.nanoTime();
		System.out.println();
		System.out.println("string:\t" + (end - start));
	}

	public static void testChar() {
		long start = System.nanoTime();
		for (int i = 0; i < count; i++) {
			System.out.print(chars);
		}
		long end = System.nanoTime();
		System.out.println();
		System.out.println("chars:\t" + (end - start));
	}

	public static void testByte() throws IOException {
		long start = System.nanoTime();
		for (int i = 0; i < count; i++) {
			System.out.write(bytes);
		}
		long end = System.nanoTime();
		System.out.println();
		System.out.println("bytes:\t" + (end - start));
	}
}
