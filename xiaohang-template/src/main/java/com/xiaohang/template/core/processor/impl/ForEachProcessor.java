package com.xiaohang.template.core.processor.impl;

import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.processor.TagProcessor;
import com.xiaohang.template.core.processor.TagPropertys;
import com.xiaohang.template.core.render.Render;
import com.xiaohang.template.core.render.RenderContext;
import com.xiaohang.template.core.render.RenderUtils;


/**
 * @author xiaohanghu
 */
public class ForEachProcessor implements TagProcessor {

	public static final String PROPERTY_VAR_NAME = "var";
	public static final String PROPERTY_ITEMS_NAME = "items";
	public static final String PROPERTY_VAR_STATUS_NAME = "varstatus";

	public static final String DEFAULT_ITEM_NAME = "item";

	@Override
	public TagPropertys parseTagPropertys(Map<String, Render> propertyRenders) {
		ForEachPropertys propertys = new ForEachPropertys();

		propertys.setVar(propertyRenders.get(PROPERTY_VAR_NAME));
		propertys.setItems(propertyRenders.get(PROPERTY_ITEMS_NAME));
		propertys.setVarstatus(propertyRenders.get(PROPERTY_VAR_STATUS_NAME));

		return propertys;
	}

	@Override
	public void process(RenderContext renderContext, TagPropertys tagPropertys,
                        Render[] chindren) {
		if (null == chindren || chindren.length == 0) {
			return;
		}
		ForEachPropertys propertys = (ForEachPropertys) tagPropertys;
		Render itemsPropertyRender = propertys.getItems();
		Object items = RenderUtils.getRenderResultObject(itemsPropertyRender,
				renderContext);

		Render itemNamePropertyRender = propertys.getVar();
		String itemName = RenderUtils.getRenderResult(itemNamePropertyRender,
				renderContext);

		if (itemName == null) {
			itemName = DEFAULT_ITEM_NAME;
		}

		Render varstatusNamePropertyRender = propertys.getVarstatus();
		String varstatusName = RenderUtils.getRenderResult(
				varstatusNamePropertyRender, renderContext);

		renderCollection(items, itemName, varstatusName, renderContext,
				chindren);

	}

	protected void renderCollection(Object items, String itemName,
			String varstatusName, RenderContext renderContext, Render[] chindren) {
		Map<String, Object> attributes = renderContext.getAttributes();
		Object oldItemValue = attributes.get(itemName);
		if (null == varstatusName) {
			renderCollection(items, itemName, attributes, renderContext,
					chindren);
		} else {
			renderCollection(items, itemName, varstatusName, attributes,
					renderContext, chindren);
		}
		attributes.remove(itemName);
		if (null != oldItemValue) {
			attributes.put(itemName, oldItemValue);
		}
	}

	@SuppressWarnings("rawtypes")
	protected void renderCollection(Object items, String itemName,
			Map<String, Object> attributes, RenderContext renderContext,
			Render[] chindren) {
		if (items instanceof List) {
			List itemList = (List) items;
			for (Object item : itemList) {
				attributes.put(itemName, item);
				renderContext(chindren, renderContext);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	protected void renderCollection(Object items, String itemName,
			String varstatusName, Map<String, Object> attributes,
			RenderContext renderContext, Render[] chindren) {
		Object oldVarStatusValue = attributes.get(varstatusName);
		VarStatus varStatus = new VarStatus();
		int index = 0;
		int count = 1;
		if (items instanceof List) {
			List itemList = (List) items;
			for (Object item : itemList) {
				varStatus.setIndex(index);
				varStatus.setCount(count);
				attributes.put(itemName, item);
				attributes.put(varstatusName, varStatus);
				renderContext(chindren, renderContext);
				index++;
				count++;
			}
		}
		if (null != oldVarStatusValue) {
			attributes.put(varstatusName, oldVarStatusValue);
		}
	}

	protected void renderContext(Render[] chindren, RenderContext renderContext) {
		for (Render render : chindren) {
			render.render(renderContext);
		}
	}

}

class ForEachPropertys implements TagPropertys {

	private Render var;
	private Render items;
	private Render varstatus;

	public Render getVar() {
		return var;
	}

	public void setVar(Render var) {
		this.var = var;
	}

	public Render getItems() {
		return items;
	}

	public void setItems(Render items) {
		this.items = items;
	}

	public Render getVarstatus() {
		return varstatus;
	}

	public void setVarstatus(Render varstatus) {
		this.varstatus = varstatus;
	}

}