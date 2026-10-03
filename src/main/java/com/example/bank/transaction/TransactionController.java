package com.example.bank.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@Profile("!demo")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;

	@GetMapping("/getData")
	public List<TransactionModel> getTransactions() {
		return transactionService.getAllTransactions();
	}
	
	@GetMapping("/getLowestSalary")
	public List<TransactionModel> getLowestSalary() {
		return transactionService.getAllTransactions().stream()
				.sorted((t1, t2) -> t1.getAmount().compareTo(t2.getAmount()))
				.limit(5)
				.toList();
	}
	
	
	@PostMapping("/addTransaction")
	public String addTransaction(@RequestBody TransactionModel transaction) {
		return transactionService.addTransaction(transaction);
	}
	
	@PutMapping("/updateTransaction/{id}")
	public String updateTransaction(@PathVariable long id, @RequestBody TransactionModel transaction) {
		return transactionService.updateTransaction(id, transaction);
	}
	
	@PostMapping("/transferAmountOtherAccount")
	public ResponseEntity<String> onTransferAmountOtherAccount(
			@RequestHeader("idempotency-Key") String idempotencyKey,
			@RequestBody TransferRequest transfer
			) {
		return transactionService.onTransferAmountOtherAccount(transfer, idempotencyKey);
	}
}
