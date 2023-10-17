package com.university.universitycms.services;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan("com.university.universitycms.services")
@EntityScan("com.university.universitycms.domain")
@EnableAutoConfiguration
public class TestConfig {
}
