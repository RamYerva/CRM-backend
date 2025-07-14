package com.crmbackend.dao;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.crmbackend.entity.Lead;
import com.crmbackend.entity.User;
import com.crmbackend.exception.ResourceNotFoundException;

import java.util.List;

import org.hibernate.*;

@Repository
public class LeadDAO {
	
	
	private final SessionFactory sessionFactory;
	
	public LeadDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public String createLead(Lead lead) {
		
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		session.save(lead);
		ts.commit();
		session.close();
		return "Lead Inserted Succesfully";
	}
	
	public List<Lead> getAllLeads(){
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		List<Lead> leads = session.createQuery("from Lead", Lead.class).list();	
		session.close();
	    return leads;
	}
	
	public Lead getLeadById(int id) {
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		Lead lead = session.get(Lead.class, id);
		if (lead == null) {
            throw new ResourceNotFoundException("User with ID " + id + " not found.");
        }
		session.close();
	    return lead;
	}
	
	public String updateLead(int id, Lead updatedLead) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Lead existing = session.get(Lead.class, id);

        if (existing == null) {
            throw new ResourceNotFoundException("Lead with ID " + id + " not found.");
        }

        existing.setName(updatedLead.getName());
        existing.setEmail(updatedLead.getEmail());
        existing.setPhone(updatedLead.getPhone());
        existing.setStatus(updatedLead.getStatus());
        existing.setSource(updatedLead.getSource());
        existing.setAssignedTo(updatedLead.getAssignedTo());

        tx.commit();
        session.close();
        return "Lead updated successfully.";
    }

    public String deleteLead(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Lead lead = session.get(Lead.class, id);
        if (lead == null) {
            throw new ResourceNotFoundException("Lead with ID " + id + " not found.");
        }
        session.delete(lead);
        tx.commit();
        session.close();
        return "Lead deleted successfully.";
    }

    public List<Lead> getLeadsByAssignedUsername(String username) {
        Session session = sessionFactory.openSession();
        List<Lead> leads = session.createQuery(
                "FROM Lead l WHERE l.assignedTo.username = :username", Lead.class)
                .setParameter("username", username)
                .list();
        session.close();
        return leads;
    }

    public Lead getOwnLead(String username, int leadId) {
        Session session = sessionFactory.openSession();
        Lead lead = session.createQuery(
                "FROM Lead l WHERE l.assignedTo.username = :username AND l.id = :id", Lead.class)
                .setParameter("username", username)
                .setParameter("id", leadId)
                .uniqueResult();
        session.close();

        if (lead == null) {
            throw new ResourceNotFoundException("Lead not found or access denied.");
        }

        return lead;
    }
	
	

}
