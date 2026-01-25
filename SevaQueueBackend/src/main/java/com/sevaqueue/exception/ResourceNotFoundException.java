package com.sevaqueue.exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3360188273627798436L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
