package com.stegnography.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class MailConstructor {
	@Autowired
	private Environment env;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	public MimeMessagePreparator constructOrderConfirmationEmail (String link , String email1) {
		Context context = new Context();
		context.setVariable("link", link);
		String text = templateEngine.process("emailtemplate", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(email1);
				email.setSubject("your encrypted image");
				email.setText(text, true);
				email.setFrom(new InternetAddress("sandyboyraper@gmail.com"));
			}
		};
		
		return messagePreparator;
	}
	
	public MimeMessagePreparator constructOrderConfirmationEmail1 (String username , String password , String email1) {
		Context context = new Context();
		context.setVariable("username", username);
		context.setVariable("password", password);
		String text = templateEngine.process("emailtemplate1", context);
		
		MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
				email.setTo(email1);
				email.setSubject("your encripted audio username and password");
				email.setText(text, true);
				email.setFrom(new InternetAddress("sandyboyraper@gmail.com"));
			}
		};
		
		return messagePreparator;
	}
}
