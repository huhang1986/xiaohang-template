package com.xiaohang.template.core.parser;

/**
 * XML关键字
 * 
 * @author xiaohanghu
 * */
public interface XmlKeywords {

	String BEGIN_TAG_START = "<";
	String END_TAG_START = "</";
	char TAG_STOP = '>';

	String NAME_SPACE_SEPARATOR = ":";

	String COMMENT_TAG_START = "<!--";
	String COMMENT_TAG_STOP = "-->";

	String EXPRESSION_START = "${";
	char EXPRESSION_STOP = '}';

}
