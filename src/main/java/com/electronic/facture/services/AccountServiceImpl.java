package com.electronic.facture.services;

import com.electronic.facture.models.AppRole;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.RoleRepo;
import com.electronic.facture.repositories.UtilisateurRepo;
import com.google.gson.Gson;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class AccountServiceImpl {

    private RoleRepo roleRepo;
    private UtilisateurRepo utilisateurRepo;
    private PasswordEncoder passwordEncoder;
    public static final String DIRECTORY = "src/main/resources/templates/";

    @Autowired
    public AccountServiceImpl(RoleRepo roleRepo, UtilisateurRepo utilisateurRepo, PasswordEncoder passwordEncoder){
        this.roleRepo = roleRepo;
        this.utilisateurRepo = utilisateurRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void affectRoleToUser(String username, String role) {
        Utilisateur user = utilisateurRepo.findByUsername(username);
        AppRole role1 = roleRepo.findByLibelle(role);
        user.getRoles().add(role1);
        role1.getUsers().add(user);
    }
    
    public void delete(Utilisateur user) {
    	this.utilisateurRepo.delete(user);
    }
    
	public static void resize(String inputImagePath,
            String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
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
    
    public Utilisateur register(String utilisateur, MultipartFile file) throws IOException {
    	Utilisateur user = new Gson().fromJson(utilisateur, Utilisateur.class);
    	user.getRoles().add(new AppRole("USER"));
    	user.setPassword(this.passwordEncoder.encode(user.getPassword()));
    	user = this.utilisateurRepo.save(user);
    	String filename = StringUtils.cleanPath(file.getOriginalFilename());
    	File directory = new File(DIRECTORY + user.getEntreprise().getId_entreprise() + "/logo/");
		if (! directory.exists()) directory.mkdirs();
		Path filestorage = Paths.get(DIRECTORY + user.getEntreprise().getId_entreprise() + "/logo/", filename).toAbsolutePath().normalize();
		Files.copy(file.getInputStream(), filestorage);
		AccountServiceImpl.resize(DIRECTORY + user.getEntreprise().getId_entreprise() + "/logo/" + filename,
				DIRECTORY + user.getEntreprise().getId_entreprise() + "/logo/" + filename, 100, 100);
		user.getEntreprise().setLogo(user.getEntreprise().getId_entreprise() + "/logo/" + filename);
		return this.utilisateurRepo.save(user);
    }
    
}
