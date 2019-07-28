package com.xiaohang.template.core.parser.scanner.token;

/**
 * 注释
 * 
 * @author xiaohanghu
 * */
public class CommentToken implements Token {

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
		builder.append("{className:'Comment',content:'");
		builder.append(content);
		builder.append("'}");
		return builder.toString();
	}

}
