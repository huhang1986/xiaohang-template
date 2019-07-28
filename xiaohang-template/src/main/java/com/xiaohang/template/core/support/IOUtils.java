package com.xiaohang.template.core.support;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

/**
 * @author xiaohanghu
 * */
public class IOUtils {

	public static Reader createrResder(InputStream inputStream, String encoding) {
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(inputStream, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
		Reader reader = new BufferedReader(inputStreamReader);
		return reader;
	}

	public static Reader createrResder(String text) {
		Reader reader = new StringReader(text);
		return reader;
	}
}
