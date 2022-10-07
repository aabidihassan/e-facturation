package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Reglement;

@Repository
public interface ReglementRepo extends JpaRepository<Reglement, Long>{

}
