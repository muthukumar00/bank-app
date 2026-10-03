package com.example.bank.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bank_transaction")
public class TransactionModel {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long transaction_id;
	
	Integer account_id;
	String transaction_reference;
	String transaction_type;
	BigDecimal amount;
	BigDecimal balance_before;
	BigDecimal balance_after;
	String Transaction_status;
	String description;
	LocalDateTime transaction_date;
	
	public long getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}
	public Integer getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}
	public String getTransaction_reference() {
		return transaction_reference;
	}
	public void setTransaction_reference(String transaction_reference) {
		this.transaction_reference = transaction_reference;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getBefore_balance() {
		return balance_before;
	}
	public void setBefore_balance(BigDecimal before_balance) {
		this.balance_before = before_balance;
	}
	public BigDecimal getAfter_balance() {
		return balance_after;
	}
	public void setAfter_balance(BigDecimal after_balance) {
		this.balance_after = after_balance;
	}
	public String getTransaction_status() {
		return Transaction_status;
	}
	public void setTransaction_status(String transaction_status) {
		Transaction_status = transaction_status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDateTime transaction_date) {
		this.transaction_date = transaction_date;
	}
	
	@Override
	public String toString() {
		return "TransactionModel [transaction_id=" + transaction_id + ", account_id=" + account_id
				+ ", transaction_reference=" + transaction_reference + ", transaction_type=" + transaction_type
				+ ", amount=" + amount + ", balance_before=" + balance_before + ", balance_after=" + balance_after
				+ ", Transaction_status=" + Transaction_status + ", description=" + description + ", transaction_date="
				+ transaction_date + "]";
	}
	
	
}
