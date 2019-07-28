package com.xiaohang.template.core.processor.impl;

import java.util.Map;

import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderUtils;

/**
 * @author xiaohanghu
 */
public class DefaultProcessor implements TagProcessor {

	@Override
	public TagPropertys parseTagPropertys(Map<String, Render> propertyRenders) {
		return null;
	}

	@Override
	public void process(RenderContext context, TagPropertys tagPropertys,
                        Render[] chindren) {

		if (null == chindren || chindren.length == 0) {
			return;
		}

		RenderUtils.render(chindren, context);

	}

}
