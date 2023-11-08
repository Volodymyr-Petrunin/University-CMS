package com.university.universitycms.generations.configurations;

import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:generation-configuration.properties")
public class GenerationConfiguration {
    @Bean
    public List<String> firstNameOfStudents(ResourcesFileReader resourcesFileReader,
                                            @Value("${generation.file.studentsName}") String firstNameFile){
        return resourcesFileReader.read(firstNameFile);
    }

    @Bean
    public List<String> secondNameOfStudents(ResourcesFileReader resourcesFileReader,
                                             @Value("${generation.file.studentsSurname}") String secondNameFile){
        return resourcesFileReader.read(secondNameFile);
    }

    @Bean
    public List<String> audiences(ResourcesFileReader resourcesFileReader,
                                  @Value("${generation.file.audiences}") String audienceFile){
        return resourcesFileReader.read(audienceFile);
    }

    @Bean
    public List<String> firstNameOfTeachers(ResourcesFileReader resourcesFileReader,
                                            @Value("${generation.file.teachersName}") String firstNameFile){
        return resourcesFileReader.read(firstNameFile);
    }

    @Bean
    public List<String> secondNameOfTeachers(ResourcesFileReader resourcesFileReader,
                                            @Value("${generation.file.teachersSurname}") String firstNameFile){
        return resourcesFileReader.read(firstNameFile);
    }
}
