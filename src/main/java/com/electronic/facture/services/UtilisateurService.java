package com.electronic.facture.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.UtilisateurRepo;

@Service
public class UtilisateurService {
	
	private UtilisateurRepo utilisateurRepo;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UtilisateurService(UtilisateurRepo utilisateurRepo, PasswordEncoder passwordEncoder) {
		this.utilisateurRepo = utilisateurRepo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public Utilisateur addNewUser(Utilisateur user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return utilisateurRepo.save(user);
    }
	
	public Utilisateur loadUserByUsername(String username) {
        return utilisateurRepo.findByUsername(username);
    }

    public List<Utilisateur> listUsers() {
        return utilisateurRepo.findAll();
    }
    
    public Utilisateur edit(Utilisateur user) {
    	return this.addNewUser(user);
    }

}
