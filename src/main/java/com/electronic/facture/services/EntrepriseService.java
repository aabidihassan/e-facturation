package com.electronic.facture.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Entreprise;
import com.electronic.facture.repositories.EntrepriseRepo;

@Service
public class EntrepriseService {
	
	private EntrepriseRepo entrepriseRepo;
	
	@Autowired
	public EntrepriseService(EntrepriseRepo entrepriseRepo) {
		this.entrepriseRepo = entrepriseRepo;
	}
	
	public List<Entreprise> getAll(){
		return this.entrepriseRepo.findAll();
	}
	
	public Entreprise findById(long id) {
		return this.entrepriseRepo.findById(id).get();
	}

}
