package com.electronic.facture.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electronic.facture.models.Modele;
import com.electronic.facture.services.ModeleService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("api/modeles")
public class ModeleController {

	public ModeleService modeleService;
	
	@Autowired
	public ModeleController(ModeleService modeleService) {
		this.modeleService = modeleService;
	}
	
	@PostMapping(path = "/")
    @PostAuthorize("hasAnyAuthority('ADMIN', 'USER')")
	public Modele save(@RequestBody Modele modele) {
		return this.modeleService.save(modele);
	}
	
	@GetMapping("/model")
	public void generate() throws JRException, IOException {
		System.out.println(this.modeleService.generateModel());
	}
	
}
