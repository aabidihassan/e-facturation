package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Produit {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_produit;
	
	private String reference;
	
	private String libelle;
	private String description;
	private double prix;
	private int quantite;
	private String photo;
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnore
	private Entreprise entreprise;
	
	@OneToMany(mappedBy = "produit", fetch = FetchType.EAGER) @JsonIgnoreProperties("produit")
	private List<LigneCommande> lignes = new ArrayList<LigneCommande>();

}
