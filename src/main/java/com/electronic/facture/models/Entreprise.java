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
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Entreprise {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_entreprise;
	
	@OneToOne(mappedBy = "entreprise", fetch = FetchType.EAGER) @JsonIgnoreProperties("entreprise")
	private Utilisateur utilisateur;
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
	
	@ManyToOne
	private Categorie categorie;
	
	@OneToMany(mappedBy = "entreprise") @JsonIgnoreProperties("entreprise")
	private List<Client> clients = new ArrayList<Client>();
	
	@OneToMany(mappedBy = "entreprise") @JsonIgnoreProperties("entreprise")
	private List<Fournisseur> fournisseurs = new ArrayList<Fournisseur>();
	
	@OneToMany(mappedBy = "entreprise") @JsonIgnoreProperties("entreprise")
	private List<Modele> modeles = new ArrayList<Modele>();
	
	@OneToMany(mappedBy = "entreprise") @JsonIgnoreProperties("entreprise")
	@Cascade(CascadeType.ALL)
	private List<Produit> produits = new ArrayList<Produit>();
	
	@OneToMany(mappedBy = "entreprise") @JsonIgnoreProperties("entreprise")
	@Cascade(CascadeType.ALL)
	private List<ServiceApp> services = new ArrayList<ServiceApp>();

}
