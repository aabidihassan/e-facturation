package com.electronic.facture.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.electronic.facture.models.ServiceApp;

@Repository
public interface ServiceRepo extends JpaRepository<ServiceApp, Long>{

}
