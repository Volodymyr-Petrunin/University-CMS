package com.university.universitycms.services;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EntityScan(basePackages = "com.university.universitycms.domain")
@ComponentScan(basePackages = "com.university.universitycms.repositories")
@EnableJpaRepositories("com.university.universitycms.repositories")
@EnableAutoConfiguration
public class TestConfig {
}
