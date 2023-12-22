package com.university.universitycms.generation.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.generation.GenerationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class GroupGenerationData implements GenerationData<Group> {
    private final GenerationRandomizer generationRandomizer;
    private final int quantity;
    private final int amountOfLetters;
    private final int amountOfNumbers;

    @Autowired
    public GroupGenerationData(GenerationRandomizer generationRandomizer, @Value("${quantity.max.group}") int quantity, @Value("${amountOfLetters}") int amountOfLetters,
                               @Value("${amountOfDigits}") int amountOfNumbers) {
        this.generationRandomizer = generationRandomizer;
        this.quantity = quantity;
        this.amountOfLetters = amountOfLetters;
        this.amountOfNumbers = amountOfNumbers;
    }

    @Override
    public List<Group> generateData() {
        List<Group> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++){
            String name = generationRandomizer.generateRandomChars('A','Z', amountOfLetters) + "-"
                    + generationRandomizer.generateRandomChars('1', '9', amountOfNumbers);

            result.add(new Group(null, name, Collections.emptySet()));
        }

        return result;
    }
}
