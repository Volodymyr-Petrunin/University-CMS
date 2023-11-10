package com.university.universitycms.generations.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Clock;
import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Random;

@Configuration
@PropertySource("classpath:generation-configuration.properties")
public class GenerationConfiguration {
    @Bean
    public Random random(){
        return new Random();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
