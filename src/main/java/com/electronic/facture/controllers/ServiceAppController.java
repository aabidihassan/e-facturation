package com.electronic.facture.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.facture.models.ServiceApp;
import com.electronic.facture.services.ServiceAppService;
import com.electronic.facture.services.UtilisateurService;

@RestController
@RequestMapping("api/services")
public class ServiceAppController {
	
	private ServiceAppService serviceAppService;
	private UtilisateurService utilisateurService;
	
	@Autowired
	public ServiceAppController(ServiceAppService serviceAppService, UtilisateurService utilisateurService) {
		this.serviceAppService = serviceAppService;
		this.utilisateurService = utilisateurService;
	}
	
	@PostMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public ServiceApp save(@RequestBody ServiceApp service, Principal principal){
		return this.serviceAppService.save(service, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@PutMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public ServiceApp edit(@RequestBody ServiceApp service, Principal principal){
		return this.serviceAppService.edit(service, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@GetMapping(path = "/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
	public void delete(@PathVariable long id){
		this.serviceAppService.delete(id);
	}

}
