package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.generations.GenerationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GroupGenerationData implements GenerationData<Group> {
    private final Random random = new Random();
    private static final int ALPHABET_SIZE = 26;
    private static final int DIGITS_SIZE = 10;
    private final int quantity;
    private final int amountOfLetters;
    private final int amountOfNumbers;

    @Autowired
    public GroupGenerationData(@Value("${quantity.max.group}") int quantity, @Value("${amountOfLetters}") int amountOfLetters,
                               @Value("${amountOfDigits}") int amountOfNumbers) {
        this.quantity = quantity;
        this.amountOfLetters = amountOfLetters;
        this.amountOfNumbers = amountOfNumbers;
    }

    @Override
    public List<Group> generateData() {
        List<Group> result = new ArrayList<>();

        for (int index = 0; index < quantity; index++){
            String name = generateRandomChars(amountOfLetters, true) + "-" + generateRandomChars(amountOfNumbers, false);

            result.add(new Group(null, name));
        }

        return result;
    }

    private String generateRandomChars(int count, boolean isLetter) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int index = 0; index < count; index++) {
            char currentChar;
            if (isLetter) {
                currentChar = (char) (random.nextInt(ALPHABET_SIZE) + 'A');
            } else {
                currentChar = (char) (random.nextInt(DIGITS_SIZE) + '0');
            }
            stringBuilder.append(currentChar);
        }

        return stringBuilder.toString();
    }
}
