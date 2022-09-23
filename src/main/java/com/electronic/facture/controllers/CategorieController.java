package com.electronic.facture.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.facture.models.Categorie;
import com.electronic.facture.services.CategorieService;

@RestController
@RequestMapping("api/categories")
public class CategorieController {
	
	private CategorieService categorieService;
	
	@Autowired
	public CategorieController(CategorieService categorieService) {
		this.categorieService = categorieService;
	}
	
	@GetMapping("/")
	public List<Categorie> getAll(){
		return this.categorieService.getAll();
	}

}
