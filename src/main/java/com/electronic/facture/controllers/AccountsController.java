package com.electronic.facture.controllers;

import com.electronic.facture.dto.AffectRoleToUserDto;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.services.AccountServiceImpl;
import com.electronic.facture.services.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("api/accounts")
public class AccountsController {

    private AccountServiceImpl accountService;
    private UtilisateurService utilisateurService;

    @Autowired
    public AccountsController(AccountServiceImpl accountService, UtilisateurService utilisateurService){
        this.accountService = accountService;
        this.utilisateurService = utilisateurService;
    }

    @PostMapping("roletouser")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void affectRoleToUser(@RequestBody AffectRoleToUserDto affectRoleToUserDto){
        this.accountService.affectRoleToUser(affectRoleToUserDto.getUsername(), affectRoleToUserDto.getRole());
    }

    @GetMapping(path = "/profile")
    @PostAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Utilisateur profile(Principal principal){
        return utilisateurService.loadUserByUsername(principal.getName());
    }

}
