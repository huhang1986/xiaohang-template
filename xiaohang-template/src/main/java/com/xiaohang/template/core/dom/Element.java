package com.xiaohang.template.core.dom;

import java.util.List;
import java.util.Map;

/**
 * 
 * xml标签
 * 
 * @author xiaohanghu
 * */
public class Element implements Node {

	private String name;

	private Map<String, Object> propertys;

	private List<Node> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getPropertys() {
		return propertys;
	}

	public void setPropertys(Map<String, Object> propertys) {
		this.propertys = propertys;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{className:'Element',name:'");
		builder.append(name);
		builder.append("', propertys:");
		builder.append(ToJsonStringUtils.toString(propertys));
		builder.append(", children:");
		builder.append(children);
		builder.append("}");
		return builder.toString();
	}

}
