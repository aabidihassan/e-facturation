package com.electronic.facture.controllers;

import java.security.Principal;
import java.util.List;

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

import com.electronic.facture.models.Produit;
import com.electronic.facture.services.ProduitService;
import com.electronic.facture.services.UtilisateurService;

@RestController
@RequestMapping("api/produits")
public class ProduitController {
	
	private ProduitService produitService;
	private UtilisateurService utilisateurService;
	
	@Autowired
	public ProduitController(ProduitService produitService, UtilisateurService utilisateurService) {
		this.produitService = produitService;
		this.utilisateurService = utilisateurService;
	}
	
	@GetMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
	public List<Produit> getAll(){
		return this.produitService.getAll();
	}
	
	@PostMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Produit save(@RequestBody Produit produit, Principal principal){
		return this.produitService.save(produit, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@PutMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Produit edit(@RequestBody Produit produit, Principal principal){
		return this.produitService.edit(produit, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@DeleteMapping(path = "/{reference}")
    @PostAuthorize("hasAnyAuthority('USER')")
	public void delete(@PathVariable String reference){
		this.produitService.delete(reference);
	}

}
