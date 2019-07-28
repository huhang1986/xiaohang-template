package com.xiaohang.template.test.expression;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.xiaohang.template.core.DefaultTemplateEngine;
import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateEngine;
import com.xiaohang.template.test.Person;


/**
 * @author xiaohanghu
 * */
public class MainTest {

	public static void main(String[] args) throws Exception {

		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream("template/expression_test.html");

		TemplateEngine templateEngine = new DefaultTemplateEngine();

		Template template = templateEngine.createTemplate(inputStream, "UTF-8");

		Map<String, Object> map = new HashMap<String, Object>();
		Person person = new Person();
		//person.setName("Andy");

		Person father = new Person();  
		father.setName("Andy's father");
		person.setFather(father);

		map.put("person", person);

		Writer writer = new PrintWriter(System.out);
		template.render(map, writer);

		writer.flush();

	}

}
