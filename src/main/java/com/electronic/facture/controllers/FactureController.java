package com.electronic.facture.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.facture.models.Facture;
import com.electronic.facture.services.FactureService;
import com.electronic.facture.services.UtilisateurService;

@RestController
@RequestMapping("api/factures")
public class FactureController {
	
	private FactureService factureService;
    private UtilisateurService utilisateurService;
	
	@Autowired
	public FactureController(FactureService factureService, UtilisateurService utilisateurService) {
		this.factureService = factureService;
		this.utilisateurService = utilisateurService;
	}
	
	@PostMapping("/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Facture save(@RequestBody Facture facture) throws Exception {
		return this.factureService.save(facture);
	}
	
	@GetMapping("/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public List<Facture> getByEntreprise(Principal principal){
		return this.factureService.getByEntreprise(utilisateurService.loadUserByUsername(principal.getName()));
	}

}
