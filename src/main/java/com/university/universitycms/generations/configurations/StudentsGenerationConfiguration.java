package com.university.universitycms.generations.configurations;

import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


@Configuration
@PropertySource("classpath:generationConfigurations/studentGenerationConfiguration.properties")
public class StudentsGenerationConfiguration {

    @Bean
    public List<String> studentsFirstNameList(@Value("${studentsName}") String fileName){
        ResourcesFileReader resourcesFileReader = new ResourcesFileReader(fileName);
        return resourcesFileReader.read();
    }

    @Bean
    public List<String> studentsSecondNameList(@Value("${studentsSurname}") String fileName){
        ResourcesFileReader resourcesFileReader = new ResourcesFileReader(fileName);
        return resourcesFileReader.read();
    }
}
