package com.electronic.facture;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.electronic.facture.models.AppRole;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.services.AccountServiceImpl;
import com.electronic.facture.services.AppRoleService;
import com.electronic.facture.services.UtilisateurService;

@SpringBootApplication
public class EFactureApplication {

	public static void main(String[] args) {
		SpringApplication.run(EFactureApplication.class, args);
	}
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    CommandLineRunner start(AccountServiceImpl accountService, AppRoleService appRoleService, UtilisateurService utilisateurService){
        return args -> {
            appRoleService.addNewRole(new AppRole("ADMIN"));
            appRoleService.addNewRole(new AppRole("USER"));
//            utilisateurService.addNewUser(new Utilisateur("hassan", "hassan"));
//            utilisateurService.addNewUser(new Utilisateur("aabidi", "hassan"));
//            accountService.affectRoleToUser("hassan", "ADMIN");
//            accountService.affectRoleToUser("aabidi", "USER");

        };
    }

}
