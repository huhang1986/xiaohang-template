package com.xiaohang.template.core.dom;

/**
 * el表达式
 * 
 * @author xiaohanghu
 * */
public class Expression implements Node {

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{className:'Expression',content:'");
		builder.append(content);
		builder.append("'}");
		return builder.toString();
	}

}
