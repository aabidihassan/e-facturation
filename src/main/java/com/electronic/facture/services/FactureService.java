package com.electronic.facture.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electronic.facture.models.Client;
import com.electronic.facture.models.Facture;
import com.electronic.facture.models.LigneCommande;
import com.electronic.facture.models.Modele;
import com.electronic.facture.models.Reglement;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.FactureRepo;
import com.electronic.facture.repositories.LigneCommandeRepo;
import com.electronic.facture.repositories.ReglementRepo;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@Service
public class FactureService {

	private FactureRepo factureRepo;
	private ReferenceService referenceService;
	private LigneCommandeRepo ligneCommandeRepo;
	private ReglementRepo reglementRepo;
	private EmailSenderService emailSenderService;
	public static final String DIRECTORY = "src/main/resources/templates/";
	
	@Autowired
	public FactureService(FactureRepo factureRepo, ReglementRepo reglementRepo, ReferenceService referenceService, LigneCommandeRepo ligneCommandeRepo, EmailSenderService emailSenderService) {
		this.factureRepo = factureRepo;
		this.referenceService = referenceService;
		this.ligneCommandeRepo = ligneCommandeRepo;
		this.reglementRepo = reglementRepo;
		this.emailSenderService = emailSenderService;
	}
	
	public Facture save(Facture facture) throws Exception {
		
		switch(facture.getStatut()) {
			case "brouillon" : 
				System.out.println("Je suis dans brouillon");
				if(facture.getNumero()!=0) {
					System.out.println("holla num facture :" + facture.getNumero());
					Facture old = this.factureRepo.findById(facture.getNumero()).get();
					if(old != null) {
						for(LigneCommande l : old.getLignes()) {
							if(!facture.getLignes().contains(l)) this.ligneCommandeRepo.delete(l);
						}
					}
				}
				if(facture.getReference()==null) {
					facture.setReference("fac/" + Calendar.getInstance().get(Calendar.YEAR) + "/" + this.referenceService.get().getFacture());
					facture.setReference(facture.getReference().toUpperCase());
					this.referenceService.incrementFacture();
				}
				List<LigneCommande> lignes = facture.getLignes();
				facture = this.factureRepo.save(facture);
				for(LigneCommande ligne : lignes) {
					ligne.setFacture(facture);
					ligne = this.ligneCommandeRepo.save(ligne);
				}
				return facture;
			case "valide":
					List<Reglement> reglements = facture.getReglements();
					for(Reglement reglement : reglements) {
						reglement.setFacture(facture);
						reglement = this.reglementRepo.save(reglement);
					}
				return this.factureRepo.save(facture);
			case "paye" : return this.factureRepo.save(facture);
		}
		return facture;
	}
	
	public List<Facture> getByEntreprise(Utilisateur user){
		List<Facture> factures = new ArrayList<Facture>();
		for(Client client : user.getEntreprise().getClients()) {
			factures.addAll(client.getFactures());
		}
		return factures;
	}
	
	public void createFacture(Facture facture, Modele modele, Utilisateur user) throws IOException {
		//Get or create if not exists file
				File directory = new File(DIRECTORY + user.getEntreprise().getId_entreprise() + "/factures/");
				if (! directory.exists()) directory.mkdir();
				File file = new File(directory.getPath() + "/" + facture.getReference() + "_" + modele.getNom_modelep() + ".pdf");
				file.createNewFile();
				FileOutputStream oFile = new FileOutputStream(file, false); 
				
				//Start generating the file
				PdfWriter pdfWriter = new PdfWriter(oFile);
				PdfDocument pdfDocument = new PdfDocument(pdfWriter);
				
				Document document = new Document(pdfDocument);
				pdfDocument.setDefaultPageSize(PageSize.A4);
				
				float headWidth[] = {100f, 250f, 250f};
				Table head = new Table(headWidth);
				
				head.setFontColor(Color.BLACK);
				ImageData data = ImageDataFactory.create(getClass().getClassLoader().getResource("templates/" + user.getEntreprise().getLogo())); 
				
				
				float infoWidth[] = {125f, 125f};
				Table infos = new Table(infoWidth);
				infos.addCell(new Cell(0,2).add(user.getEntreprise().getRaison().toUpperCase())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_entt().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_entt().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_entt().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER)
						.setFontSize(modele.getTaill_titre_entt())
						.setBold());
				
				infos.addCell(new Cell(0,2).add(user.getEntreprise().getAdresse1())
						.setBorder(Border.NO_BORDER)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
						.setFontSize(modele.getTaill_txt_entt()));
				
				infos.addCell(new Cell(0,2).add(user.getEntreprise().getTelephone1())
						.setBorder(Border.NO_BORDER)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
						.setFontSize(modele.getTaill_txt_entt()));
				
