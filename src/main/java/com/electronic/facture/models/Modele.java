package com.electronic.facture.models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Modele {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_modele;
	
	private String nom_modelep;
	private boolean prefixe;
	private String description;
	private String position;
	private String casse;
	private String pied;
	private String cl_titre_corps;
	private String cl_txt_corps;
	private String pl_titre_corps;
	private String pl_txt_corps;
	private String cl_titre_entt;
	private String cl_txt_entt;
	private String pl_titre_entt;
	private String pl_txt_entt;
	private String cl_bas;
	private String cl_total;
	private String pl_total;
	private String pl_bas;
	private String style_bordure;
	private String cl_bordure;
	private String cl_template;
	private int taill_titre_entt;
	private int taill_txt_entt;
	private int taill_bas;
	private int taill_total;
	private int taill_titre_corps;
	private int taill_txt_corps;
	private String file;
	
	@ManyToOne @JsonIgnoreProperties("entreprise")
	private Entreprise entreprise;

}
