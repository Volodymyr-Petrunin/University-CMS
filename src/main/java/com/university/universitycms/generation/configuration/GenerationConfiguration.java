package com.university.universitycms.generation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Random;

@Configuration
@PropertySource("classpath:generation-configuration.properties")
public class GenerationConfiguration {
    @Bean
    public Random random(){
        return new Random();
    }
}
