package com.electronic.facture.dto;

import com.electronic.facture.models.Facture;
import com.electronic.facture.models.Modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class FactureSend {
	
	private Facture facture;
	private Modele modele;
	private String message;
	private String object;

}
