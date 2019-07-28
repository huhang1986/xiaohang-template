package com.xiaohang.template.core.parser.scanner.support;

/**
 * @author xiaohanghu
 * */
public class CharUtils {

	public static boolean isWhitespace(char ch) {
		return ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t'
				|| ch == '\f' || ch == '\b';
	}

}
