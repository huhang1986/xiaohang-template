package com.xiaohang.template.core.parser.scanner.token;

/**
 * @author xiaohanghu
 * */
public abstract class ElementTag implements Token {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{className:'ElementTag',name:'");
		builder.append(name);
		builder.append("'}");
		return builder.toString();
	}


}
