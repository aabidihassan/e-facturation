package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Produit {
	
	@Id @Column(length = 20)
	private String reference;
	
	private String libelle;
	private String description;
	private double prix;
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnoreProperties("produits")
	@Cascade(CascadeType.ALL)
	private Entreprise entreprise;
	
	@ManyToMany @JsonIgnoreProperties("commandes")
	private List<Commande> commandes = new ArrayList<Commande>();

}
