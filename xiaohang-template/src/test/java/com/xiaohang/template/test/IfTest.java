package com.xiaohang.template.test;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.xiaohang.template.core.DefaultTemplateEngine;
import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateEngine;

/**
 * @author xiaohanghu
 */
public class IfTest {

	public static void main(String[] args) {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("template/if_test.html");

		TemplateEngine templateEngine = new DefaultTemplateEngine();
		Template template = templateEngine.createTemplate(in, "GBK");
		StringWriter sb = new StringWriter();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msgid", 123654);
		template.render(map, sb);
		System.out.println(sb.toString());

	}

}
