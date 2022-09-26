package com.electronic.facture.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.facture.models.Fournisseur;
import com.electronic.facture.services.FournisseurService;
import com.electronic.facture.services.UtilisateurService;

@RestController
@RequestMapping("api/fournisseurs")
public class FournisseurController {
	
	private FournisseurService fournisseurService;
	private UtilisateurService utilisateurService;
	
	@Autowired
	public FournisseurController(FournisseurService fournisseurService, UtilisateurService utilisateurService) {
		this.fournisseurService = fournisseurService;
		this.utilisateurService = utilisateurService;
	}
	
	@PostMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Fournisseur save(@RequestBody Fournisseur fournisseur, Principal principal){
		return this.fournisseurService.save(fournisseur, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@PutMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Fournisseur edit(@RequestBody Fournisseur fournisseur){
		return this.fournisseurService.edit(fournisseur);
	}
	
	@DeleteMapping(path = "/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
	public void delete(@PathVariable(name = "id") long id){
		this.fournisseurService.delete(id);
	}

}