				Table identifiants = new Table(infoWidth);
				if(facture.getStatut().equals("brouillon")) {
					identifiants.addCell(new Cell(0,2).add("DEVIS")
							.setBorder(Border.NO_BORDER)
							.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
						            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
						            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
							.setFontSize(modele.getTaill_txt_entt())
							.setBold());
					identifiants.addCell(new Cell(0,2).add(facture.getCreation())
							.setBorder(Border.NO_BORDER)
							.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
						            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
						            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
							.setFontSize(modele.getTaill_txt_entt()));
				}else {
					identifiants.addCell(new Cell(0,2).add("FACTURE")
						.setBorder(Border.NO_BORDER)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
						.setFontSize(modele.getTaill_txt_entt())
						.setBold());
					identifiants.addCell(new Cell(0,2).add(facture.getValidation())
							.setBorder(Border.NO_BORDER)
							.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
						            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
						            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
							.setFontSize(modele.getTaill_txt_entt()));
				}
				
				
				
				identifiants.addCell(new Cell(0,2).add(facture.getReference())
						.setBorder(Border.NO_BORDER)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
						.setFontSize(modele.getTaill_txt_entt()));
				
				
				
				if(modele.getPosition().equals("G")) {
					head.addCell(new Cell().add(new Image(data).setAutoScale(false))
							.setBorder(Border.NO_BORDER));
					
					head.addCell(new Cell().add(infos)
							.setBorder(Border.NO_BORDER)
							.setMarginTop(20f));
					
					head.addCell(new Cell().add(identifiants)
							.setTextAlignment(TextAlignment.RIGHT)
							.setMarginTop(20f)
							.setBorder(Border.NO_BORDER));
				}else {
					if(modele.getPosition().equals("D")) {
						head.addCell(new Cell().add(identifiants)
								.setMarginTop(20f)
							.setBorder(Border.NO_BORDER));
					
						head.addCell(new Cell().add(infos)
								.setBorder(Border.NO_BORDER)
								.setTextAlignment(TextAlignment.CENTER)
								.setMarginTop(20f)
								.setMarginLeft(140f));
						
						head.addCell(new Cell().add(new Image(data).setAutoScale(false))
								.setBorder(Border.NO_BORDER)
								.setMarginLeft(150f));
					}
					else {
						head.addCell(new Cell().add(identifiants)
								.setBorder(Border.NO_BORDER));
						
						head.addCell(new Cell().add(new Image(data).setAutoScale(false))
								.setBorder(Border.NO_BORDER)
								.setMarginTop(20f)
								.setMarginLeft(160f));
						
						head.addCell(new Cell().add(infos)
								.setBorder(Border.NO_BORDER)
								.setMarginTop(20f)
								.setTextAlignment(TextAlignment.RIGHT));
					}
				}
				
				
				Table dest = new Table(2);
				dest.addCell(new Cell().add("Destinataire :")
						.setFontSize(modele.getTaill_titre_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
						.setBold()
						.setUnderline()
						.setBorder(Border.NO_BORDER));
				dest.addCell(new Cell().add("Envoyez à :")
						.setFontSize(modele.getTaill_titre_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
						.setBold()
						.setUnderline()
						.setBorder(Border.NO_BORDER));
				
				dest.addCell(new Cell().add(user.getEntreprise().getRaison())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				dest.addCell(new Cell().add(facture.getClient().getRaison() + facture.getClient().getNom())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				
				dest.addCell(new Cell().add(user.getEntreprise().getAdresse1())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				dest.addCell(new Cell().add(facture.getClient().getAdresse())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setFontSize(modele.getTaill_txt_corps())
						.setBorder(Border.NO_BORDER));
				
				dest.addCell(new Cell().add(user.getEntreprise().getTelephone1())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				dest.addCell(new Cell().add(facture.getClient().getTelephone())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				
				dest.addCell(new Cell().add(user.getEntreprise().getEmail())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				dest.addCell(new Cell().add(facture.getClient().getEmail())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				
				float[] detailsWidth = {250f, 100f, 125f, 125f};
				Table details = new Table(detailsWidth);
				
				details.addCell(new Cell().add("Description")
						.setFontSize(modele.getTaill_titre_corps())
						.setBold()
						.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				details.addCell(new Cell().add("QUANTITÉ")
						.setFontSize(modele.getTaill_titre_corps())
						.setBold()
						.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				details.addCell(new Cell().add("PRIX UNITAIRE")
						.setFontSize(modele.getTaill_titre_corps())
						.setBold()
						.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				details.addCell(new Cell().add("TOTAL")
						.setFontSize(modele.getTaill_titre_corps())
						.setBold()
						.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER));
				
				DeviceRgb[] colors = {new DeviceRgb(255, 255, 255), new DeviceRgb(243, 243, 243)};

				int j = 0;
				for(LigneCommande ligne : facture.getLignes()) {
					if(ligne.getService()==null) {
						details.addCell(new Cell().add(ligne.getProduit().getLibelle())
							.setFontSize(9f)
							.setBackgroundColor(colors[j%2])
							.setFontColor(Color.BLACK));
						details.addCell(new Cell().add(ligne.getQte()+"")
								.setFontSize(9f)
								.setBackgroundColor(colors[j%2])
								.setFontColor(Color.BLACK));
						details.addCell(new Cell().add(ligne.getProduit().getPrix()+"")
								.setFontSize(9f)
								.setBackgroundColor(colors[j%2])
								.setFontColor(Color.BLACK));
						details.addCell(new Cell().add(facture.getHt()+"")
								.setFontSize(9f)
								.setBackgroundColor(colors[j%2])
								.setFontColor(Color.BLACK));
					}else {
						details.addCell(new Cell().add(ligne.getService().getLibelle())
								.setFontSize(9f)
								.setBackgroundColor(colors[j%2])
								.setFontColor(Color.BLACK));
						details.addCell(new Cell().add(ligne.getQte()+"")
								.setFontSize(9f)
								.setBackgroundColor(colors[j%2])
								.setFontColor(Color.BLACK));
						details.addCell(new Cell().add(ligne.getService().getTaux_horaire()+"")
								.setFontSize(9f)
								.setBackgroundColor(colors[j%2])
								.setFontColor(Color.BLACK));
						details.addCell(new Cell().add(facture.getHt()+"")
								.setFontSize(9f)
								.setBackgroundColor(colors[j%2])
								.setFontColor(Color.BLACK));
					}
					j++;
				}
				
				details.addCell(new Cell(0,2));
				
				details.addCell(new Cell().add("SOUS-TOTAL")
						.setFontSize(modele.getTaill_txt_corps())
						.setTextAlignment(TextAlignment.RIGHT)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBold());
				details.addCell(new Cell().add(facture.getHt())
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setTextAlignment(TextAlignment.RIGHT)
						.setBold());
				
				details.addCell(new Cell(0,2)
						.setFontSize(9f)
						.setBold()
						.setBorder(Border.NO_BORDER));
				
				details.addCell(new Cell().add("TAUX D'IMPOSITION")
						.setFontSize(modele.getTaill_txt_corps())
						.setTextAlignment(TextAlignment.RIGHT)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBold());
				details.addCell(new Cell().add(user.getEntreprise().getTaxe()+"")
						.setFontSize(modele.getTaill_txt_corps())
						.setTextAlignment(TextAlignment.RIGHT)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBold());
				
				details.addCell(new Cell(0,2)
						.setFontSize(9f)
						.setBold()
						.setBorder(Border.NO_BORDER));
				
				details.addCell(new Cell().add("TAXE TOTAL")
						.setFontSize(modele.getTaill_txt_corps())
						.setTextAlignment(TextAlignment.RIGHT)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBold());
				details.addCell(new Cell().add((facture.getTtc()-facture.getHt()) + "")
						.setFontSize(modele.getTaill_txt_corps())
						.setTextAlignment(TextAlignment.RIGHT)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBold());
				
				details.addCell(new Cell(0,2)
						.setFontSize(9f)
						.setBold()
						.setBorder(Border.NO_BORDER));
				
				details.addCell(new Cell().add("EXPEDITIO, MANUTENTION")
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setTextAlignment(TextAlignment.RIGHT)
						.setBold());
				details.addCell(new Cell().add(facture.getReste()+"")
						.setFontSize(modele.getTaill_txt_corps())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setTextAlignment(TextAlignment.RIGHT)
						.setBold());
				
				details.addCell(new Cell(0,2)
						.setFontSize(9f)
						.setBold()
						.setBorder(Border.NO_BORDER));
				
				details.addCell(new Cell().add("SOLDE DU")
						.setFontSize(modele.getTaill_total())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_total().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_total().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_total().substring( 5, 7 ), 16 )))
						.setTextAlignment(TextAlignment.RIGHT)
						.setBold());
				details.addCell(new Cell().add(facture.getTtc())
						.setFontSize(modele.getTaill_total())
						.setTextAlignment(TextAlignment.RIGHT)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_total().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_total().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_total().substring( 5, 7 ), 16 )))
						.setBold());
				
				Table description = new Table(1);
				
				description.addCell(new Cell().add(modele.getDescription())
						.setTextAlignment(TextAlignment.CENTER)
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER)
						.setBold()
						.setFontSize(modele.getTaill_txt_corps()));
				
				Table pied = new Table(1);
				
				pied.addCell(new Cell().add(modele.getPied().toUpperCase())
						.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_bas().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_bas().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_bas().substring( 5, 7 ), 16 )))
						.setBorder(Border.NO_BORDER)
						.setTextAlignment(TextAlignment.CENTER)
						.setFontSize(modele.getTaill_bas())
						.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
						.setMarginRight(-40f)
						.setMarginLeft(-40f));
				
				Table top = new Table(1);
				
				top.addCell(new Cell()
						.setBorder(Border.NO_BORDER)
						.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
					            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
						.setMarginRight(-40f)
						.setMarginLeft(-40f)
						.setHeight(12f));
				
				
				//CLose file
				document.add(top.setMarginTop(-36));
				document.add(head.setMarginTop(30));
				document.add(dest.setMarginTop(20f));
				document.add(details.setMarginTop(20f));
				document.add(description.setMarginTop(30f));
				document.add(pied.setMarginTop(20f));
				document.close();
				
				modele.setEntreprise(user.getEntreprise());
				modele.setFile(user.getEntreprise().getId_entreprise() + "/" + modele.getNom_modelep() + ".pdf");
	}
	
	public void sendMail() {
		
	}
}
