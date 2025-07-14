package com.crmbackend.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.crmbackend.dao.LeadDAO;
import com.crmbackend.dao.UserDAO;
import com.crmbackend.entity.Lead;
import com.crmbackend.entity.User;
import com.crmbackend.service.LeadService;

@Service
public class LeadServiceImpl implements LeadService {
	
	
	private final LeadDAO leadDAO;
	private final UserDAO userDAO;
	public LeadServiceImpl(LeadDAO leadDAO,UserDAO userDAO) {
		this.leadDAO = leadDAO;
		this.userDAO = userDAO;
	}

	@Override
	public String createLead(Lead lead) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	   
	    System.out.println("==> Authenticated User: " + auth.getName());
	    System.out.println("==> Authorities: " + auth.getAuthorities());

	    String username = auth.getName();
	    User user = userDAO.getUserByUsername(username); 

	    lead.setAssignedTo(user); 
	    leadDAO.createLead(lead);
	    return "Lead Inserted Successfully.";
	}


	@Override
	public List<Lead> getAllLeads() {
		return leadDAO.getAllLeads();
	}
     
	@Override
	public Lead getLeadById(int id) {
		return leadDAO.getLeadById(id);
	}
	
	@Override
    public String updateLead(int id, Lead updatedLead) {
        return leadDAO.updateLead(id, updatedLead);
    }

    @Override
    public String deleteLead(int id) {
        return leadDAO.deleteLead(id);
    }

    @Override
    public List<Lead> getLeadsByAssignedUsername(String username) {
        return leadDAO.getLeadsByAssignedUsername(username);
    }

    @Override
    public Lead getOwnLead(String username, int leadId) {
        return leadDAO.getOwnLead(username, leadId);
    }

}
