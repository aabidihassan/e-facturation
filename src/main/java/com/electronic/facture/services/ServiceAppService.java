package com.electronic.facture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.ServiceApp;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.ServiceRepo;

@Service
public class ServiceAppService {
	
	private ServiceRepo serviceRepo;
	private ReferenceService referenceService;
	
	@Autowired
	public ServiceAppService(ServiceRepo serviceRepo, ReferenceService referenceService) {
		this.serviceRepo = serviceRepo;
		this.referenceService = referenceService;
	}
	
	public ServiceApp save(ServiceApp service, Utilisateur user){
		if(service.getReference()==null) {
			service.setReference("serv" + this.referenceService.get().getService());
			this.referenceService.incrementService();
		}
		
		service.setEntreprise(user.getEntreprise());
		return this.serviceRepo.save(service);
	}
	
	public ServiceApp getById(long id) {
		return this.serviceRepo.findById(id).get();
	}
	
	public ServiceApp edit(ServiceApp service, Utilisateur user) {
		ServiceApp s = this.getById(service.getId_service());
		if(s!=null && s.getEntreprise().getId_entreprise() == user.getEntreprise().getId_entreprise()) {
			s.setDescription(service.getDescription());
			s.setLibelle(service.getLibelle());
			s.setPrix(service.getPrix());
			s.setTaux_horaire(service.getTaux_horaire());
			return this.serviceRepo.save(s);
		}
		return null;
	}
	
	public void delete(long id) {
		ServiceApp service = this.getById(id);
		this.serviceRepo.delete(service);
	}

}
