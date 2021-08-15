package com.au.pei.express.exception;

/**
 * Exception class  to handle Project NOT found exception.
 */
public class CounterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CounterException(String message) {
		super(message);
	}
}
