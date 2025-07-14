package com.crmbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crmbackend.entity.Customer;
import com.crmbackend.service.CustomerService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	
	CustomerService customerService;
	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@PostMapping("/insert")
	public ResponseEntity<String> insertCustomer(@Valid @RequestBody Customer customer) {
		String msg = customerService.insertCustomer(customer);
	 	return ResponseEntity.status(HttpStatus.CREATED).body(msg);
	}
	
	@GetMapping
	public ResponseEntity<List<Customer>> getCustomerList(){
		List<Customer> customers =  customerService.getCustomerList();
		if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
	    Customer customer = customerService.getCustomerById(id);
	    return ResponseEntity.ok(customer);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateCustomer(@PathVariable int id, @Valid @RequestBody Customer customer) {
		 String result = customerService.updateCustomer(id, customer);
	        if (result.contains("not found")) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	        }
	        return ResponseEntity.ok(result);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable int id) {
		String result = customerService.deleteCustomerById(id);
        if (result.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<?> insertMultipleCustomers(@Valid @RequestBody List<Customer> customer){
		String result = customerService.insertMultipleCustomers(customer);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	

}
