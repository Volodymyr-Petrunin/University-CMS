package com.university.universitycms.generation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordGeneration {
    private final GenerationRandomizer generationRandomizer;
    private final int quantityPasswordChars;

    @Autowired
    public PasswordGeneration(GenerationRandomizer generationRandomizer, @Value("${quantity.max.password.chars}") int quantityPasswordChars) {
        this.generationRandomizer = generationRandomizer;
        this.quantityPasswordChars = quantityPasswordChars;
    }

    public char[] generatePassword(){
        return generationRandomizer.generateRandomChars('A', 'Z', quantityPasswordChars).toCharArray();
    }
}
