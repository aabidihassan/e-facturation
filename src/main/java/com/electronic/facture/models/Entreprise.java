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
public class Entreprise {
	
	@Id @Column(length = 20)
	private String login;
	
	private String password;
	private String raison;
	private String telephone1;
	private String telephone2;
	private String adresse1;
	private String adresse2;
	private String email;
	private String pays;
	private String region;
	private long code_postal;
	private String categorie;
	private String logo;
	private double taxe;
	
	@ManyToMany
	private List<Client> clients = new ArrayList<Client>();
	
	@ManyToMany
	private List<Modele> modeles = new ArrayList<Modele>();

}
