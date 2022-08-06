package com.electronic.facture.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Reglement {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_reglement;
	
	private double montant;
	
	@ManyToOne
	private Facture facture;

}
