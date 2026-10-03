package com.example.bank.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
@Profile("!demo")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;

	@GetMapping("/getData")
	public List<CustomerModel> getCustomers() {
		return customerService.getAllCustomers();
	}
	
	@PutMapping("/updateCustomer/{customer_id}")
	public CustomerModel updateCustomer(@RequestBody CustomerModel customerModel, @PathVariable Long customer_id) {
		System.out.println("Updating customer with ID: " + customer_id);
		return customerService.updateCustomer(customerModel, customer_id);
	}
	
	@GetMapping("/getCustomerById/{customer_id}")
	public CustomerModel getCustomerById(@PathVariable Long customer_id) {
		return customerService.getCustomerById(customer_id);
	}
	
	@DeleteMapping("/deleteCustomer/{customer_id}")
	public CustomerModel deleteCustomer(@PathVariable Long customer_id) {
		return customerService.deleteCustomer(customer_id);
	}
	
	@PostMapping("/saveCustomer")
	public ResponseEntity<CustomerModel> SaveCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		
		System.out.println("Customer data received" + customerDTO);
		
		CustomerModel customer = new CustomerModel();
		
		customer.setFirst_name(customerDTO.getFirst_name());
		customer.setLast_name(customerDTO.getLast_name());
		customer.setEmail(customerDTO.getEmail());
		customer.setPhone_number(customerDTO.getPhone_number());
		customer.setDate_of_birth(customerDTO.getDate_of_birth());
		customer.setAddress(customerDTO.getAddress());
		customer.setStatus(customerDTO.getStatus());
		customer.setCreated_at(customerDTO.getCreated_at());
		customer.setUpdated_at(customerDTO.getUpdated_at());
		
		System.out.println("Customer data received" + customer);
		return ResponseEntity.ok(customerService.saveCustomer(customer));
	}
	
//	@PostMapping("/saveValidateCustomer")
//	public String SaveValidateCustomer(@Valid @RequestBody CustomerModel customerModel) {
//		customerService.saveCustomer(customerModel);
//		return "Customer saved successfully";
//	}
}
