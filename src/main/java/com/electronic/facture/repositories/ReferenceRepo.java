package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Reference;

@Repository
public interface ReferenceRepo extends JpaRepository<Reference, Long>{

}
