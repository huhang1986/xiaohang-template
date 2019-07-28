package com.xiaohang.template.core.dom;

/**
 * 文本
 * 
 * @author xiaohanghu
 * */
public class Text implements Node {
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
		builder.append("{className:'Text',content:'");
		builder.append(content);
		builder.append("'}");
		return builder.toString();
	}

}
