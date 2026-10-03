package com.example.bank.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.bank.account.AccountModel;
import com.example.bank.account.AccountRepo;
import com.example.bank.exception.AccountNotFoundException;
import com.example.bank.exception.CustomerNotFoundException;
import com.example.bank.exception.InvalidTransactionHandle;
import com.example.bank.exception.TransactionExceptionHandle;
import com.example.bank.idempotency.IdempotencyModel;
import com.example.bank.idempotency.IdempotencyRepo;
import com.example.bank.idempotency.IdempotencyService;

import jakarta.transaction.Transactional;
import jakarta.transaction.TransactionalException;

@Service
@Profile("!demo")
public class TransactionService {
	
	
	TransactionRepo transactionRepo;
	AccountRepo acccountRepo;
	IdempotencyService idempotencyService;
	
	TransactionService(
			TransactionRepo transactionRepo,
			AccountRepo acccountRepo,
			IdempotencyService idempotencyService
			) {
		this.transactionRepo = transactionRepo;
		this.acccountRepo = acccountRepo;
		this.idempotencyService = idempotencyService;
	}

	public List<TransactionModel> getAllTransactions() {
		// TODO Auto-generated method stub
		List<TransactionModel> transactions = transactionRepo.findAll();
		for (TransactionModel transaction : transactions) {
			System.out.println("Transaction: " + transaction.getTransaction_id() 
											+ " " + transaction.getAmount() 
											+ " " + transaction.getTransaction_type()
											+ " " + transaction.getTransaction_date());
		}
		List<TransactionModel> LowestSalary = transactions.stream()
											.sorted((t1, t2) -> t1.getAmount()
                                            .compareTo(t2.getAmount()))
											.limit(5)
											.toList();
		System.out.println("Lowest Salary Transactions:" + LowestSalary);
		return transactionRepo.findAll();
	}

	public String addTransaction(TransactionModel transaction) {
		// TODO Auto-generated method stub
		TransactionModel savedTransaction = transactionRepo.save(transaction);
		System.out.println("Transaction added: " + savedTransaction.getTransaction_id() 
											+ " " + savedTransaction.getAmount() 
											+ " " + savedTransaction.getTransaction_type()
											+ " " + savedTransaction.getTransaction_date());
		return "Successfully added transaction with ID: ";
	}

	public String updateTransaction(long transaction_id, TransactionModel transaction) {
		// TODO Auto-generated method stub
		Optional<TransactionModel> existingTransaction = transactionRepo.findById(transaction_id);
		
		if(existingTransaction.isPresent()) {
			existingTransaction.get().setBefore_balance(existingTransaction.get().getAfter_balance());
			if(transaction.getAmount() != null) {
				if(transaction.getTransaction_type() != null && !transaction.getTransaction_type().isEmpty() && transaction.getTransaction_type().equals("DEPOSIT")) {
					BigDecimal newAmount = existingTransaction.get().getBefore_balance().add(transaction.getAmount());
					existingTransaction.get().setAfter_balance(newAmount);
					existingTransaction.get().setTransaction_type("DEPOSIT");
					existingTransaction.get().setDescription("testing");
					} else if(transaction.getTransaction_type() != null
								&& !transaction.getTransaction_type().isEmpty()
								&& transaction.getTransaction_type().equals("WITHDRAWAL")) {
						if(existingTransaction.get().getAfter_balance().compareTo(transaction.getAmount()) < 0) {
							new TransactionExceptionHandle(transaction_id);
							return "InSufficient balance";
						}
					BigDecimal newAmount = existingTransaction.get().getAfter_balance().subtract(transaction.getAmount());
					existingTransaction.get().setAfter_balance(newAmount);
					existingTransaction.get().setTransaction_type("WITHDRAWAL");
					existingTransaction.get().setDescription("testing");
				}
				
			}
			existingTransaction.get().setAmount(transaction.getAmount());
			System.out.println("Trans update payload " + existingTransaction.get());
			
			transactionRepo.save(existingTransaction.get());
			return "Successfully updated transaction with ID: " + transaction_id;
		} else {
			System.out.println("Transaction with ID: " + transaction_id + " not found.");
			return "Failed to update transaction with ID: ";
		}
	}
	
