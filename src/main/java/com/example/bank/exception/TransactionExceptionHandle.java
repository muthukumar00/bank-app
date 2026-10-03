package com.example.bank.exception;

public class TransactionExceptionHandle extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TransactionExceptionHandle(Long transactionId) {
		// TODO Auto-generated constructor stub
		super("The transaction " + transactionId + " Exception");
	}

}
