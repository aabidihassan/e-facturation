package com.electronic.facture.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Reference {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_reference;
	
	private int produit;
	private int service;
	private int client;
	private int fournisseur;

}
