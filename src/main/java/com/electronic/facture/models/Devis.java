package com.electronic.facture.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Devis {
	
	@Id @Column(length = 11)
	private String id_devise;
	
	@ManyToOne
	private Facture facture;

}
