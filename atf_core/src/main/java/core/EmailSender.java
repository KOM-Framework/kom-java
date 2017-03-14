package core;

import core.filemanagement.MFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

public class EmailSender {
	
	public static void sendTo(String toAddress, String subject, String body, ArrayList<String> attachments) {
		// Assuming you are sending email from localhost
		String host = Global.LOCAL_SMTP_SERVER;
		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress("Teams@worldpay.ca", "Auto Test Framework"));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(toAddress));
			// Set Subject: header field
			message.setSubject(subject);
			
			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Create the message part 
			BodyPart messageBodyPart = new MimeBodyPart();
			// Fill the message
			messageBodyPart.setContent(body, "text/html");
			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			if (attachments != null) {
				for (String attachment : attachments) {
					messageBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(attachment);
					messageBodyPart.setDataHandler(new DataHandler(source));
					messageBodyPart.setFileName(source.getName());
					multipart.addBodyPart(messageBodyPart);
				}
			}

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			Log.info("Email sent to " + toAddress);
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendReportTo(String toAddress) {
		String filepath = Global.WORKSPACE_LOCATION + "\\test-output\\emailable-report.html";
		String[] file = MFile.read(filepath);
		
		StringBuilder builder = new StringBuilder();
		for (String s : file) {
			builder.append(s);
		}

		sendTo(toAddress, "ATF Test Results", builder.toString(), null);

	}
}