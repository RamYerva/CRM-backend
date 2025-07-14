package com.crmbackend.service;

import java.util.List;

import com.crmbackend.entity.Customer;

public interface CustomerService {
	
	
	 String insertCustomer(Customer customer);
	 
	 List<Customer> getCustomerList();
	 
	 Customer getCustomerById(int id);
	 
	 String updateCustomer(int id,Customer customer);
	 
	 String deleteCustomerById(int id);
	 
	 String insertMultipleCustomers(List<Customer> customers);

}
