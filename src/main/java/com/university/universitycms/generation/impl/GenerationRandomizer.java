package com.university.universitycms.generation.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Component
public class GenerationRandomizer {
    private final Random random;
    private final int dayStart;
    private final int dayEnd;

    @Autowired
    public GenerationRandomizer(@Qualifier("random") Random random, @Value("${quantity.university.day.start}") int dayStart,
                                @Value("${quantity.university.day.end}") int dayEnd) {
        this.random = random;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
    }

    public <T> T getRandomElementFromList(List<T> list){
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }

        int index = random.nextInt(list.size());
        return list.get(index);
    }

    public LocalTime getStartTime(){
        return LocalTime.of(random.nextInt(dayStart, dayEnd), 0);
    }
}
