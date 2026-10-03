package com.example.bank.customer;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!demo")
public interface CustomerRepo extends JpaRepository<CustomerModel, Long> {

	List<CustomerModel> findAll();
}
