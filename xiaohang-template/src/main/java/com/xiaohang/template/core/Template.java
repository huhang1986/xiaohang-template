package com.xiaohang.template.core;

import java.io.Writer;
import java.util.Map;

import com.xiaohang.template.core.render.RenderException;

/**
 * @author xiaohanghu
 * */
public interface Template {

	/**
	 * 用指定的参数字典渲染模板
	 * 
	 * */
	void render(Map<String, Object> attributes, Writer writer)
			throws RenderException;

}
