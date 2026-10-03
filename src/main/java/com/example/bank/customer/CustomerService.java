package com.example.bank.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.bank.exception.CustomerNotFoundException;

@Service
@Profile("!demo")
public class CustomerService {
	
	@Autowired
	CustomerRepo customerRepo;
	
	public List<CustomerModel> getAllCustomers() {
		System.out.println("Getting all customers " + customerRepo.findAll());
		List<CustomerModel> customers = customerRepo.findAll();
		customers.forEach(customer -> {
			System.out.println("Customer: " + customer.getFirst_name() 
											+ " " + customer.getLast_name() 
											+ " " + customer.getEmail() 
											+ " " + customer.getPhone_number());
											
		});
		return customerRepo.findAll();
	}
	
	@Cacheable(value = "customers", key="#customer_id")
	public CustomerModel getCustomerById(Long customer_id) {
		 System.out.println("######## DATABASE METHOD CALLED ########");
		return customerRepo.findById(customer_id)
							.orElseThrow(() -> new CustomerNotFoundException(customer_id));
	}

	public CustomerModel saveCustomer(CustomerModel customerModel) {
		// TODO Auto-generated method stub
		return customerRepo.save(customerModel);
	}
	
//	public CustomerModel updateCustomer(CustomerModel customerModel2, Long customer_id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	@CachePut(value="customers", key="#customer_id")
	public CustomerModel updateCustomer(CustomerModel customerModel, Long customer_id) {
		// TODO Auto-generated method stub
		System.out.println("Updating customer with ID: --->" + customerModel);
		
		CustomerModel existingCustomer = customerRepo.findById(customer_id)
				.orElseThrow(() -> new CustomerNotFoundException(Integer.valueOf(customerModel.getCustomerId()).longValue()));
		
		if(existingCustomer.getCustomerId() != null) {
			
			existingCustomer.setFirst_name(customerModel.getFirst_name());
			existingCustomer.setLast_name(customerModel.getLast_name());
			existingCustomer.setEmail(customerModel.getEmail());
			existingCustomer.setPhone_number(customerModel.getPhone_number());
			existingCustomer.setDate_of_birth(customerModel.getDate_of_birth());
			existingCustomer.setAddress(customerModel.getAddress());
			existingCustomer.setStatus(customerModel.getStatus());
			existingCustomer.setCreated_at(customerModel.getCreated_at());
			existingCustomer.setUpdated_at(customerModel.getUpdated_at());
			
			System.out.println("existingCustomer ->" + existingCustomer);
		
			return customerRepo.save(existingCustomer);
		} else {
			return null;
		}
	}

	@CacheEvict(value = "customers", key = "#customer_id")
	public CustomerModel deleteCustomer(Long customer_id) {
		// TODO Auto-generated method stub
		CustomerModel existingCustomer = customerRepo.findById(customer_id)
				.orElseThrow(() -> new CustomerNotFoundException(customer_id));
		if(existingCustomer.getCustomerId() != null) {
			customerRepo.delete(existingCustomer);	
		} else {
			return null;
		}
		return existingCustomer;
	}
}
