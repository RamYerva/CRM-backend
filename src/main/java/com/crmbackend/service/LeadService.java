package com.crmbackend.service;

import com.crmbackend.entity.Lead;

import java.util.List;


public interface LeadService {

    String createLead(Lead lead);

    List<Lead> getAllLeads();

    Lead getLeadById(int id);

    String updateLead(int id, Lead updatedLead);

    String deleteLead(int id);

    List<Lead> getLeadsByAssignedUsername(String username);

    Lead getOwnLead(String username, int leadId);
}

