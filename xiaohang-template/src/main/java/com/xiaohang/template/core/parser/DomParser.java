package com.xiaohang.template.core.parser;

import java.io.Reader;
import java.util.List;

import com.xiaohang.template.core.dom.Node;

/**
 * 
 * 将文本解析为dom树
 * 
 * @author xiaohanghu
 * */
public interface DomParser {

	List<Node> parse(Reader reader);

}
