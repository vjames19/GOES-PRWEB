package edu.uprm.ece.hydroclimate.email;

import java.io.File;
import java.net.MalformedURLException;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import edu.uprm.ece.hydroclimate.properties.EmailProperties;

/**
 * Class for sending emails with properties taken from {@link EmailProperties}
 * 
 * <p>To send an email just construct the object and select from one of the two methods to send an email </p>
 * @author Victor J.
 * 
 */
public class Emailer {

	private EmailProperties emailProps;

	public Emailer(EmailProperties emailProps) {
		this.emailProps = emailProps;
	}

	public EmailProperties getEmailProps() {
		return emailProps;
	}

	public void setEmailProps(EmailProperties emailProps) {
		this.emailProps = emailProps;
	}
	
	/**
	 * Send email with just a message
	 * @param subject Subject to use in the email
	 * @param message Message to write in the email
	 * @throws EmailException if the send failed
	 * @throws MalformedURLException the file being attached is a bad file
	 */
	public void sendEmail(String subject, String message) throws EmailException{
		Email email = initializeEmail(new SimpleEmail(), subject);
		email.setMsg(message);
		email.send();
	}

	private Email initializeEmail(Email email, String subject) throws EmailException {
		if(subject == null){
			subject = "";
		}
		email.setHostName(emailProps.getHostname());
		email.setSmtpPort(emailProps.getPort());
		email.setAuthenticator(new DefaultAuthenticator(emailProps.getFrom(), emailProps.getPassword()));
		email.setSSL(true);
		email.setTLS(true);
		email.setFrom(emailProps.getFrom());
		email.setSubject(subject);
		email.addTo(emailProps.getTo());
		return email;
	}
	
	/**
	 * Send an email with an attachment
	 * @param subject Subject to use in the email
	 * @param message Message to write in the email
	 * @param file File to attach
	 * @throws EmailException if the send failed
	 * @throws MalformedURLException the file being attached is a bad file
	 */
	public void sendEmailWithAttachment(String subject, String message, File file) throws EmailException, MalformedURLException{
		MultiPartEmail email = new MultiPartEmail();
		initializeEmail(email,subject);
		email.setMsg(message);
		email.attach(file.toURI().toURL(), file.getName(), "goes-file");
		email.send();
		
	}
	
	

}
