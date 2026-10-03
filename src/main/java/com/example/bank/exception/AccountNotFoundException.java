package com.example.bank.exception;

public class AccountNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(Integer account_id) {
		super("The account with ID " + account_id + " was not found.");
	}
}
