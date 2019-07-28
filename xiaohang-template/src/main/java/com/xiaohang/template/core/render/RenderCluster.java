package com.xiaohang.template.core.render;

import java.util.List;


/**
 * @author xiaohanghu
 * */
public class RenderCluster implements Render {

	// 数组遍历性能较好
	private Render[] renders = null;

	public RenderCluster(Render[] renders) {
		if (null == renders || renders.length == 0) {
			throw new IllegalArgumentException("Render list must not be null!");
		}
		this.renders = renders;
	}

	public RenderCluster(List<Render> renders) {
		if (null == renders || renders.isEmpty()) {
			throw new IllegalArgumentException("Render list must not be null!");
		}
		this.renders = renders.toArray(new Render[renders.size()]);
	}

	@Override
	public void render(RenderContext renderContext) {
		for (Render render : renders) {
			render.render(renderContext);
		}
	}

}
