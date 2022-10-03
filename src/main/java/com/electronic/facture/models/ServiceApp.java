package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class ServiceApp {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_service;
	
	private String reference;
	private String libelle;
	private String description;
	private double prix;
	private double taux_horaire;
	
	@ManyToMany
	private List<Commande> commandes = new ArrayList<Commande>();
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnoreProperties("services")
	@Cascade(CascadeType.ALL)
	private Entreprise entreprise;
	
}
