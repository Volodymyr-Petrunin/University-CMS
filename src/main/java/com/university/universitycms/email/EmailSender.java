package com.university.universitycms.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;


@Component
public class EmailSender {
    private final GreenMail greenMail;
    private final String subject;
    private final String from;
    private final String emailTemplate;


    @Autowired
    public EmailSender(GreenMail greenMail, @Value("${email.subject}") String subject,
                       @Value("${email.from}") String from, @Value("${email.template}") String emailTemplate) {
        this.greenMail = greenMail;
        this.subject = subject;
        this.from = from;
        this.emailTemplate = emailTemplate;
        greenMail.start();
    }

    public void sendEmail(String name, String email, char[] password){
        String text = fillTemplate(name, email, password);
        ServerSetup serverSetup = greenMail.getSmtp().getServerSetup();

        GreenMailUtil.sendTextEmail(email, from, subject, text, serverSetup);
    }

    private String fillTemplate(String name, String email, char[] password){
        return String.format(emailTemplate, name, email, CharBuffer.wrap(password));
    }
}
