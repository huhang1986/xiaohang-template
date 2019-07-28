package com.xiaohang.template.core.processor;

/**
 * @author xiaohanghu
 */
public interface TagProcessorManager {

	/***
	 * 
	 * @param name
	 * @param processor
	 */
	void addProcessor(String name, TagProcessor processor);

	/**
	 * 
	 * @param name
	 * @return
	 */
	TagProcessor getProcessor(String name);

}
