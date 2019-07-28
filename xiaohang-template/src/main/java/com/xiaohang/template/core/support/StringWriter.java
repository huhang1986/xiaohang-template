package com.xiaohang.template.core.support;

import java.io.IOException;
import java.io.Writer;

/**
 * 非线程安全的实现
 * 
 * @author xiaohanghu
 * */
public class StringWriter extends Writer {

	public StringWriter() {
		buf = new StringBuilder();
		lock = buf;
	}

	public StringWriter(int i) {
		if (i < 0) {
			throw new IllegalArgumentException("Negative buffer size");
		} else {
			buf = new StringBuilder(i);
			lock = buf;
			return;
		}
	}

	public void write(int i) {
		buf.append((char) i);
	}

	public void write(char ac[], int i, int j) {
		// if (i < 0 || i > ac.length || j < 0 || i + j > ac.length || i + j <
		// 0)
		// throw new IndexOutOfBoundsException();
		// if (j == 0) {
		// return;
		// } else {
		buf.append(ac, i, j);
		// return;
		// }
	}

	public void write(char ac[]) {
		buf.append(ac);
	}

	public void write(String s) {
		buf.append(s);
	}

	public void write(String s, int i, int j) {
		buf.append(s.substring(i, i + j));
	}

	public StringWriter append(CharSequence charsequence) {
		if (charsequence == null)
			write("null");
		else
			write(charsequence.toString());
		return this;
	}

	public StringWriter append(CharSequence charsequence, int i, int j) {
		Object obj = charsequence != null ? ((Object) (charsequence)) : "null";
		write(((CharSequence) (obj)).subSequence(i, j).toString());
		return this;
	}

	public StringWriter append(char c) {
		write(c);
		return this;
	}

	public String toString() {
		return buf.toString();
	}

	public StringBuilder getBuffer() {
		return buf;
	}

	public void flush() {
	}

	@Override
	public void close() throws IOException {
	}

	private StringBuilder buf;

}
