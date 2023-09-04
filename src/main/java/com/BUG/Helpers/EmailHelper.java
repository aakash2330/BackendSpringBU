package com.BUG.Helpers;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Component;

import com.BUG.Entities.generalRequest;

@Component
public class EmailHelper {

public generalRequest generateOtpEmail(String email) {
	Properties prop = System.getProperties();
	prop.put("mail.smtp.host", "smtp.gmail.com");
	prop.put("mail.smtp.port", "465");
	prop.put("mail.smtp.auth", "true");
	prop.put("mail.smtp.socketFactory.port", "465");
	prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	Session sess = Session.getInstance(prop, new Authenticator() {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("johnkennedy3023@gmail.com", "rmcsgodifweszwtc");
		}

	});
	
	
	MimeMessage message = new MimeMessage(sess);
	try {
		message.setFrom("johnkennedy3023@gmail.com");
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
		message.setSubject("BUG - ChangePassword");
		message.setText("OTP");
		Transport.send(message);
		return new generalRequest(true);
		
	} catch (MessagingException e) {
		e.printStackTrace();
	}
	
	return new generalRequest(false);
}
}