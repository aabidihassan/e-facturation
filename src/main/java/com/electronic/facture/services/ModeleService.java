package com.electronic.facture.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.electronic.facture.models.Modele;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.ModeleRepository;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.Line;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.lowagie.text.Font;

@Service
public class ModeleService {
	
	public ModeleRepository modeleRepository;
	
	@Autowired
	public ModeleService(ModeleRepository modeleRepository) {
		this.modeleRepository = modeleRepository;
	}
//	
//	public Modele save(Modele modele) {
//		return this.modeleRepository.save(modele);
//	}
	
//	public String generateModel() throws JRException, IOException {
//		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(null);
//		JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/templates/Invoice_model.jrxml"));
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource("templates/invoice_logo.png"));
//		map.put("logo", image);
////		FileInputStream file = new FileInputStream("src/main/resources/templates/invoice_logo.png");
////		BufferedImage image = ImageIO.read(file);new FileInputStream("res/test.txt")
//		map.put("CompanyName", "Hassan Aabidi");
//		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, beanCollectionDataSource);
//		JasperExportManager.exportReportToPdfFile(jasperPrint, "model.pdf");
//		return "Done";
//	}
	
	public Modele save(Modele modele, Utilisateur user) throws IOException {
		
		//Get or create if not exists file
		File file = new File("src/main/resources/templates/modele.pdf");
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
		ImageData data = ImageDataFactory.create(getClass().getClassLoader().getResource("templates/invoice_logo.png")); 
		head.addCell(new Cell().add(new Image(data).setAutoScale(false))
				.setBorder(Border.NO_BORDER));
		
		
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
		
		head.addCell(new Cell().add(infos)
				.setBorder(Border.NO_BORDER)
				.setMarginTop(20f));
		
		Table identifiants = new Table(infoWidth);
		identifiants.addCell(new Cell(0,2).add(modele.getType_modele().toUpperCase())
				.setBorder(Border.NO_BORDER)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
				.setFontSize(modele.getTaill_txt_entt())
				.setBold());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		identifiants.addCell(new Cell(0,2).add(simpleDateFormat.format(new Date()))
				.setBorder(Border.NO_BORDER)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
				.setFontSize(modele.getTaill_txt_entt()));
		
		identifiants.addCell(new Cell(0,2).add("<identifiant>")
				.setBorder(Border.NO_BORDER)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
				.setFontSize(modele.getTaill_txt_entt()));
		
		head.addCell(new Cell().add(identifiants)
				.setTextAlignment(TextAlignment.RIGHT)
				.setMarginTop(30f)
				.setBorder(Border.NO_BORDER));
		
		
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
		
		dest.addCell(new Cell().add("<Nom>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Nom>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Nom de la societe>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Nom de la societe>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Adresse Postale>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Adresse Postale>")
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setFontSize(modele.getTaill_txt_corps())
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Telephone>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Telephone>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Email>")
				.setFontSize(modele.getTaill_txt_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Email>")
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
		
		for(int j=0; j<10; j++) {
			for(int i=0; i<4; i++) {
				details.addCell(new Cell()
						.setFontSize(9f)
						.setBackgroundColor(colors[j%2])
						.setFontColor(Color.BLACK));
			}
		}
		
		details.addCell(new Cell(0,2));
		
		details.addCell(new Cell().add("SOUS-TOTAL")
				.setFontSize(modele.getTaill_txt_corps())
				.setTextAlignment(TextAlignment.RIGHT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBold());
		details.addCell(new Cell().add("0,00")
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
		
		details.addCell(new Cell().add("REMISE")
				.setFontSize(modele.getTaill_txt_corps())
				.setTextAlignment(TextAlignment.RIGHT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBold());
		details.addCell(new Cell().add("0,00")
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
		
		details.addCell(new Cell().add("SOUS-TOTAL MOINS LES REMISES")
				.setFontSize(modele.getTaill_txt_corps())
				.setTextAlignment(TextAlignment.RIGHT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBold());
		details.addCell(new Cell().add("0,00")
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
		details.addCell(new Cell().add("0,00")
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
		details.addCell(new Cell().add("0,00")
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
		details.addCell(new Cell().add("0,00DH")
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
		return this.modeleRepository.save(modele);
	}

}
