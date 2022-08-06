package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Entreprise {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_entreprise;
	
	@OneToOne(mappedBy = "entreprise")
	Utilisateur utilisateur;
	private String raison;
	private String telephone1;
	private String telephone2;
	private String adresse1;
	private String adresse2;
	private String email;
	private String pays;
	private String region;
	private String code_postal;
	private String logo;
	private double taxe;
	
	@ManyToMany
	private List<Categorie> categories = new ArrayList<Categorie>();
	
	@ManyToMany
	private List<Client> clients = new ArrayList<Client>();
	
	@ManyToMany
	private List<Modele> modeles = new ArrayList<Modele>();

}
