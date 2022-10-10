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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class ServiceApp {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_service;
	
	private String reference;
	private String libelle;
	private String description;
	private double taux_horaire;
	
	@OneToMany(mappedBy = "service", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<LigneCommande> lignes = new ArrayList<LigneCommande>();
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnore
	private Entreprise entreprise;
	
}
