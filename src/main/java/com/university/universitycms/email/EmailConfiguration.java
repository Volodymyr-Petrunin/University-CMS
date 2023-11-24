package com.university.universitycms.email;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class EmailConfiguration {

    @Profile("notification-disable")
    @Bean
    public GreenMail greenMail(){
        ServerSetup serverSetup = new ServerSetup(3025, "localhost", "smtp");
        GreenMail greenMail = new GreenMail(serverSetup);
        greenMail.setUser("University-CMS", "1234");

        return greenMail;
    }
}
