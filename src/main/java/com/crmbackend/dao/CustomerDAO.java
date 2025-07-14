package com.crmbackend.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;


import com.crmbackend.entity.Customer;
import com.crmbackend.exception.ResourceNotFoundException;

import jakarta.persistence.EntityManagerFactory;

@Repository
public class CustomerDAO {
	
	
	SessionFactory sessionFactory;
	
	public CustomerDAO(EntityManagerFactory entityManagerFactory) {
	    this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	public String insertCustomer(Customer customer) {
		
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		session.save(customer);
		ts.commit();
		session.close();
		return "Customer insert Succesfully";
	}
	
	public List<Customer> getCustomerList() {
		Session session = sessionFactory.openSession();
		List<Customer> customers =  session.createQuery("from Customer").list();
		session.close();
		return customers;
	}
	
	public Customer getCustomerById(int id) {
		Session session = sessionFactory.openSession();
		Customer customer = session.get(Customer.class, id);
		
		if(customer==null) {
			throw new ResourceNotFoundException("Customer with ID " + id + " not found");
		}
		session.close();
		return customer;
	}
	
	public String updateCustomer(int id, Customer customer) {
		Session session = sessionFactory.openSession();
		
		Transaction transaction = session.beginTransaction();
		try {
		Customer existingCustomer = session.get(Customer.class, id);
		
		if(existingCustomer==null) {
			throw new ResourceNotFoundException("Customer with ID " + id + " not found");
		}
		
		
		existingCustomer.setFirstName(customer.getFirstName());
		existingCustomer.setLastName(customer.getLastName());
		existingCustomer.setEmail(customer.getEmail());
		existingCustomer.setMobileNumber(customer.getMobileNumber());
		existingCustomer.setAge(customer.getAge());
		
		transaction.commit();
		return "customer updated succesfully";
		}
		catch(Exception e) {
			if (transaction != null) transaction.rollback();
	        throw e;
		}
		finally {
			session.close();
		}	
	}
	
	public String deleteCustomerById(int id) {
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		try {
			Customer exist = session.get(Customer.class,id);
			
			if (exist == null) {
			    throw new ResourceNotFoundException("Customer with ID " + id + " not found");
			}
			
			session.delete(exist);
			ts.commit();
			return "customer - " + id + " is succesfully deleted";
		}
		catch(Exception e) {
			if (ts != null) ts.rollback();
	        throw e;
		}
	    finally {
			session.close();
		}
	}
	
	
	public String insertMultipleCustomers(List<Customer> customers) {
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		for(Customer customer : customers) {
			session.save(customer);
		}
		ts.commit();
		session.close();
		return "customers inserted succesfully";
	}

}
