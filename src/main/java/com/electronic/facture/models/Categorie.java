package com.electronic.facture.models;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Categorie {
	
	@Id @Column(length = 20)
	private String nom_categorie;
	
	@OneToMany(mappedBy = "categorie") @JsonIgnore
	private List<Entreprise> entreprises = new ArrayList<Entreprise>();

}
