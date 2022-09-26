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

import com.electronic.facture.models.Client;
import com.electronic.facture.services.ClientService;
import com.electronic.facture.services.UtilisateurService;

@RestController
@RequestMapping("api/clients")
public class ClientController {
	
	private ClientService clientService;
	private UtilisateurService utilisateurService;
	
	@Autowired
	public ClientController(ClientService clientService, UtilisateurService utilisateurService) {
		this.clientService = clientService;
		this.utilisateurService = utilisateurService;
	}
	
	@PostMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Client save(@RequestBody Client client, Principal principal){
		return this.clientService.save(client, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@PutMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Client edit(@RequestBody Client client){
		return this.clientService.edit(client);
	}
	
	@DeleteMapping(path = "/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
	public void delete(@PathVariable(name = "id") long id){
		this.clientService.delete(id);
	}

}
