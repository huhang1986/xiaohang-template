package com.xiaohang.template.core.dom;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author xiaohanghu
 * */
public class ToJsonStringUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String toString(Map map) {
		StringBuilder builder = new StringBuilder();
		builder.append("{className:'Map'");

		Iterator<Entry> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry entry = iterator.next();
			builder.append(",");
			builder.append(entry.getKey());
			builder.append(":'");
			builder.append(entry.getValue());
			builder.append("'");
		}

		builder.append("}");
		return builder.toString();
	}

}
