package com.example.bank.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@Profile("!demo")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@GetMapping("/GetAccount/{account_id}")
	public ResponseEntity<AccountModel> getAccountById(@PathVariable Integer account_id) {
		// Implement logic to retrieve account by ID
		return ResponseEntity.ok(accountService.getAccountById(account_id)); // Placeholder return
	}
	
	@GetMapping("/GetAllAccounts")
	public ResponseEntity<List<AccountModel>> getAllAccounts() {
		// Implement logic to retrieve all accounts
		return ResponseEntity.ok(accountService.getAllAccounts()); // Placeholder return
	}
	
	@PutMapping("/UpdateAccount/{account_id}")
	public String updateAccount(@PathVariable Integer account_id, @RequestBody AccountModel account) {
		// Implement logic to update account
		accountService.updateAccount(account_id, account);
		return "Account updated successfully"; // Placeholder return
	}
	
	@DeleteMapping("/DeleteAccount/{account_id}")
	public String deleteAccount(Integer account_id) {
		// Implement logic to delete account
		return "Account deleted successfully"; // Placeholder return
	}
}
