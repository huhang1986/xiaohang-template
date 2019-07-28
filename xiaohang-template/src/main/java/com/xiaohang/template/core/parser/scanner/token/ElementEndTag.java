package com.xiaohang.template.core.parser.scanner.token;

/**
 * @author xiaohanghu
 * */
public class ElementEndTag extends ElementTag {

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{className:'ElementEndTag',name:'");
		builder.append(super.getName());
		builder.append("'}");
		return builder.toString();
	}
}
