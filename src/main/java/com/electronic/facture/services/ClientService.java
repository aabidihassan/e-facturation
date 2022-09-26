package com.electronic.facture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Client;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.ClientRepo;

@Service
public class ClientService {
	
	private ClientRepo clientRepo;
	
	@Autowired
	public ClientService(ClientRepo clientRepo) {
		this.clientRepo = clientRepo;
	}
	
	public Client save(Client client, Utilisateur user) {
		client.setEntreprise(user.getEntreprise());
		return this.clientRepo.save(client);
	}
	
	public void delete(long id) {
		this.clientRepo.deleteById(id);
	}
	
	public Client getById(long id) {
		return this.clientRepo.findById(id).get();
	}
	
	public Client edit(Client client) {
		Client old = this.getById(client.getId_client());
		if(old != null) {
			old.setAdresse(client.getAdresse());
			old.setCode_postal(client.getCode_postal());
			old.setEmail(client.getEmail());
			old.setNom(client.getNom());
			old.setPays(client.getPays());
			old.setRaison(client.getRaison());
			old.setRegion(client.getRegion());
			old.setTelephone(client.getTelephone());
			old.setType(client.getType());
			old.setVille(client.getVille());
			this.clientRepo.save(old);
		}
		return old;
	}

}
