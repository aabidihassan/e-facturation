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
	private EmailSenderService emailSenderService;
	
	@Autowired
	public UtilisateurService(UtilisateurRepo utilisateurRepo, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService) {
		this.utilisateurRepo = utilisateurRepo;
		this.passwordEncoder = passwordEncoder;
		this.emailSenderService = emailSenderService;
	}
	
	public Utilisateur addNewUser(Utilisateur user) {
		this.emailSenderService.sendEmail(user.getEntreprise().getEmail(), "Votre Compte", 
    			"Bonjour "+user.getEntreprise().getRaison()+
    			", \nLe compte de votre entreprise "
    					+"\" est bien cree. \nVous pouvez authentifier en utilisant: \nNom d'utilisateur : "
    					+ user.getUsername() + "\nMot de passe : "+user.getPassword()
    					+ "\nBonne reception.");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return utilisateurRepo.save(user);
    }
	
	public Utilisateur save(Utilisateur user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return this.utilisateurRepo.save(user);
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
