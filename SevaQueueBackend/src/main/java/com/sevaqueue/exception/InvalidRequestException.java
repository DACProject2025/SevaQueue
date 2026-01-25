package com.sevaqueue.exception;

public class InvalidRequestException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -509752008531777425L;

	public InvalidRequestException(String message) {
		super(message);
	}
}
