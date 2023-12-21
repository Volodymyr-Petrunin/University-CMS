package com.university.universitycms.generation.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.generation.GenerationData;
import com.university.universitycms.reader.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CourseGenerationData implements GenerationData<Course> {
    private final ResourcesFileReader resourcesFileReader;
    private final String fileName;

    @Autowired
    public CourseGenerationData(ResourcesFileReader resourcesFileReader,
                                @Value("${generation.file.courses}") String fileName) {
        this.resourcesFileReader = resourcesFileReader;
        this.fileName = fileName;
    }

    @Override
    public List<Course> generateData() {
        return resourcesFileReader.read(fileName).stream()
                .map(line -> new Course(null, line, Collections.emptySet()))
                .toList();
    }
}
