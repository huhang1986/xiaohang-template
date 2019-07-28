package com.xiaohang.template.core.parser.scanner.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * 
 * @author xiaohanghu
 * */
public abstract class CharNodeUtils {

	public static CharNode findNode(char c, List<CharNode> charNodes) {
		for (CharNode charNode : charNodes) {
			char value = charNode.getValue();
			if (c == value) {
				return charNode;
			}
		}
		return null;
	}

	public static void addNodes(String str, Object userData, CharNode charNode) {
		addNodes(str.toCharArray(), userData, charNode);
	}

	public static void addNodes(char[] chars, Object userData, CharNode charNode) {
		addNodes(chars, 0, userData, charNode);
	}

	public static void addNodes(char[] chars, int currentIndex,
			Object userData, CharNode currentCharNode) {
		char c = chars[currentIndex];
		CharNode charNode = findNode(c, currentCharNode.getChildren());
		if (null == charNode) {
			charNode = new CharNode();
			charNode.setValue(c);
			currentCharNode.addChild(charNode);
		}

		currentIndex = currentIndex + 1;

		if (currentIndex == chars.length) {// if current char is the last one
			charNode.setUserData(userData);
			charNode.setFullValue(chars);
			return;
		}

		currentCharNode = charNode;

		addNodes(chars, currentIndex, userData, currentCharNode);

	}

	/**
	 * 根据指定的词根构造字符树
	 * 
	 * result json example:
	 * 
	 * [ { value : 'A', userData : 'null', children : [ { value : 'B', userData
	 * : 'userData__AB', children : [ { value : 'C', userData : 'userData__ABC',
	 * children : [] }, { value : 'D', userData : 'null', children : [ { value :
	 * 'E', userData : 'userData__ABDE', children : [] } ] } ] } ] }, { value :
	 * 'B', userData : 'null', children : [ { value : 'C', userData :
	 * 'userData__BC', children : [] }, { value : 'D', userData :
	 * 'userData__BD', children : [] } ] } ]
	 * 
	 * */
	public static List<CharNode> buildCharTree(Map<String, ?> keywords) {
		CharNode root = new CharNode();
		for (Entry<String, ?> entry : keywords.entrySet()) {
			String keyword = entry.getKey();
			Object userData = entry.getValue();
			addNodes(keyword, userData, root);
		}
		return root.getChildren();
	}

	public static void main(String[] args) {

		Map<String, Object> keywords = new HashMap<String, Object>();
		keywords.put("BC", "userData__BC");
		keywords.put("BD", "userData__BD");
		keywords.put("ABC", "userData__ABC");
		keywords.put("AB", "userData__AB");
		keywords.put("ABDE", "userData__ABDE");

		List<CharNode> charNodes = buildCharTree(keywords);

		System.out.println(charNodes.toString());
	}

}
