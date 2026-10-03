package com.example.bank.idempotency;



import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Profile("!demo")
public class IdempotencyService {
	
	private final IdempotencyRepo idempotency;
	
	public IdempotencyService(IdempotencyRepo idempotency) {
		// TODO Auto-generated constructor stub
		this.idempotency = idempotency;
	}
	
	public Optional<IdempotencyModel> getfindById(String idempotencyKey) {
		return idempotency.findByIdempotencyKey(idempotencyKey);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public IdempotencyModel SaveIdempotency(String idempotencyKey, String status, String responseData) {
		
		IdempotencyModel idempotencyModel = new IdempotencyModel();
		
		if(idempotencyKey != null && status != null && responseData != null) {
			idempotencyModel.setIdempotencyKey(idempotencyKey);
			idempotencyModel.setResponseData(responseData);
			idempotencyModel.setStatus(status);
			idempotencyModel.setCreatedAt(LocalDateTime.now());
		}
		return idempotency.save(idempotencyModel);
	}

	
}
