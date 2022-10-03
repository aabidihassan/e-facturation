package com.electronic.facture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Fournisseur;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.FournisseurRepo;

@Service
public class FournisseurService {
	
	private FournisseurRepo fournisseurRepo;
	private ReferenceService referenceService;
	
	@Autowired
	public FournisseurService(FournisseurRepo fournisseurRepo, ReferenceService referenceService) {
		this.fournisseurRepo = fournisseurRepo;
		this.referenceService = referenceService;
	}
	
	public Fournisseur save(Fournisseur fournisseur, Utilisateur user) {
		if(fournisseur.getReference()==null) {
			fournisseur.setReference("four" + this.referenceService.get().getFournisseur());
			this.referenceService.incrementFournisseur();
		}
		
		fournisseur.setEntreprise(user.getEntreprise());
		return this.fournisseurRepo.save(fournisseur);
	}
	
	public void delete(long id) {
		this.fournisseurRepo.deleteById(id);
	}
	
	public Fournisseur getById(long id) {
		return this.fournisseurRepo.findById(id).get();
	}
	
	public Fournisseur edit(Fournisseur fournisseur) {
		Fournisseur old = this.getById(fournisseur.getId_fournisseur());
		if(old != null) {
			old.setAdresse(fournisseur.getAdresse());
			old.setCode_postal(fournisseur.getCode_postal());
			old.setEmail(fournisseur.getEmail());
			old.setNom(fournisseur.getNom());
			old.setPays(fournisseur.getPays());
			old.setRaison(fournisseur.getRaison());
			old.setRegion(fournisseur.getRegion());
			old.setTelephone(fournisseur.getTelephone());
			old.setType(fournisseur.getType());
			old.setVille(fournisseur.getVille());
			this.fournisseurRepo.save(old);
		}
		return old;
	}

}
