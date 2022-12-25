package com.electronic.facture.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.electronic.facture.models.Modele;
import com.electronic.facture.models.Utilisateur;
import com.electronic.facture.repositories.ModeleRepository;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;

@Service
public class ModeleService {
	
	public ModeleRepository modeleRepository;
	public static final String DIRECTORY = "src/main/resources/templates/modeles/";
	
	@Autowired
	public ModeleService(ModeleRepository modeleRepository) {
		this.modeleRepository = modeleRepository;
	}
	
	public Modele save(Modele modele, Utilisateur user) throws IOException {
		//Get or create if not exists file
		File directory = new File(DIRECTORY + user.getEntreprise().getId_entreprise() + "/");
		if (! directory.exists()) directory.mkdir();
		File file = new File(directory.getPath() + "/" + modele.getNom_modelep() + ".pdf");
		file.createNewFile();
		FileOutputStream oFile = new FileOutputStream(file, false); 
		System.out.println("1");
		//Start generating the file
		PdfWriter pdfWriter = new PdfWriter(oFile);
		System.out.println("2");
		PdfDocument pdfDocument = new PdfDocument(pdfWriter);
		System.out.println("3");
		
		Document document = new Document(pdfDocument);
		System.out.println("4");
		pdfDocument.setDefaultPageSize(PageSize.A4);
		
		float headWidth[] = {100f, 250f, 250f};
		Table head = new Table(headWidth);
		
		head.setFontColor(Color.BLACK);
		ImageData data = ImageDataFactory.create(getClass().getClassLoader().getResource("templates/" + user.getEntreprise().getLogo()));
		System.out.println("5");
		System.out.println(user.getEntreprise().getRaison());
		
		
		float infoWidth[] = {125f, 125f};
		Table infos = new Table(infoWidth);
		System.out.println("1");
		infos.addCell(new Cell(0,2).add(user.getEntreprise().getRaison().toUpperCase())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_entt().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_entt().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_entt().substring( 5, 7 ), 16 )))
				.setBorder(Border.NO_BORDER)
				.setFontSize(modele.getTaill_titre_entt())
				.setBold());
		System.out.println("6oneone");
		
		infos.addCell(new Cell(0,2).add(user.getEntreprise().getAdresse1())
				.setBorder(Border.NO_BORDER)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
				.setFontSize(modele.getTaill_txt_entt()));
		System.out.println("6onetwo");
		
		infos.addCell(new Cell(0,2).add(user.getEntreprise().getTelephone1())
				.setBorder(Border.NO_BORDER)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_entt().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_entt().substring( 5, 7 ), 16 )))
				.setFontSize(modele.getTaill_txt_entt()));
		
		Table identifiants = new Table(infoWidth);
		identifiants.addCell(new Cell(0,2).add("FACTURE")
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
		
		
		
		if(modele.getPosition().equals("G")) {
			System.out.println("6one");
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
		dest.addCell(new Cell().add("Expéditeur :")
				.setFontSize(modele.getTaill_titre_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
				.setBold()
				.setUnderline()
				.setBorder(Border.NO_BORDER));
		dest.addCell(new Cell().add("Destinataire :")
				.setFontSize(modele.getTaill_titre_corps())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 )))
				.setBold()
				.setUnderline()
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
		
		details.addCell(new Cell().add("Libelle")
				.setFontSize(modele.getTaill_titre_corps())
				.setBold()
				.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 ))));
		details.addCell(new Cell().add("Quantite")
				.setFontSize(modele.getTaill_titre_corps())
				.setBold()
				.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 ))));
		details.addCell(new Cell().add("Prix Unitaire")
				.setFontSize(modele.getTaill_titre_corps())
				.setBold()
				.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 ))));
		details.addCell(new Cell().add("Sous-Total")
				.setFontSize(modele.getTaill_titre_corps())
				.setBold()
				.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_template().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_template().substring( 5, 7 ), 16 )))
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_titre_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_titre_corps().substring( 5, 7 ), 16 ))));
		System.out.println("6");
		
		DeviceRgb[] colors = {new DeviceRgb(255, 255, 255), new DeviceRgb(243, 243, 243)};
		
		for(int j=0; j<10; j++) {
			for(int i=0; i<4; i++) {
				details.addCell(new Cell()
						.setFontSize(9f)
						.setBackgroundColor(colors[j%2])
						.setFontColor(Color.BLACK));
			}
		}
		
		details.addCell(new Cell(0,2)
				.add(modele.getDescription())
				.setFontSize(modele.getTaill_txt_corps())
				.setBorder(Border.NO_BORDER)
				.setTextAlignment(TextAlignment.LEFT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 ))));
		
		details.addCell(new Cell().add("Montant HT")
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
		
		details.addCell(new Cell().add("Taux d'imposition")
				.setFontSize(modele.getTaill_txt_corps())
				.setTextAlignment(TextAlignment.RIGHT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBold());
		details.addCell(new Cell().add(user.getEntreprise().getTaxe()+"%")
				.setFontSize(modele.getTaill_txt_corps())
				.setTextAlignment(TextAlignment.RIGHT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBold());
		
		details.addCell(new Cell(0,2)
				.add("Date d'échéance :")
				.setFontSize(modele.getTaill_txt_corps())
				.setBorder(Border.NO_BORDER)
				.setBold()
				.setTextAlignment(TextAlignment.LEFT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 ))));
		
		details.addCell(new Cell().add("Montant du Taxe")
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
		
		details.addCell(new Cell().add("Total")
				.setFontSize(modele.getTaill_total())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setTextAlignment(TextAlignment.RIGHT)
				.setBold());
		details.addCell(new Cell().add("0,00DH")
				.setFontSize(modele.getTaill_total())
				.setTextAlignment(TextAlignment.RIGHT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_total().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_total().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_total().substring( 5, 7 ), 16 )))
				.setBold());
		
		details.addCell(new Cell(0,2)
				.setFontSize(9f)
				.setBold()
				.setBorder(Border.NO_BORDER));
		
		details.addCell(new Cell().add("Reglements")
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
		
		details.addCell(new Cell().add("Montant dû")
				.setFontSize(modele.getTaill_total())
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setTextAlignment(TextAlignment.RIGHT)
				.setBold());
		details.addCell(new Cell().add("0,00DH")
				.setFontSize(modele.getTaill_total())
				.setTextAlignment(TextAlignment.RIGHT)
				.setFontColor(new DeviceRgb(Integer.valueOf( modele.getCl_txt_corps().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_txt_corps().substring( 5, 7 ), 16 )))
				.setBackgroundColor(new DeviceRgb(Integer.valueOf( modele.getCl_total().substring( 1, 3 ), 16 ),
			            Integer.valueOf( modele.getCl_total().substring( 3, 5 ), 16 ),
			            Integer.valueOf( modele.getCl_total().substring( 5, 7 ), 16 )))
				.setBold());
		
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
		
		Cell info = new Cell().add(modele.getEntreprise().getRaison())
				.setFontColor(Color.BLACK).setFontSize(11);
		

		//add content
		document.add(top.setMarginTop(-36));
		document.add(head.setMarginTop(30));
		document.add(dest.setMarginTop(20f));
		document.add(details.setMarginTop(20f));
		document.add(pied.setMarginTop(20f));
		document.add(info);
		
		//Close file
		document.close();
		
		modele.setEntreprise(user.getEntreprise());
		modele.setFile(user.getEntreprise().getId_entreprise() + "/" + modele.getNom_modelep() + ".pdf");
		return this.modeleRepository.save(modele);
	}
	
	
	public ResponseEntity<Resource> download(String filename, long id) throws IOException{
		Path filepath = Paths.get(DIRECTORY + id + "/").toAbsolutePath().normalize().resolve(filename);
		if(!Files.exists(filepath)) throw new FileNotFoundException("Le fichier n'est pas trouve");
		Resource resource = new UrlResource(filepath.toUri());
		HttpHeaders headers = new HttpHeaders();
		headers.add("File-Name", filename);
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachement;File-Name="+resource.getFilename());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filepath)))
				.headers(headers).body(resource);
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
	
	public Modele getById(long id) {
		return this.modeleRepository.findById(id).get();
	}
	
	public void delete(long id) {
		Modele modele = this.getById(id);
		File file = new File(DIRECTORY+ modele.getFile());
		file.delete();
		this.modeleRepository.deleteById(id);
		
	}

}
