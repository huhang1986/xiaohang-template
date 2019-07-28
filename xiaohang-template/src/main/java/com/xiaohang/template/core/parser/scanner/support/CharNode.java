package com.xiaohang.template.core.parser.scanner.support;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaohanghu
 * */
public class CharNode {

	private char value;

	private char[] fullValue;

	private Object userData;

	private CharNode parent;

	private List<CharNode> children = new LinkedList<CharNode>();

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public Object getUserData() {
		return userData;
	}

	public void setUserData(Object userData) {
		this.userData = userData;
	}

	public CharNode getParent() {
		return parent;
	}

	public void setParent(CharNode parent) {
		this.parent = parent;
	}

	public List<CharNode> getChildren() {
		return children;
	}

	public void addChild(CharNode child) {
		this.children.add(child);
		child.setParent(this);
	}

	public boolean isLeaf() {
		return null == this.children;
	}

	public char[] getFullValue() {
		return this.fullValue;
	}

	public void setFullValue(char[] fullValue) {
		this.fullValue = fullValue;
	}

	public static char[] toPrimitive(List<Character> fullValue) {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{value:'");
		builder.append(value);
		builder.append("', userDate:'");
		builder.append(userData);
		builder.append("', children:");
		builder.append(children);
		builder.append("}");
		return builder.toString();
	}

}
