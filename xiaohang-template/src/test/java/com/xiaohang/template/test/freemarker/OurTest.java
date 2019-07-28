package com.xiaohang.template.test.freemarker;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.xiaohang.template.core.DefaultTemplateEngine;
import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateEngine;


public class OurTest {
	public static Writer writer = new PrintWriter(System.out);
	static {
		writer = new StringWriter();
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> context = createContext();

		Template template = getTemplate("template/", "asciitable.html");

		test(context, template);
		long start = System.nanoTime();

		for (int i = 0; i < 1000000; i++) {
			test(context, template);
		}

		long end = System.nanoTime();

		System.out.println((end - start) / 1000000f);
	}

	public static void test(Map<String, Object> context, Template template) {
		writer = new StringWriter();
		template.render(context, writer);
	}

	private static Map<String, Object> createContext() {
		HashMap<String, Object> context = new HashMap<String, Object>();
		context.put("name", "Andy");
		context.put("border", "1px");
		context.put("age", "25");
		return context;
	}

	public static Template getTemplate(String packagePath, String templateName)
			throws Exception {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(packagePath + templateName);
		if (in == null)
			return null;
		TemplateEngine templateEngine = new DefaultTemplateEngine();
		Template template = templateEngine.createTemplate(in, "gbk");
		return template;
	}

	public static void test(Object template, Map<String, Object> context,
			Writer out, int count) throws Exception {
		Template t = (Template) template;
		for (int i = 0; i < count; i++) {
			t.render(context, out);
		}
	}
}
