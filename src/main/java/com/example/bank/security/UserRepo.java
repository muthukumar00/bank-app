package com.example.bank.security;

import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!demo")
public interface UserRepo extends JpaRepository<UserModel, Long>{
	
	Optional<UserModel> findByUsername(String username);

    boolean existsByUsername(String username);
	

}
