package com.electronic.facture.controllers;

import java.io.IOException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@PostMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Modele save(@RequestBody Modele modele, Principal principal) throws IOException {
		return this.modeleService.save(modele, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@GetMapping("/{id}/{filename}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename, @PathVariable("id") long id) throws IOException{
		return this.modeleService.download(filename, id);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public void delete(@PathVariable("id") long id){
		this.modeleService.delete(id);
	}
	
}
