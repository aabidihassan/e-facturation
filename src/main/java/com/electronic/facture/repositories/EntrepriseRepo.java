package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Entreprise;

@Repository
public interface EntrepriseRepo extends JpaRepository<Entreprise, Long>{

}
