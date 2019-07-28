package com.xiaohang.template.elapse.parser;

/**
 * XML关键字
 * 
 * @author xiaohanghu
 * */
public interface ElapseKeywords {

	String BEGIN_TAG_START = "${";
	String END_TAG_START = "${/";
	char TAG_STOP = '}';

	String COMMENT_TAG_START = "${:";
	String COMMENT_TAG_STOP = "}";

	String EXPRESSION_START = "${";
	char EXPRESSION_STOP = '}';

	char VARIABLE_START = '#';

}
