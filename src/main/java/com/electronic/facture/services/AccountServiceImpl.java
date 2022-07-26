package com.electronic.facture.services;

import com.electronic.facture.models.AppRole;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.RoleRepo;
import com.electronic.facture.repositories.UtilisateurRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountServiceImpl {

    private RoleRepo roleRepo;
    private UtilisateurRepo utilisateurRepo;
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(@Autowired RoleRepo roleRepo, @Autowired UtilisateurRepo utilisateurRepo, @Autowired PasswordEncoder passwordEncoder){
        this.roleRepo = roleRepo;
        this.utilisateurRepo = utilisateurRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Utilisateur addNewUser(Utilisateur user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return utilisateurRepo.save(user);
    }

    public AppRole addNewRole(AppRole role) {
        return roleRepo.save(role);
    }

    public void affectRoleToUser(String username, String role) {
        Utilisateur user = utilisateurRepo.findByUsername(username);
        AppRole role1 = roleRepo.findByLibelle(role);
        user.getRoles().add(role1);
        role1.getUsers().add(user);
    }

    public Utilisateur loadUserByUsername(String username) {
        return utilisateurRepo.findByUsername(username);
    }

    public List<Utilisateur> listUsers() {
        return utilisateurRepo.findAll();
    }
}
