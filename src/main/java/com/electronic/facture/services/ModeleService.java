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

@Service
public class ModeleService {
	
	public ModeleRepository modeleRepository;
	
	@Autowired
	public ModeleService(ModeleRepository modeleRepository) {
		this.modeleRepository = modeleRepository;
	}
	
	public Modele save(Modele modele) {
		return this.modeleRepository.save(modele);
	}
	
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
	
	public String generateModel() throws IOException {
		
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
		infos.addCell(new Cell(0,2).add("<Company Name>")
				.setBorder(Border.NO_BORDER)
				.setFontSize(11f)
				.setBold());
		
		infos.addCell(new Cell(0,2).add("<Adresse>")
				.setBorder(Border.NO_BORDER)
				.setFontSize(8f));
		
		infos.addCell(new Cell(0,2).add("<Phone>")
				.setBorder(Border.NO_BORDER)
				.setFontSize(8f));
		
		head.addCell(new Cell().add(infos)
				.setBorder(Border.NO_BORDER)
				.setMarginTop(20f));
		
		Table identifiants = new Table(infoWidth);
		identifiants.addCell(new Cell(0,2).add("Facture")
				.setBorder(Border.NO_BORDER)
				.setFontSize(18f)
				.setBold());
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		identifiants.addCell(new Cell(0,2).add(simpleDateFormat.format(new Date()))
				.setBorder(Border.NO_BORDER)
				.setFontSize(9f));
		
		identifiants.addCell(new Cell(0,2).add("<identifiant>")
				.setBorder(Border.NO_BORDER)
				.setFontSize(9f));
		
		head.addCell(new Cell().add(identifiants)
				.setTextAlignment(TextAlignment.RIGHT)
				.setMarginTop(50f)
				.setBorder(Border.NO_BORDER));
		
		
		Table dest = new Table(2);
		dest.addCell(new Cell().add("Destinataire :")
				.setFontSize(9f)
				.setBold()
				.setUnderline()
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("Envoyez à :")
				.setFontSize(9f)
				.setBold()
				.setUnderline()
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Nom>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Nom>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Nom de la societe>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Nom de la societe>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Adresse Postale>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Adresse Postale>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Telephone>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Telephone>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		
		dest.addCell(new Cell().add("<Email>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("<Email>")
				.setFontSize(9f)
				.setBorder(Border.NO_BORDER));
		
		float[] detailsWidth = {250f, 100f, 125f, 125f};
		Table details = new Table(detailsWidth);
		
		details.addCell(new Cell().add("DESCRIPTION")
				.setFontSize(9f)
				.setBold()
				.setBackgroundColor(new DeviceRgb(255, 87, 34))
				.setFontColor(Color.WHITE)
				.setBorder(Border.NO_BORDER));
		details.addCell(new Cell().add("QUANTITÉ")
				.setFontSize(9f)
				.setBold()
				.setBackgroundColor(new DeviceRgb(255, 87, 34))
				.setFontColor(Color.WHITE)
				.setBorder(Border.NO_BORDER));
		details.addCell(new Cell().add("PRIX UNITAIRE")
				.setFontSize(9f)
				.setBold()
				.setBackgroundColor(new DeviceRgb(255, 87, 34))
				.setFontColor(Color.WHITE)
				.setBorder(Border.NO_BORDER));
		details.addCell(new Cell().add("TOTAL")
				.setFontSize(9f)
				.setBold()
				.setBackgroundColor(new DeviceRgb(255, 87, 34))
				.setFontColor(Color.WHITE)
				.setBorder(Border.NO_BORDER));
		
		DeviceRgb[] colors = {new DeviceRgb(255, 255, 255), new DeviceRgb(243, 243, 243)};
		
		for(int j=0; j<10; j++) {
			for(int i=0; i<4; i++) {
				details.addCell(new Cell().add("")
						.setFontSize(9f)
						.setBackgroundColor(colors[j%2])
						.setFontColor(Color.BLACK));
			}
		}
		
		
		//CLose file
		document.add(head);
		document.add(dest.setMarginTop(20f));
		document.add(details.setMarginTop(20f));
		document.close();
		return "Done";
	}

}