	@Transactional
	public ResponseEntity<String> onTransferAmountOtherAccount(TransferRequest transferRequest, String idempotency) {
		
		Optional<IdempotencyModel> existingRequest =  idempotencyService.getfindById(idempotency);
		
		System.out.println("####IdempotencyModel data " + existingRequest);
			
		if(existingRequest.isPresent()) {
			return ResponseEntity.ok(existingRequest.get().getResponseData());
		}
		
		AccountModel fromAccount = acccountRepo.findByIdForUpdate(transferRequest.getFromAccountId())
				.orElseThrow(() -> new AccountNotFoundException(transferRequest.getFromAccountId()));
		
		AccountModel toAccount = acccountRepo.findByIdForUpdate(transferRequest.getToAccountId())
				.orElseThrow(() -> new AccountNotFoundException(transferRequest.getToAccountId()));
		
		BigDecimal transferAmount = transferRequest.getAmount();
		
		System.out.println(
		        "Account read: balance = "
		        + fromAccount.getBalance()
		        + ", version = "
		        + fromAccount.getVersionLong()
		    );

		    // TEMPORARY: concurrency testing only
		    try {
		        Thread.sleep(5000);
		    } catch (InterruptedException e) {
		        Thread.currentThread().interrupt();
		    }
		
		TransactionModel transaction = new TransactionModel();
		Long fromtransactionId = System.currentTimeMillis();
		
		if(fromAccount.getBalance().compareTo(transferAmount) < 0) {
			 throw new InvalidTransactionHandle("Insufficient balance in the source account.");
		}
		
		fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
		toAccount.setBalance(toAccount.getBalance().add(transferAmount));
		
		fromAccount.setupdated_at(LocalDateTime.now());
		toAccount.setupdated_at(LocalDateTime.now());
		
		AccountModel updatedFromAccount = acccountRepo.save(fromAccount);
		AccountModel updatedToAccount = acccountRepo.save(toAccount);
		
		TransactionModel fromTransaction = new TransactionModel();
		//fromTransaction.setTransaction_id(fromtransactionId);
		fromTransaction.setAccount_id(fromAccount.getAccount_id());
		fromTransaction.setTransaction_reference("TRN" + fromtransactionId);
		fromTransaction.setTransaction_type("WITHDRAWAL");
		fromTransaction.setAmount(transferAmount);
		fromTransaction.setBefore_balance(fromAccount.getBalance());
		fromTransaction.setAfter_balance(fromAccount.getBalance().subtract(transferAmount));
		fromTransaction.setTransaction_status("SUCCESS");
		fromTransaction.setDescription("Transfer to account ID: " + toAccount.getAccount_id());
		fromTransaction.setTransaction_date(LocalDateTime.now());
		
		TransactionModel toTransaction = new TransactionModel();
		//toTransaction.setTransaction_id(System.currentTimeMillis() + 1);
		toTransaction.setAccount_id(toAccount.getAccount_id());
		toTransaction.setTransaction_reference("TRAN" + (System.currentTimeMillis() + 1));
		toTransaction.setTransaction_type("DEPOSIT");
		toTransaction.setAmount(transferAmount);
		toTransaction.setBefore_balance(toAccount.getBalance());
		toTransaction.setAfter_balance(toAccount.getBalance().add(transferAmount));
		toTransaction.setTransaction_status("SUCCESS");
		toTransaction.setDescription("Transfer from account ID: " + fromAccount.getAccount_id());
		toTransaction.setTransaction_date(LocalDateTime.now());
		
		TransactionModel savedFromTransaction = transactionRepo.save(fromTransaction);
		TransactionModel savedToTransaction = transactionRepo.save(toTransaction);
		
		String ReponseStr = "Successfully transfered amount to other account with transaction ID:" + transferRequest.getFromAccountId();
		
		idempotencyService.SaveIdempotency(idempotency, "COMPLETED", ReponseStr);
		
		return ResponseEntity.ok(ReponseStr);
	}
}
