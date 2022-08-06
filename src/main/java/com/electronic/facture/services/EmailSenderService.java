package com.electronic.facture.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

}
