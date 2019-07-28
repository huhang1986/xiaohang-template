package com.xiaohang.template.test;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaohang.template.core.DefaultTemplateEngine;
import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateEngine;

/**
 * @author xiaohanghu
 */
public class ForEachTest {

	public static void main(String[] args) {

		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("template/foreach_test.html");

		TemplateEngine templateEngine = new DefaultTemplateEngine();
		Template template = templateEngine.createTemplate(in, "GBK");

		Map<String, Object> map = new HashMap<String, Object>();

		List<Person> contents = new ArrayList<Person>();
		for (int i = 0; i < 10; i++) {
			Person person = new Person();
			person.setName("name_" + i);
			contents.add(person);
		}

		map.put("msgid", 123654);
		map.put("contents", contents);

		template.render(map, new PrintWriter(System.out));

	}
}
