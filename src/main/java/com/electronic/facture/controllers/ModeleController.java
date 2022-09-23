package com.electronic.facture.controllers;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.facture.models.Modele;
import com.electronic.facture.services.ModeleService;
import com.electronic.facture.services.UtilisateurService;


@RestController
@RequestMapping("api/modeles")
public class ModeleController {

	private ModeleService modeleService;
    private UtilisateurService utilisateurService;
	
	@Autowired
	public ModeleController(ModeleService modeleService, UtilisateurService utilisateurService) {
		this.modeleService = modeleService;
		this.utilisateurService = utilisateurService;
	}
	
//	@PostMapping(path = "/")
//    @PostAuthorize("hasAnyAuthority('ADMIN', 'USER')")
//	public Modele save(@RequestBody Modele modele) {
//		return this.modeleService.save(modele);
//	}
	
	@PostMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Modele save(@RequestBody Modele modele, Principal principal) throws IOException {
		return this.modeleService.save(modele, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
//	@GetMapping("/model")
//	public void generate() throws JRException, IOException {
//		System.out.println(this.modeleService.generateModel());
//	}
	
}
