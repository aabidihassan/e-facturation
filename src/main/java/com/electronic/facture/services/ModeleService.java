package com.electronic.facture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Modele;
import com.electronic.facture.repositories.ModeleRepository;

@Service
public class ModeleService {
	
	public ModeleRepository modeleRepository;
	
	@Autowired
	public ModeleService(ModeleRepository modeleRepository) {
		this.modeleRepository = modeleRepository;
	}
	
	public Modele save(Modele modele) {
		return this.modeleRepository.save(modele);
	}

}
