package com.university.universitycms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.format.DateTimeFormatter;

@Configuration
public class TimeConfiguration {
    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
    @Bean
    public DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern("EE/dd/MM");
    }
}
