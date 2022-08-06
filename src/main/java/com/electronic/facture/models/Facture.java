package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Facture {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long numero;
	
	private Date creation;
	private Date validation;
	private Date echeance;
	private String destination;
	private String livraison;
	private String email;
	private String telephone;
	private double taxe1;
	private double taxe2;
	private double remise;
	
	@ManyToOne
	private Facture_statut statut;
	
	@OneToMany(mappedBy = "facture")
	private List<Commande> commandes = new ArrayList<Commande>();
	
	@OneToMany(mappedBy = "facture")
	private List<Reglement> reglements = new ArrayList<Reglement>();
	
	@OneToMany(mappedBy = "facture")
	private List<Devis> devises = new ArrayList<>();
	
	@OneToMany(mappedBy = "facture")
	private List<ACompte> acomptes = new ArrayList<>();
	
}
