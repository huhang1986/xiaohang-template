package com.xiaohang.template.elapse.parser;

import com.xiaohang.template.core.processor.impl.IfProcessor;

/**
 * 解析forEach开始标签
 * 
 * @author xiaohanghu
 * */
public class ElseIfTagScanner extends IfBeginTagScanner {

	public ElseIfTagScanner(char endWord) {
		super(endWord);
	}

	protected String getTagName() {
		return IfProcessor.TAG_ELSE_IF;
	}

}
