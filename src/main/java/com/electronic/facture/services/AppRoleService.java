package com.electronic.facture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.AppRole;
import com.electronic.facture.repositories.RoleRepo;

@Service
public class AppRoleService {
	
	private RoleRepo roleRepo;
	
	@Autowired
	public AppRoleService(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}
	
	public AppRole addNewRole(AppRole role) {
        return this.roleRepo.save(role);
    }

}
