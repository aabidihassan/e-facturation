package com.electronic.facture;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.electronic.facture.models.AppRole;
import com.electronic.facture.models.Categorie;
import com.electronic.facture.models.Reference;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.CategorieRepo;
import com.electronic.facture.repositories.ReferenceRepo;
import com.electronic.facture.services.AccountServiceImpl;
import com.electronic.facture.services.AppRoleService;
import com.electronic.facture.services.UtilisateurService;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class EFactureApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFactureApplication.class, args);
	}
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    CommandLineRunner start(AccountServiceImpl accountService, ReferenceRepo referenceRepo ,CategorieRepo categorieRepo ,AppRoleService appRoleService, UtilisateurService utilisateurService){
        return args -> {
            appRoleService.addNewRole(new AppRole("ADMIN"));
            appRoleService.addNewRole(new AppRole("USER"));
            categorieRepo.save(new Categorie("SERVICES", null));
            categorieRepo.save(new Categorie("PRODUITS", null));
            categorieRepo.save(new Categorie("PRODUITS & SERVICES", null));
            if(!referenceRepo.existsById((long)1)) {
            	referenceRepo.save(new Reference());
            }
//            utilisateurService.addNewUser(new Utilisateur("hassan", "hassan"));
//            utilisateurService.addNewUser(new Utilisateur("aabidi", "hassan"));
//            accountService.affectRoleToUser("hassan", "ADMIN");
//            accountService.affectRoleToUser("aabidi", "USER");

        };
    }

}
