package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Client {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_client;
	
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
	
	@OneToMany(mappedBy = "client") @JsonIgnoreProperties("client")
	private List<Commande> commandes = new ArrayList<Commande>();
	
	@ManyToOne @JsonIgnoreProperties("client")
	private Entreprise entreprise;

}
