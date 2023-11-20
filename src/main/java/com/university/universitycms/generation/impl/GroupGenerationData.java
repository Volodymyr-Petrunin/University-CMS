package com.university.universitycms.generation.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.generation.GenerationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GroupGenerationData implements GenerationData<Group> {
    private final Random random;
    private final int quantity;
    private final int amountOfLetters;
    private final int amountOfNumbers;

    @Autowired
    public GroupGenerationData(Random random, @Value("${quantity.max.group}") int quantity, @Value("${amountOfLetters}") int amountOfLetters,
                               @Value("${amountOfDigits}") int amountOfNumbers) {
        this.random = random;
        this.quantity = quantity;
        this.amountOfLetters = amountOfLetters;
        this.amountOfNumbers = amountOfNumbers;
    }

    @Override
    public List<Group> generateData() {
        List<Group> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++){
            String name = generateRandomChars('A','Z', amountOfLetters) + "-"
                    + generateRandomChars('1', '9', amountOfNumbers);

            result.add(new Group(null, name));
        }

        return result;
    }

    private String generateRandomChars(char startChar, char endChar, int count) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int index = 0; index < count; index++) {
            char currentChar = (char) (random.nextInt(endChar - startChar + 1) + startChar);
            stringBuilder.append(currentChar);
        }

        return stringBuilder.toString();
    }
}
