package com.electronic.facture.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Produit;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.ProduitRepo;

@Service
public class ProduitService {
	
	private ProduitRepo produitRepo;
	
	@Autowired
	public ProduitService(ProduitRepo produitRepo) {
		this.produitRepo = produitRepo;
	}
	
	public List<Produit> getAll(){
		return this.produitRepo.findAll();
	}
	
	public Produit save(Produit produit, Utilisateur user) {
		produit.setEntreprise(user.getEntreprise());
		return this.produitRepo.save(produit);
	}
	
	public Produit getByReference(String reference) {
		return this.produitRepo.findById(reference).get();
	}
	
	public Produit edit(Produit produit, Utilisateur user) {
		Produit prod = this.getByReference(produit.getReference());
		if(prod != null && prod.getEntreprise().getId_entreprise() == user.getEntreprise().getId_entreprise()) {
			prod.setDescription(produit.getDescription());
			prod.setLibelle(produit.getLibelle());
			prod.setPrix(produit.getPrix());
			this.save(prod, user);
		}
		return prod;
	}
	
	public void delete(String reference) {
		Produit produit = this.getByReference(reference);
		this.produitRepo.delete(produit);
	}

}
