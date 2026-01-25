package com.sevaqueue.exception;

public class UnauthorizedActionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5832107992296759077L;

	public UnauthorizedActionException(String message) {
		super(message);
	}
}
