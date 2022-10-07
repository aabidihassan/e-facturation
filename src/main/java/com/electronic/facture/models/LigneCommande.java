package com.electronic.facture.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class LigneCommande {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_ligne;
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnoreProperties("lignes")
	private Produit produit;
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnoreProperties("lignes")
	private ServiceApp service;
	
	private int qte;
	private double ht;
	private double ttc;
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnore
	private Facture facture;

}
