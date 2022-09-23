package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Categorie;

@Repository
public interface CategorieRepo extends JpaRepository<Categorie, String>{

}
