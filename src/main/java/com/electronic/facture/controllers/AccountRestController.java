package com.electronic.facture.controllers;

import com.electronic.facture.dto.AffectRoleToUserDto;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.services.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("api/users")
public class AccountRestController {

    private AccountServiceImpl accountService;

    @Autowired
    public AccountRestController(AccountServiceImpl accountService){
        this.accountService = accountService;
    }

    @GetMapping("/")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public List<Utilisateur> listUsers(){
        return accountService.listUsers();
    }

    @PostMapping("/save")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Utilisateur saveUser(@RequestBody Utilisateur user){
        return this.accountService.addNewUser(user);
    }

    @GetMapping("/{username}")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public Utilisateur getUserByUsername(@PathVariable String username){
        return this.accountService.loadUserByUsername(username);
    }

    @PostMapping("roletouser")
    @PostAuthorize("hasAnyAuthority('ADMIN')")
    public void affectRoleToUser(@RequestBody AffectRoleToUserDto affectRoleToUserDto){
        this.accountService.affectRoleToUser(affectRoleToUserDto.getUsername(), affectRoleToUserDto.getRole());
    }

    @GetMapping(path = "/profile")
    @PostAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public Utilisateur profile(Principal principal){
        return accountService.loadUserByUsername(principal.getName());
    }

}
