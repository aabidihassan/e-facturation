package com.electronic.facture.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.facture.models.Entreprise;
import com.electronic.facture.services.EntrepriseService;

@RestController
@RequestMapping("api/entreprises")
public class EntrepriseController {
	
	private EntrepriseService entrepriseService;
	
	@Autowired
	public EntrepriseController(EntrepriseService entrepriseService) {
		this.entrepriseService = entrepriseService;
	}
	
	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public List<Entreprise> getAll(){
		return this.entrepriseService.getAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public Entreprise findById(@PathVariable(name = "id") long id){
		return this.entrepriseService.findById(id);
	}

}
