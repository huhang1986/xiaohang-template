package com.xiaohang.template.core.processor.impl;

import java.util.Map;

import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;

/**
 * 用于给ElseIf标签填充属性值
 * 
 * @author xiaohanghu
 */
public class ElseIfProcessor implements TagProcessor {

	public static final String PROPERTY_IF_CONDITION = "test";

	@Override
	public TagPropertys parseTagPropertys(Map<String, Render> propertyRenders) {
		IfPropertys ifPropertys = new IfPropertys();
		ifPropertys.setCondition(propertyRenders.get(PROPERTY_IF_CONDITION));
		return ifPropertys;
	}

	@Override
	public void process(RenderContext renderContext, TagPropertys tagPropertys,
                        Render[] chindren) {

	}

}
