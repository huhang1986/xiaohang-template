package com.xiaohang.template.core.parser.scanner.support;

import java.io.IOException;
import java.io.Reader;

/**
 * @author xiaohanghu
 * */
public class MnemonicReader {

	private Reader reader;

	private CharArrayList buffer = new CharArrayList(100);

	private int currentIndex = 0;

	public MnemonicReader(Reader reader) {
		this.reader = reader;
	}

	public int read() {

		if (buffer.size() > currentIndex) {
			int result = buffer.get(currentIndex);
			currentIndex++;
			return result;
		}

		try {
			int i = reader.read();
			if (i != -1) {
				currentIndex++;
				buffer.add((char) i);
			}
			return i;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public void forward(int i) {
		for (int j = 0; j < i; j++) {
			read();
		}
	}

	public void back(int i) {
		this.currentIndex -= i;
	}

	public void cleaMemory() {
		int bufferSize = buffer.size();
		if (bufferSize == 0) {
			return;
		}
		int i = bufferSize - currentIndex;

		if (i > 0) {
			this.buffer.removeRange(0, this.buffer.size() - i);
		} else {
			this.buffer.clear();
		}

		currentIndex = 0;
	}

	public CharsExcerpt getMemory() {
		CharsExcerpt result = buffer.toCharsExcerpt();
		result.setEndIndex(currentIndex - 1);
		return result;
	}

	public String getMemoryToStirng() {
		return getMemory().toString();
	}
}
