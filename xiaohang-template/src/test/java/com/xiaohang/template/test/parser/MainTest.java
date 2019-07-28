package com.xiaohang.template.test.parser;

import java.io.IOException;
import java.util.List;

import com.xiaohang.template.core.dom.Node;
import com.xiaohang.template.core.parser.DomParser;
import com.xiaohang.template.core.parser.XmlDomParser;
import com.xiaohang.template.core.support.IOUtils;


/**
 * @author xiaohanghu
 * */
public class MainTest {

	public static void main(String[] args) throws IOException {
		DomParser xmlDomParser = new XmlDomParser();

		String text = "asdfasdfasdf<th>${age}</th><#:if value= '234' age='657' >asdf<#:if name='heihei' > </#:if>sadf</#:if> <#:bean name = '' /> dsfsadf";

		List<Node> xmlNodes = xmlDomParser.parse(IOUtils.createrResder(text));
//		List<Node> jsteNodes = jsteDomParser.parse(IOUtils.createrResder(text));

//		System.out.println(xmlNodes.toString().equals(jsteNodes.toString()));
	}
}
