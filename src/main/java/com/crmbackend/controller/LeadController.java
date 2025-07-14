package com.crmbackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crmbackend.entity.Lead;
import com.crmbackend.impl.LeadServiceImpl;
import com.crmbackend.service.LeadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
	
	private final LeadService leadService;
	public LeadController(LeadService leadService) {
		this.leadService = leadService;
	}
	
	@PostMapping("/insert")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER', 'SALES', 'USER')")
	public ResponseEntity<String> createLead(@Valid @RequestBody Lead lead, Authentication auth) {
		String result = leadService.createLead(lead);
		System.out.println(">> Inserting lead by user: " + auth.getName());
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
	public ResponseEntity<List<Lead>> getAllLeads(){
		List<Lead> leads = leadService.getAllLeads();
		if (leads.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
		return ResponseEntity.ok(leads);
	}
	
	@GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<Lead> getLeadById(@PathVariable int id) {
        return ResponseEntity.ok(leadService.getLeadById(id));
    }

   
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<String> updateLead(@PathVariable int id, @RequestBody Lead lead) {
        String result = leadService.updateLead(id, lead);
        return ResponseEntity.ok(result);
    }

   
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteLead(@PathVariable int id) {
        String result = leadService.deleteLead(id);
        return ResponseEntity.ok(result);
    }

    
    @GetMapping("/my-leads")
    @PreAuthorize("hasAnyRole('USER', 'SALES', 'MANAGER', 'ADMIN')")
    public ResponseEntity<List<Lead>> getMyLeads(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(leadService.getLeadsByAssignedUsername(username));
    }

   
    @GetMapping("/my-leads/{id}")
    @PreAuthorize("hasAnyRole('USER', 'SALES', 'MANAGER', 'ADMIN')")
    public ResponseEntity<Lead> getOwnLead(@PathVariable int id, Authentication authentication) {
        String username = authentication.getName();
        Lead lead = leadService.getOwnLead(username, id);
        return ResponseEntity.ok(lead);
    }
	
	
	
	

}
