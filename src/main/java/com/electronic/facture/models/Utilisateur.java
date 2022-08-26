package com.electronic.facture.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Utilisateur {

    @Id
    @Column(length = 30)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private List<AppRole> roles = new ArrayList<>();
    @OneToOne @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Entreprise entreprise;
    
    public Utilisateur(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
}
