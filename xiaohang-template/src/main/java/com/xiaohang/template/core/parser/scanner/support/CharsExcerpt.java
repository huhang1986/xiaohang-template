package com.xiaohang.template.core.parser.scanner.support;

/**
 * @author xiaohanghu
 * */
public class CharsExcerpt {

	private char[] chars;
	private int startIndex;
	private int endIndex;
	private int length;

	public CharsExcerpt(char[] chars) {
		this.chars = chars;
		this.startIndex = 0;
		this.endIndex = chars.length - 1;
		this.length = chars.length;
	}

	public CharsExcerpt(char[] chars, int startIndex, int endIndex) {
		this.chars = chars;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.resetLength();
	}

	protected void resetLength() {
		this.length = endIndex - startIndex + 1;
	}

	public void setStartIndex(int startIndex) {
		if (this.startIndex == startIndex) {
			return;
		}
		this.startIndex = startIndex;
		this.resetLength();
	}

	public void setEndIndex(int endIndex) {
		if (this.endIndex == endIndex) {
			return;
		}
		this.endIndex = endIndex;
		this.resetLength();
	}

	public char[] getChars() {
		return chars;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public int getLength() {
		return length;
	}

	public int getStartIndex() {
		return startIndex;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CharsExcerpt)) {
			return false;
		}
		CharsExcerpt charsExcerpt = (CharsExcerpt) obj;
		if (this.chars == charsExcerpt.chars
				&& this.startIndex != charsExcerpt.startIndex
				&& this.endIndex != charsExcerpt.endIndex) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return new String(chars, startIndex, length);
	}

}
