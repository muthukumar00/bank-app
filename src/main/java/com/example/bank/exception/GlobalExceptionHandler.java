package com.example.bank.exception;

import java.time.LocalDateTime;

import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
		
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(TransactionExceptionHandle.class)
	public ResponseEntity<ErrorResponse> handleTransactionHandle(TransactionExceptionHandle ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.PARTIAL_CONTENT.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(errorResponse);
	}
	
	@ExceptionHandler(InvalidTransactionHandle.class)
	public ResponseEntity<ErrorResponse> handleException(InvalidTransactionHandle ex) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	
	@ExceptionHandler(StaleStateException.class)
	public ResponseEntity<ErrorResponse> handleStaleState(
	        StaleStateException ex) {

		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), "Account was modified by another transaction. Please retry.", LocalDateTime.now());

	    return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body(response);
	}
	
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<String> handleOptimisticLock(
	        ObjectOptimisticLockingFailureException ex) {
		
		ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.value(), "Account was modified by another transaction. Please retry.", LocalDateTime.now());

	    return ResponseEntity
	            .status(HttpStatus.CONFLICT)
	            .body("Account was modified by another transaction. Please retry.");
	}
	}
