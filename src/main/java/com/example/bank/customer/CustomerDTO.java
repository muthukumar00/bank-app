package com.example.bank.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public class CustomerDTO {

	@NotBlank(message = "First name cannot be blank")
	@NotNull(message = "First name cannot be null")
	String first_name; 
	
	@NotBlank(message = "First name cannot be blank")
	@NotNull(message = "First name cannot be null")
	String last_name;
	
	@Email(message = "Invalid email format")
	@NotNull(message = "Email cannot be null")
	@NotBlank(message = "Email cannot be blank")
	String email;
	
	@Pattern(regexp = "^[0-9]{10}$")
	String phone_number; 
	
	 @NotNull
	 @Past
	LocalDate date_of_birth;
	
	@NotBlank(message = "Address cannot be blank")
	String address;
	
	String status; 
	LocalDateTime created_at;  
	LocalDateTime updated_at;
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public LocalDate getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(LocalDate date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}
	
	@Override
	public String toString() {
		return "CustomerDTO [first_name=" + first_name + ", last_name=" + last_name + ", email=" + email
				+ ", phone_number=" + phone_number + ", date_of_birth=" + date_of_birth + ", address=" + address
				+ ", status=" + status + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
	
	
}
