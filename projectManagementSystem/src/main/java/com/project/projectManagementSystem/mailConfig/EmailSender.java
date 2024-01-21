package com.project.projectManagementSystem.mailConfig;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

	@Value("${smtp.host}")
	private String host;

	@Value("${smtp.port}")
	private Integer port;

	@Value("${smtp.username}")
	private String userName;

	@Value("${smtp.password}")
	private String password;

	@Value("${smtp.tls}")
	private Boolean tls;

	public void sendMail(String to, String Subject, String body) throws MessagingException {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", String.valueOf(tls));
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		});

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(Subject);
		message.setText(body);

		Transport.send(message);
	}

}
