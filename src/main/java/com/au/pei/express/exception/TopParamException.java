package com.au.pei.express.exception;

/**
 * Exception class to handle exceptions related to the Input Payload.
 */
public class TopParamException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TopParamException(String message) {
		super(message);
	}
}
