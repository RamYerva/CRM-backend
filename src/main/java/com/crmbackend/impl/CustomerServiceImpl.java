package com.crmbackend.impl;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crmbackend.dao.CustomerDAO;
import com.crmbackend.entity.Customer;
import com.crmbackend.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	
	CustomerDAO customerDAO;
	public CustomerServiceImpl(CustomerDAO customerDAO) {
		super();
		this.customerDAO = customerDAO;
	}

	@Override
	public String insertCustomer(Customer customer) {
		String msg = customerDAO.insertCustomer(customer);
		return msg;
	}

	@Override
	public List<Customer> getCustomerList() {
		List<Customer> customers = customerDAO.getCustomerList();
		return customers;
	}
	
	@Override
	public Customer getCustomerById(int id) {
		Customer customer = customerDAO.getCustomerById(id);
		return customer;
	}

	@Override
	public String updateCustomer(int id, Customer customer) {
        return customerDAO.updateCustomer(id, customer); 
    }

	@Override
	public String deleteCustomerById(int id) {
		return customerDAO.deleteCustomerById(id);
	}

	@Override
	public String insertMultipleCustomers(List<Customer> customers) {
		return customerDAO.insertMultipleCustomers(customers);
	}
	
	

	
	

}
