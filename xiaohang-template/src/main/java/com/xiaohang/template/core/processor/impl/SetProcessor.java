package com.xiaohang.template.core.processor.impl;

import java.util.Map;

import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderUtils;
import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;


/**
 * @author xiaohanghu
 */
public class SetProcessor implements TagProcessor {

	public static final String PROPERTY_VAR_NAME = "var";
	public static final String PROPERTY_VALUE_NAME = "value";

	@Override
	public TagPropertys parseTagPropertys(Map<String, Render> propertyRenders) {
		SetPropertys propertys = new SetPropertys();

		propertys.setVar(propertyRenders.get(PROPERTY_VAR_NAME));
		propertys.setValue(propertyRenders.get(PROPERTY_VALUE_NAME));

		return propertys;
	}

	@Override
	public void process(RenderContext renderContext, TagPropertys tagPropertys,
                        Render[] chindren) {
		SetPropertys propertys = (SetPropertys) tagPropertys;

		String varName = RenderUtils.getRenderResult(propertys.getVar(),
				renderContext);
		if (null == varName) {
			return;
		}
		Object value = RenderUtils.getRenderResultObject(propertys.getValue(),
				renderContext);

		renderContext.getAttributes().put(varName, value);

	}

}

class SetPropertys implements TagPropertys {

	private Render var;
	private Render value;

	public Render getVar() {
		return var;
	}

	public void setVar(Render var) {
		this.var = var;
	}

	public Render getValue() {
		return value;
	}

	public void setValue(Render value) {
		this.value = value;
	}

}