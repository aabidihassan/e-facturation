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
public class Categorie {
	
	@Id @Column(length = 10)
	private String nom_categorie;
	
	@ManyToMany(mappedBy = "categories")
	private List<Entreprise> entreprises = new ArrayList<Entreprise>();

}
