package com.xiaohang.template.core.processor.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.dom.Element;
import com.xiaohang.template.core.processor.SupportedTagName;
import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;
import com.xiaohang.template.core.render.ElementRender;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;


/**
 * @author xiaohanghu
 */
public class IfProcessor implements TagProcessor {

	public static final String PROPERTY_IF_CONDITION = "test";

	public static String TAG_ELSE_IF = SupportedTagName.ELSE_IF;
	public static String TAG_ELSE = "else";

	@Override
	public TagPropertys parseTagPropertys(Map<String, Render> propertyRenders) {
		IfPropertys ifPropertys = new IfPropertys();
		ifPropertys.setCondition(propertyRenders.get(PROPERTY_IF_CONDITION));
		return ifPropertys;
	}

	@Override
	public void process(RenderContext renderContext, TagPropertys tagPropertys,
                        Render[] chindren) {

		if (null == chindren || chindren.length == 0) {
			return;
		}

		IfPropertys propertys = (IfPropertys) tagPropertys;

		List<Render> chindrenList = Arrays.asList(chindren);

		Iterator<Render> iterator = chindrenList.iterator();

		if (getCondition(propertys, renderContext)) {
			renderUntilNextIf(iterator, renderContext);
			return;
		}

		while (iterator.hasNext()) {
			Render render = iterator.next();
			if (render instanceof ElementRender) {
				ElementRender elementRender = (ElementRender) render;
				Element element = elementRender.getElement();
				String name = element.getName();
				if (StringUtils.equalsIgnoreCase(TAG_ELSE, name)) {
					renderUntilNextIf(iterator, renderContext);
				} else if (StringUtils.equalsIgnoreCase(TAG_ELSE_IF, name)) {
					boolean con = getCondition(
							(IfPropertys) elementRender.getTagPropertys(),
							renderContext);
					if (con) {
						renderUntilNextIf(iterator, renderContext);
					}
				}
			}
		}

	}

	protected void renderUntilNextIf(Iterator<Render> iterator,
			RenderContext context) {
		while (iterator.hasNext()) {
			Render render = iterator.next();
			if (render instanceof ElementRender) {
				ElementRender elementRender = (ElementRender) render;
				Element element = elementRender.getElement();
				String name = element.getName();
				if (StringUtils.equalsIgnoreCase(TAG_ELSE, name)) {
					break;
				}
				if (StringUtils.equalsIgnoreCase(TAG_ELSE_IF, name)) {
					break;
				}
			}
			render.render(context);
		}
	}

	protected boolean getCondition(IfPropertys ifPropertys,
			RenderContext renderContext) {

		Render render = ifPropertys.getCondition();

		Object conditionValue = RenderUtils.getRenderResultObject(render,
				renderContext);

		if (conditionValue instanceof Boolean) {
			return (Boolean) conditionValue;
		}

		if (conditionValue instanceof String) {
			String value = (String) conditionValue;
			return BooleanUtils.toBoolean(value);
		}

		if (conditionValue instanceof Number) {
			Number number = (Number) conditionValue;
			return number.intValue() > 0;
		}

		return false;
	}

}

class IfPropertys implements TagPropertys {

	private Render condition;

	public Render getCondition() {
		return condition;
	}

	public void setCondition(Render condition) {
		this.condition = condition;
	}

}
