package com.university.universitycms.generations.configurations;

import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:generationConfigurations/teacherGenerationConfiguration.properties")
public class TeacherGenerationConfiguration {

    @Bean
    public List<String> teacherFirstNames(@Value("${teachersName}") String fileName){
        ResourcesFileReader resourcesFileReader = new ResourcesFileReader(fileName);
        return resourcesFileReader.read();
    }

    @Bean
    public List<String> teacherSecondNames(@Value("${teachersSurname}") String fileName){
        ResourcesFileReader resourcesFileReader = new ResourcesFileReader(fileName);
        return resourcesFileReader.read();
    }

}
