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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Facture {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long numero;
	
	private String reference;
	private String creation;
	private String validation;
	private String echeance;
	private double ht;
	private double ttc;
	private String statut;
	private double reste;
	
	@ManyToOne(fetch = FetchType.EAGER) @JsonIgnoreProperties("factures")
	private Client client;
	
	@OneToMany(mappedBy = "facture", fetch = FetchType.EAGER) 
	@Fetch(value = FetchMode.SUBSELECT)
	private List<LigneCommande> lignes = new ArrayList<LigneCommande>();
	
	@OneToMany(mappedBy = "facture", fetch = FetchType.EAGER) 
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Reglement> reglements = new ArrayList<Reglement>();
	
}
