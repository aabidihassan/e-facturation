package com.electronic.facture.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.electronic.facture.models.Produit;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.ProduitRepo;
import com.google.gson.Gson;

@Service
public class ProduitService {
	
	private ProduitRepo produitRepo;
	private ReferenceService referenceService;
	public static final String DIRECTORY = "src/main/resources/templates/";
	
	@Autowired
	public ProduitService(ProduitRepo produitRepo, ReferenceService referenceService) {
		this.produitRepo = produitRepo;
		this.referenceService = referenceService;
	}
	
	public List<Produit> getAll(){
		return this.produitRepo.findAll();
	}
	
	public Produit save(String prod, MultipartFile file, Utilisateur user) throws IOException {
		Produit produit = new Gson().fromJson(prod, Produit.class);
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
    	File directory = new File(DIRECTORY + user.getEntreprise().getId_entreprise() + "/produits/");
		if (! directory.exists()) directory.mkdir();
		Path filestorage = Paths.get(DIRECTORY + user.getEntreprise().getId_entreprise() + "/produits/", filename).toAbsolutePath().normalize();
		Files.copy(file.getInputStream(), filestorage);
		produit.setPhoto(user.getEntreprise().getId_entreprise() + "/produits/" + filename);
		if(produit.getReference()==null) {
			produit.setReference("prod" + this.referenceService.get().getProduit());
			this.referenceService.incrementProduit();
		}
		produit.setEntreprise(user.getEntreprise());
		return this.produitRepo.save(produit);
	}
	
	public ResponseEntity<Resource> download(String id, String directory, String file) throws IOException{
		Path filepath = Paths.get(DIRECTORY + id + "/" + directory + "/").toAbsolutePath().normalize().resolve(file);
		if(!Files.exists(filepath)) throw new FileNotFoundException("Le fichier n'est pas trouve");
		Resource resource = new UrlResource(filepath.toUri());
		HttpHeaders headers = new HttpHeaders();
		headers.add("File-Name", resource.getFilename());
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachement;File-Name="+resource.getFilename());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filepath)))
				.headers(headers).body(resource);
	}
	
	public Produit getByReference(String reference) {
		return this.produitRepo.findById(reference).get();
	}
	
	public Produit edit(Produit produit, Utilisateur user) {
		Produit prod = this.getByReference(produit.getReference());
		if(prod != null && prod.getEntreprise().getId_entreprise() == user.getEntreprise().getId_entreprise()) {
			prod.setDescription(produit.getDescription());
			prod.setLibelle(produit.getLibelle());
			prod.setPrix(produit.getPrix());
			this.produitRepo.save(prod);
		}
		return prod;
	}
	
	public void delete(String reference) {
		this.produitRepo.deleteById(reference);
	}

}
