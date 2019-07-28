package com.xiaohang.template.core.render;

import java.io.IOException;

import com.xiaohang.template.core.dom.Text;

/**
 * @author xiaohanghu
 * */
public class TextRender implements Render {

	private Text text = null;
	private char[] textChars = null;

	public TextRender(Text text) {
		this.text = text;
		this.textChars = this.text.getContent().toCharArray();
	}

	public Text getText() {
		return text;
	}

	@Override
	public void render(RenderContext renderContext) {
		try {
			renderContext.getWriter().write(textChars);
		} catch (IOException e) {
			throw new RenderException("Render element [" + text.toString()
					+ "] throw exception!", e);
		}
	}

}
