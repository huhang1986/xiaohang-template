package com.xiaohang.template.core.parser.scanner.token;

import java.util.List;
import java.util.Map;

/**
 * @author xiaohanghu
 * */
public class ElementBeginTag extends ElementTag {

	private Map<String, List<Token>> propertys;

	public Map<String, List<Token>> getPropertys() {
		return propertys;
	}

	public void setPropertys(Map<String, List<Token>> propertys) {
		this.propertys = propertys;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{className:'ElementBeginTag',name:'");
		builder.append(super.getName());
//		builder.append("',propertys:'");
//		builder.append(propertys);
		builder.append("'}");
		return builder.toString();
	}

}
