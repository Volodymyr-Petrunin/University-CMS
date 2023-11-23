package com.university.universitycms.generation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.CharBuffer;
import java.util.Arrays;

@Component
public class GenerationPassword {
    private final GenerationRandomizer generationRandomizer;
    private final PasswordEncoder passwordEncoder;
    private final int quantityPasswordChars;

    @Autowired
    public GenerationPassword(GenerationRandomizer generationRandomizer, PasswordEncoder passwordEncoder,
                              @Value("${quantity.max.password.chars}") int quantityPasswordChars) {
        this.generationRandomizer = generationRandomizer;
        this.passwordEncoder = passwordEncoder;
        this.quantityPasswordChars = quantityPasswordChars;
    }

    public String generatePassword(){
        char[] password = generationRandomizer.generateRandomChars('A', 'Z', quantityPasswordChars).toCharArray();
        Arrays.fill(password, '\0');

        return passwordEncoder.encode(CharBuffer.wrap(password));
    }
}
