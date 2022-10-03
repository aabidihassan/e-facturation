package com.electronic.facture.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Utilisateur {

    @Id
    @Column(length = 30)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER) @JsonIgnoreProperties("users")
    @Cascade(CascadeType.ALL)
    private List<AppRole> roles = new ArrayList<>();
    @OneToOne(fetch = FetchType.EAGER) @Cascade(CascadeType.ALL)
    @JsonIgnoreProperties("utilisateur")
    private Entreprise entreprise;
    
    public Utilisateur(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
}
