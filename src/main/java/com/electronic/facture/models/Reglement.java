package com.electronic.facture.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Reglement {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_reglement;
	
	@ManyToOne
	private Facture facture;

}
