package com.electronic.facture.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Fournisseur {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_fournisseur;
	
	private String reference;
	private String type;
	private String raison;
	private String nom;
	private String telephone;
	private String adresse;
	private String email;
	private String pays;
	private String region;
	private String ville;
	private long code_postal;
	
	@ManyToOne @JsonIgnoreProperties("client")
	private Entreprise entreprise;

}
