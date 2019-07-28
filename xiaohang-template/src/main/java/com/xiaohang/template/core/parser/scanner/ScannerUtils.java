package com.xiaohang.template.core.parser.scanner;

import java.util.List;

import com.xiaohang.template.core.parser.ParserException;
import com.xiaohang.template.core.parser.scanner.support.CharNode;
import com.xiaohang.template.core.parser.scanner.support.CharNodeUtils;
import com.xiaohang.template.core.parser.scanner.support.CharUtils;
import com.xiaohang.template.core.parser.scanner.support.MnemonicReader;

/**
 * @author xiaohanghu
 * */
public abstract class ScannerUtils {

	/**
	 * 读取直到遇到某个字符
	 * 
	 * @return 如果没有找到字符则返回false
	 * */
	public static boolean scnnerUntilEndWord(MnemonicReader mnemonicReader,
			char endWord) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char c = (char) i;
			if (c == endWord) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 读取直到遇到空白或者某个字符
	 * 
	 * @return 如果没有找到字符则返回false
	 * */
	public static boolean scnnerUntilEndWordOrWhitespace(
			MnemonicReader mnemonicReader, char endWord) {
		for (int i = mnemonicReader.read(); i != -1; i = mnemonicReader.read()) {
			char ch = (char) i;
			if (CharUtils.isWhitespace(ch) || ch == endWord) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 读取直到遇到空白或者某个字符，读取的内容不包含endWord
	 * 
	 * @return 如果没有找到字符则返回false
	 * */
	public static String readUntilEndWordOrWhitespace(
			MnemonicReader mnemonicReader, char endWord) {
		boolean find = scnnerUntilEndWordOrWhitespace(mnemonicReader, endWord);
		if (!find) {
			throw new ParserException("Scanner can't find whitespace or +'"
					+ endWord + "' !");
		}
		mnemonicReader.back(1);
		String result = mnemonicReader.getMemoryToStirng();
		// mnemonicReader.forward(1);
		mnemonicReader.cleaMemory();
		return result;
	}

	/**
	 * 读取直到遇到空白或者某个字符，读取的内容不包含endWord
	 * 
	 * @return 如果没有找到字符则返回false
	 * */
	public static String readUntilEndWord(MnemonicReader mnemonicReader,
			char endWord) {
		boolean find = scnnerUntilEndWord(mnemonicReader, endWord);
		if (!find) {
			throw new ParserException("Scanner can't find whitespace or +'"
					+ endWord + "' !");
		}
		mnemonicReader.back(1);
		String result = mnemonicReader.getMemoryToStirng();
		// mnemonicReader.forward(1);
		mnemonicReader.cleaMemory();
		return result;
	}

	/**
	 * 判断接下来读取的字符是否匹配给定的字符
	 * */
	public static boolean matchWord(MnemonicReader mnemonicReader, char[] chars) {
		int readCount = 0;
		boolean result = true;
		for (char ch : chars) {
			int i = mnemonicReader.read();
			if (i == -1) {
				result = false;
				break;
			}
			int currentChar = (char) i;
			readCount++;
			if (ch != currentChar) {
				result = false;
				break;
			}
		}
		mnemonicReader.back(readCount);
		return result;
	}

	/**
	 * reader中查找匹配的且userData不为空的节点,mnemonicReader指针会重置
	 * 
	 * 优先匹配最长的关键字
	 * */
	public static CharNode matchCharNode(MnemonicReader mnemonicReader,
			List<CharNode> charNodes) {
		Context context = new Context();
		CharNode result = matchCharNode(mnemonicReader, charNodes, context);
		mnemonicReader.back(context.readCount);
		if (null == result) {
			return null;
		}
		if (null == result.getUserData()) {
			return findParetHasUserData(result);
		}
		return result;
	}

	protected static CharNode findParetHasUserData(CharNode charNode) {
		charNode = charNode.getParent();
		if (null == charNode) {
			return null;
		}
		if (null != charNode.getUserData()) {
			return charNode;
		}
		return findParetHasUserData(charNode);
	}

	protected static CharNode matchCharNode(MnemonicReader mnemonicReader,
			List<CharNode> charNodes, Context context) {
		int i = mnemonicReader.read();
		if (i < 0) {
			return null;
		}

		char c = (char) i;
		context.readCount++;

		CharNode result = null;

		result = CharNodeUtils.findNode(c, charNodes);
		if (null == result) {
			return null;
		}

		charNodes = result.getChildren();
		if (charNodes.isEmpty()) {
			return result;
		}
		CharNode nextCharNode = matchCharNode(mnemonicReader, charNodes,
				context);
		if (null != nextCharNode) {
			result = nextCharNode;
		}

		return result;
	}

	static class Context {
		// 记录读取过的字符个数
		int readCount;
	}
}
