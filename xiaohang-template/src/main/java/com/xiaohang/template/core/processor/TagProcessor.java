package com.xiaohang.template.core.processor;

import java.util.Map;

import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;


/**
 * @author xiaohanghu
 */
public interface TagProcessor {

	TagPropertys parseTagPropertys(Map<String, Render> propertyRenders);

	/**
	 * @param renderContext
	 * @return
	 */
	void process(RenderContext renderContext,
                 TagPropertys tagPropertys, Render[] chindren);

}
