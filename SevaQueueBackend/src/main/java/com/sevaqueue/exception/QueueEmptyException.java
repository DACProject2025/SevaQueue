package com.sevaqueue.exception;

public class QueueEmptyException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 310642029812020227L;

	public QueueEmptyException(String message) {
		super(message);
	}
}
