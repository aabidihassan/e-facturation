package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Commande {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_cmd;
	
	@ManyToMany(mappedBy = "commandes")
	private List<Produit> produits = new ArrayList<Produit>();
	
	@ManyToOne
	private Client client;

}
