package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
                .map(line -> new Course(null, line))
                .toList();
    }
}
