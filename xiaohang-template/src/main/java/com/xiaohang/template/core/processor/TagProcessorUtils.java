package com.xiaohang.template.core.processor;

import java.util.Map;

import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderUtils;
import com.xiaohang.template.core.processor.impl.DefaultProcessor;


/**
 * @author xiaohanghu
 * */
public class TagProcessorUtils {

	public static TagProcessor getDefaultProcessor() {
		return SingletonDefaultProcessor.INSTANCE;
	}

	public static Object getPropertyValue(String name,
                                          Map<String, Render> propertyRenders, RenderContext renderContext) {
		Render render = propertyRenders.get(name);
		Object value = RenderUtils.getRenderResultObject(render, renderContext);
		return value;
	}

	public static String getPropertyValueString(String name,
			Map<String, Render> propertyRenders, RenderContext renderContext) {
		Render render = propertyRenders.get(name);
		String value = RenderUtils.getRenderResult(render, renderContext);
		return value;
	}

}

class SingletonDefaultProcessor {
	static TagProcessor INSTANCE = new DefaultProcessor();
}
