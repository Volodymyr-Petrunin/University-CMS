package com.university.universitycms.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;


@Component
public class EmailSender implements EmailNotification{
    private final GreenMail greenMail;
    private final String subject;
    private final String from;
    private final String emailTemplate;


    @Autowired
    public EmailSender(@Value("${email.subject}") String subject,
                       @Value("${email.from}") String from, @Value("${email.template}") String emailTemplate) {
        this.subject = subject;
        this.from = from;
        this.emailTemplate = emailTemplate;
        this.greenMail = setupGreenMail();
        greenMail.start();
    }

    @Override
    public void sendRegistrationConfirmation(String name, String email, char[] password){
        String text = fillTemplate(name, email, password);
        ServerSetup serverSetup = greenMail.getSmtp().getServerSetup();

        GreenMailUtil.sendTextEmail(email, from, subject, text, serverSetup);
    }

    private String fillTemplate(String name, String email, char[] password){
        return String.format(emailTemplate, name, email, CharBuffer.wrap(password));
    }

    private GreenMail setupGreenMail(){
        ServerSetup serverSetup = new ServerSetup(3025, "localhost", "smtp");
        GreenMail setupGreenMail = new GreenMail(serverSetup);
        setupGreenMail.setUser("University-CMS", "1234");

        return setupGreenMail;
    }
}
