package com.xiaohang.template.core.render;

import java.io.IOException;

import com.xiaohang.template.core.TemplateEngine;
import com.xiaohang.template.core.dom.Expression;
import com.xiaohang.template.core.parser.ParserException;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.JexlException;

public class ExpressionRender implements Render {

	// private Expression expression = null;
	private org.apache.commons.jexl2.Expression jexlExpression = null;

	public ExpressionRender(Expression expression, TemplateEngine templateEngine) {
		// this.expression = expression;
		JexlEngine jexlEngine = templateEngine.getJexlEngine();
		String contentString = expression.getContent();
		try {
			jexlExpression = jexlEngine.createExpression(contentString);
		} catch (JexlException e) {
			throw new ParserException("Create Expression [" + contentString
					+ "] error!", e);
		}
	}

	public org.apache.commons.jexl2.Expression getJexlExpression() {
		return jexlExpression;
	}

	public Object evaluate(RenderContext renderContext) {
//		Map<String, Object> attributes = renderContext.getAttributes();
//		JexlContext jexlContext = new MapContext(attributes);
		JexlContext jexlContext = renderContext.getJexlContext();
		Object value = jexlExpression.evaluate(jexlContext);
		return value;
	}

	@Override
	public void render(RenderContext renderContext) {
		try {
			doRender(renderContext);
		} catch (IOException e) {
			throw new RenderException("Render element ["
					+ jexlExpression.getExpression() + "] throw exception!", e);
		}
	}

	public void doRender(RenderContext renderContext) throws IOException {
		Object value = null;
		try {
			value = evaluate(renderContext);
		} catch (JexlException e) {
			throw new RenderException(
					"Evaluate exception[" + jexlExpression.getExpression()
							+ "] throw JexlException!", e);
		}

		if (null != value) {
			renderContext.getWriter().write(value.toString());
		}
	}

}
