package com.electronic.facture.dto;

import com.electronic.facture.models.Facture;
import com.electronic.facture.models.Modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class FactureCreation {
	
	private Facture facture;
	private Modele modele;

}
