package nl.hu.sie.bep.friendspammer;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	
	public static void sendEmail(String subject, String to, String messageBody, boolean asHtml) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.mailtrap.io");
		props.put("mail.smtp.port", "2525");
		props.put("mail.smtp.auth", "true");

		String username = "YOUR MAIL USERNAME";
		String password = "YOUR MAIL PASSWORD";

		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("spammer@spammer.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
			message.setSubject(subject);
			
			if (asHtml) {
					message.setContent(messageBody, "text/html; charset=utf-8");
			} else {
				message.setText(messageBody);	
			}
			Transport.send(message);

			MongoSaver.saveEmail(to, "spammer@spamer.com", subject, messageBody, asHtml);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void sendEmail(String subject, String[] toList, String messageBody, boolean asHtml) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.mailtrap.io");
		props.put("mail.smtp.port", "2525");
		props.put("mail.smtp.auth", "true");
		
		String username = "YOUR MAIL USERNAME";
		String password = "YOUR MAIL PASSWORD";

		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		try {

			for (int index = 0; index < toList.length; index++) {
			
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("spammer@spammer.com"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toList[index]));
				message.setSubject(subject);
				
				if (asHtml) {
						message.setContent(messageBody, "text/html; charset=utf-8");
				} else {
					message.setText(messageBody);	
				}
				Transport.send(message);
	
				System.out.println("Done");
			}

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
}
