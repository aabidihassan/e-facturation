package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Facture_statut {
	
	@Id @Column(length = 10)
	private String statut;
	
	@OneToMany(mappedBy = "statut")
	private List<Facture> factures = new ArrayList<Facture>();

}
