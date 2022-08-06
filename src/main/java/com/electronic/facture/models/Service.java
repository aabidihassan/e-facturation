package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Service {

	@Id @Column(length = 20)
	private String id_service;
	
	private String libelle;
	private String description;
	private double prix;
	private double taux_horaire;
	@ManyToMany
	private List<Commande> commandes = new ArrayList<Commande>();
	
}
