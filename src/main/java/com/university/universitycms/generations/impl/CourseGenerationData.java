package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Course;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseGenerationData implements GenerationData<Course> {
    private final ResourcesFileReader resourcesFileReader;

    @Autowired
    public CourseGenerationData(@Qualifier("readerCourseFile") ResourcesFileReader resourcesFileReader) {
        this.resourcesFileReader = resourcesFileReader;
    }

    @Override
    public List<Course> generateData() {
        return resourcesFileReader.read().stream()
                .map(line -> new Course(null, line))
                .toList();
    }
}
