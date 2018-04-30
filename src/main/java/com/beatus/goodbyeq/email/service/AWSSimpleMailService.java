package com.beatus.goodbyeq.email.service;

import java.io.IOException;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

public class AWSSimpleMailService {
	
	private AmazonSimpleEmailService amazonSimpleEmailService;

	// Replace sender@example.com with your "From" address.
	// This address must be verified with Amazon SES.
	private String FROM;

	// Replace recipient@example.com with a "To" address. If your account
	// is still in the sandbox, this address must be verified.
	private String TO;

	// The configuration set to use for this email. If you do not want to use a
	// configuration set, comment the following variable and the
	// .withConfigurationSetName(CONFIGSET); argument below.
	private String CONFIGSET;

	// The subject line for the email.
	private String SUBJECT;

	// The HTML body for the email.
	private String HTMLBODY;

	// The email body for recipients with non-HTML email clients.
	private String TEXTBODY;

	public void sendEmailThroughAWSSES(String to, String subject, String textBody, String htmlBody) throws IOException {

		try {

			SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(TO))
					.withMessage(new Message()
							.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(HTMLBODY))
									.withText(new Content().withCharset("UTF-8").withData(TEXTBODY)))
							.withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
					.withSource(FROM)
					// Comment or remove the next line if you are not using a
					// configuration set
					.withConfigurationSetName(CONFIGSET);
			amazonSimpleEmailService.sendEmail(request);
			System.out.println("Email sent!");
		} catch (Exception ex) {
			System.out.println("The email was not sent. Error message: " + ex.getMessage());
		}
	}
}