package com.xiaohang.template.test;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.xiaohang.template.core.ConcurrentTemplateManager;
import com.xiaohang.template.core.DefaultTemplateEngine;
import com.xiaohang.template.core.Template;
import com.xiaohang.template.core.TemplateEngine;
import com.xiaohang.template.core.TemplateManager;

/**
 * @author xiaohanghu
 */
public class EnginTest {

	public static void main(String[] args) {

		// 获取模板引擎
		TemplateEngine templateEngine = new DefaultTemplateEngine();

		// 获取模板输入流
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("template/demo.html");
		InputStream include = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("template/include.html");

		// 通过模板输入流，构造模板
		Template template = templateEngine.createTemplate(in, "UTF-8");
		Template includeTemplate = templateEngine
				.createTemplate(include, "UTF-8");

		// 向templateManager注册模板
		TemplateManager templateManager = new ConcurrentTemplateManager();
		templateManager.addTemplate("demo", template);
		templateManager.addTemplate("test", includeTemplate);

		// 构造模板所需要的参数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("msgid", 123654);
		map.put("msg", 147852);
		map.put("sohucms_summary", "sohucms_summary");
		map.put("pic_temp", "pic_temp");
		map.put("at_temp", "at_temp");
		map.put("from", "from");
		map.put("geo", "geo");
		map.put("num_reply", "num_reply");

		// 模板渲染，输出到StringWrite
		StringWriter sb = new StringWriter();
		template.render(map, sb);

		System.out.println(sb.toString());

	}

}
