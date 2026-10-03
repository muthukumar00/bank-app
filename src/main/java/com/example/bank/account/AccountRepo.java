package com.example.bank.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bank.transaction.TransactionModel;

import jakarta.persistence.LockModeType;

public interface AccountRepo extends JpaRepository<AccountModel, Integer> {
	
	// Custom query methods can be defined here if needed
	// For example, to find accounts by customer_id:
	
	/*@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountModel a WHERE a.account_id = :accountId")
    Optional<AccountModel> findByIdForUpdate(
            @Param("accountId") Integer accountId);
            */

	@Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AccountModel a WHERE a.account_id = :accountId")
	Optional<AccountModel> findByIdForUpdate( @Param("accountId") Integer accountId);
	
}
