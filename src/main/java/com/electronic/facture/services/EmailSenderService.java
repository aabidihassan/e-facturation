package com.electronic.facture.services;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailSenderService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendEmail(String to, String subject, String message) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("labos.management@gmail.com");
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setText(message);
		this.javaMailSender.send(mail);
	} 
	
	public void sendEmailWithFile(String to, String subject, String message, File attachment) throws MessagingException {
		MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("labos.management@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText("<html><body>" + message + "</html></body>", true);
		FileSystemResource file = new FileSystemResource(attachment);
		helper.addAttachment(attachment.getName(), file);
		this.javaMailSender.send(mimeMessage);

	}

}
