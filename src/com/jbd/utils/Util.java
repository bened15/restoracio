package com.jbd.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Util {

	public Util() {
		// TODO Auto-generated constructor stub
	}

	public static void sendEmail(String msg) {

		// Recipient's email ID needs to be mentioned.
		String to = "jcoreas6969@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "no-reply-jbdpos@gmail.com";

		// Assuming you are sending email from localhost
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
//		 properties.put("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", "587");
//		properties.setProperty("mail.smtp.auth", "true");


		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("POS-SW-Errors Notifications");

			// Now set the actual message
			message.setText(msg);

			// Send message

			Transport.send(message);
			// System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}
	public static void writeIntoErrorLog(String msg, String type) {

		try {
			// Development
//			File file = new File("C:/PC_TUNE_UP_Logs/errorLog.txt");
			// Production
			 File file = new File("C:/JBDPOS/logs/errorLog.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write((new Date()).toString());
			bw.newLine();
			bw.write(msg);
			bw.newLine();
			bw.flush();
			bw.close();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
