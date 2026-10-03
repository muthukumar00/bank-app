package com.example.bank.idempotency;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdempotencyRepo extends JpaRepository<IdempotencyModel, Long> {

	Optional<IdempotencyModel> findByIdempotencyKey(String idempotencyKey);
}
