package com.example.bank.exception;

public class ObjectOptimisticLockingFailureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectOptimisticLockingFailureException(String message) {
		super(message);
	}
	
}
