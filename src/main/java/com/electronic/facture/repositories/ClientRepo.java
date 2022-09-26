package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.Client;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long>{

}
