package com.electronic.facture.controllers;

import com.electronic.facture.dto.AffectRoleToUserDto;
import com.electronic.facture.models.AppRole;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.services.AccountServiceImpl;
import com.electronic.facture.services.UtilisateurService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

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
    
    @PostMapping(path = "/register")
    public Utilisateur create(@RequestParam("file") MultipartFile file, @RequestParam("user") String user) throws IOException {
    	return this.accountService.register(user, file);
    }
    
    @DeleteMapping(path = "/delete/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Utilisateur delete(@PathVariable(name = "username") String username) {
    	Utilisateur user = this.utilisateurService.loadUserByUsername(username);
    	if(user!=null)
    		this.accountService.delete(user);
    	return user;
    }
    
    @GetMapping("/logo/{id}/{directory}/{file}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Resource> getFile(@PathVariable("id") String id, @PathVariable("directory") String directory, @PathVariable("file") String file) throws IOException{
		return this.accountService.download(id, directory, file);
	}
    
   @PatchMapping(path = "/modify")
   @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public Utilisateur modify(@RequestBody Utilisateur user, Principal principal) {
	   if(utilisateurService.loadUserByUsername(principal.getName()).getUsername().equals(user.getUsername()) ||
			   utilisateurService.loadUserByUsername(principal.getName()).getRoles().get(0).equals(new AppRole("ADMIN")))
		   return this.utilisateurService.edit(user);
	   return null;
   }

}
