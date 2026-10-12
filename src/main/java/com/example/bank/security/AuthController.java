package com.example.bank.security;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.bank.security.dto.LoginRequest;
import com.example.bank.security.dto.LoginResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;





@RestController
@RequestMapping("/auth")
@Profile("!demo")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthController(
            UserService userService,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
    	
    	this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(
            @RequestBody UserModel user) {

        UserModel savedUser = userService.registerUser(user);

        savedUser.setPassword(null);

        return ResponseEntity.ok(savedUser);
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

    	Authentication authentication =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        	
			String token = jwtService.generateToken(userDetails);
			
			System.out.println("Token values are" + token);
			
		    return ResponseEntity.ok(
                new LoginResponse(
                        "Login successful",
                        token
                )
        );
    } 
    
}