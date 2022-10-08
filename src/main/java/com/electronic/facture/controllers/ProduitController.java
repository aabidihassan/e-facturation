package com.electronic.facture.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.facture.models.Produit;
import com.electronic.facture.services.ProduitService;
import com.electronic.facture.services.UtilisateurService;

@RestController
@RequestMapping("api/produits")
@CrossOrigin("*")
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
	public Produit save(@RequestParam("produit") String produit, @RequestParam("file") MultipartFile file ,Principal principal) throws IOException{
		return this.produitService.save(produit, file, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@GetMapping("/photo/{id}/{directory}/{file}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Resource> getFile(@PathVariable("id") String id, @PathVariable("directory") String directory, @PathVariable("file") String file) throws IOException{
		return this.produitService.download(id, directory, file);
	}
	
	@PutMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('USER')")
	public Produit edit(@RequestBody Produit produit, Principal principal){
		return this.produitService.edit(produit, utilisateurService.loadUserByUsername(principal.getName()));
	}
	
	@GetMapping(path = "/{id}")
    @PostAuthorize("hasAnyAuthority('USER')")
	public void delete(@PathVariable(name = "id") long id){
		this.produitService.delete(id);
	}

}
