package com.example.bank.account;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.bank.exception.AccountNotFoundException;

@Service
@Profile("!demo")
public class AccountService {

	//@Autowired
	private final AccountRepo accountRepo;
	
	AccountService( AccountRepo accountRep) {
		this.accountRepo = accountRep;
	}
	
	List<AccountModel> getAllAccounts() {
		return accountRepo.findAll();
	}
	
	AccountModel getAccountById(Integer account_id) {
		return accountRepo.findById(account_id).orElseThrow(() -> new AccountNotFoundException(account_id));
	}
	
	String createAccount(AccountModel account) {
		accountRepo.save(account);
		return "Account created successfully";
	}
	
	String updateAccount(Integer account_id, AccountModel account) {
		AccountModel existingAccount = accountRepo.findById(account_id).orElse(null);
		if (existingAccount != null) {
			existingAccount.setCustomer_id(account.getCustomer_id());
			existingAccount.setAccount_number(account.getAccount_number());
			existingAccount.setAccount_type(account.getAccount_type());
			existingAccount.setCurrency(account.getCurrency());
			existingAccount.setBalance(account.getBalance());
			existingAccount.setStatus(account.getStatus());
			accountRepo.save(existingAccount);
			return "Account updated successfully";
		} else {
			return "Account not found";
		}
	}
}
