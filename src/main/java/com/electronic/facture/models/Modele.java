package com.electronic.facture.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Modele {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_modele;
	
	private String libelle;
	private boolean etat;
	private String cl_titre_cps;
	private String cl_txt_cps;
	private String pl_titre_cps;
	private String pl_txt_cps;
	private String cl_titre_entt;
	private String cl_txt_entt;
	private String pl_titre_entt;
	private String pl_txt_entt;
	private String pl_bas;
	private String brd_tab;
	private String cl_tab;
	private String cl_total;
	private String pl_total;
	private boolean classe;
	private String dispo_logo;
	private String dispo_ref;
	private String dispo_dest;
	private String dispo_dates;
	private String dispo_entt;
	private String dispo_liv;
	
	@ManyToMany(mappedBy = "modeles")
	private List<Entreprise> entreprises = new ArrayList<Entreprise>();

}
