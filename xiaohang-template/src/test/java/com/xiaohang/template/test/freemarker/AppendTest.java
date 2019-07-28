package com.xiaohang.template.test.freemarker;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author xiaohanghu
 * */
public class AppendTest {
	public static Writer writer = new PrintWriter(System.out);
	private static String border = "1px";
	private static String name = "Andy";
	private static int age = 25;
	static {
		writer = new StringWriter();
	}

	public static void main(String[] args) throws Exception {

		test();
		long start = System.nanoTime();

		for (int i = 0; i < 1000000; i++) {
			test();
		}

		long end = System.nanoTime();

		System.out.println((end - start) / 1000000f);

	}

	public static void test() throws IOException {
		writer = new StringWriter();
		StringBuilder builder = new StringBuilder();
		builder.append("<div>");
		builder.append("\r\n");

		builder.append("	<h1>");
		builder.append(name);
		builder.append("</h1>");
		builder.append("\r\n");

		builder.append("	<table border=\"");
		builder.append(border);
		builder.append("\">");
		builder.append("\r\n");

		builder.append("		<tr>");
		builder.append("\r\n");

		builder.append("			<th>name</th>");
		builder.append("\r\n");

		builder.append("			<th>");
		builder.append(name);
		builder.append("</th>");
		builder.append("\r\n");

		builder.append("		</tr>");
		builder.append("\r\n");

		builder.append("		<tr>");
		builder.append("\r\n");

		builder.append("			<th>age</th>");
		builder.append("\r\n");

		builder.append("			<th>");
		builder.append(age);
		builder.append("</th>");
		builder.append("\r\n");

		builder.append("		</tr>");
		builder.append("\r\n");

		builder.append("	</table>");
		builder.append("\r\n");

		builder.append("</div>");
		builder.append("\r\n");
		builder.toString();
//		writer.write(builder.toString());

		// System.out.println(builder.toString());
	}
}
