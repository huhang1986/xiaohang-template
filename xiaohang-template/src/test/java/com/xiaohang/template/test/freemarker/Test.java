package com.xiaohang.template.test.freemarker;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.TemplateElement;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author xiaohanghu
 * */
public class Test {

	public static Writer writer = new PrintWriter(System.out);
	static {
		writer = new StringWriter();
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> context = createContext();

		Template template = getTemplate("template/", "asciitable.html");
		template.setEncoding("UTF-8");

		test(context, template);
		long start = System.nanoTime();

		for (int i = 0; i < 1000000; i++) {
			test(context, template);
		}

		long end = System.nanoTime();

		System.out.println((end - start) / 1000000f);
	}

	public static void test(Map<String, Object> context, Template template)
			throws TemplateException, IOException {
		template.process(context, writer);
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
		Template template = new Template("test", new InputStreamReader(in),
				null, "utf-8");
		return template;
	}

	public static void test(Object template, Map<String, Object> context,
			Writer out, int count) throws Exception {
		Template t = (Template) template;
		for (int i = 0; i < count; i++) {
			t.process(context, out);
		}
	}

}
