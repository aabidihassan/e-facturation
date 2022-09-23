package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Produit;

@Repository
public interface ProduitRepo extends JpaRepository<Produit, String>{

}
