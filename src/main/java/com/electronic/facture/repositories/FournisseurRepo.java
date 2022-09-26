package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Fournisseur;

@Repository
public interface FournisseurRepo extends JpaRepository<Fournisseur, Long>{

}
