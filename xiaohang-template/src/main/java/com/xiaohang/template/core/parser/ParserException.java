package com.xiaohang.template.core.parser;

/**
 * 
 * @author XiaohangHu
 */
public class ParserException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5982113877670286969L;

	public ParserException() {
		super();
	}

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

}
