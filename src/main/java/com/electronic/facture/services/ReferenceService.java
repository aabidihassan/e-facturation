package com.electronic.facture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Reference;
import com.electronic.facture.repositories.ReferenceRepo;

@Service
public class ReferenceService {
	
	private ReferenceRepo referenceRepo;
	
	@Autowired
	public ReferenceService(ReferenceRepo referenceRepo) {
		this.referenceRepo = referenceRepo;
	}
	
	public Reference get() {
		return this.referenceRepo.findById((long) 1).get();
	}
	
	public Reference edit(Reference reference) {
		return this.referenceRepo.save(reference);
	}
	
	public void incrementClient() {
		Reference ref = this.get();
		ref.setClient(ref.getClient()+1);
		this.referenceRepo.save(ref);
	}
	
	public void incrementFournisseur() {
		Reference ref = this.get();
		ref.setFournisseur(ref.getFournisseur()+1);
		this.referenceRepo.save(ref);
	}
	
	public void incrementProduit() {
		Reference ref = this.get();
		ref.setProduit(ref.getProduit()+1);
		this.referenceRepo.save(ref);
	}
	
	public void incrementService() {
		Reference ref = this.get();
		ref.setService(ref.getService()+1);
		this.referenceRepo.save(ref);
	}

}
