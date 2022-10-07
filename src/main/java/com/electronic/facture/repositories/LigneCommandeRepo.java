package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.LigneCommande;

@Repository
public interface LigneCommandeRepo extends JpaRepository<LigneCommande, Long>{

}
