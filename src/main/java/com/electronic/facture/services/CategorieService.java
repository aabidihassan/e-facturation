package com.electronic.facture.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Categorie;
import com.electronic.facture.repositories.CategorieRepo;

@Service
public class CategorieService {
	
	private CategorieRepo categorieRepo;
	
	@Autowired
	public CategorieService(CategorieRepo categorieRepo) {
		this.categorieRepo = categorieRepo;
	}
	
	public List<Categorie> getAll(){
		return this.categorieRepo.findAll();
	}

}
