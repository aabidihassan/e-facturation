package com.electronic.facture;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.electronic.facture.models.AppRole;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.services.AccountServiceImpl;

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
    CommandLineRunner start(AccountServiceImpl accountService){
        return args -> {
            accountService.addNewRole(new AppRole("ADMIN"));
            accountService.addNewRole(new AppRole("USER"));
            accountService.addNewUser(new Utilisateur("hassan", "hassan", new ArrayList<>()));
            accountService.addNewUser(new Utilisateur("aabidi", "hassan", new ArrayList<>()));
            accountService.addNewUser(new Utilisateur("hassanaabidi", "hassan", new ArrayList<>()));
            accountService.affectRoleToUser("hassan", "ADMIN");
            accountService.affectRoleToUser("aabidi", "USER");
            accountService.affectRoleToUser("hassanaabidi", "USER");
            accountService.affectRoleToUser("hassanaabidi", "ADMIN");

        };
    }

}
