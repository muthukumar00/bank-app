package com.example.bank.exception;

public class CustomerNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(Long customerId) {
		super("The customer with ID " + customerId + " was not found.");
	}

}
