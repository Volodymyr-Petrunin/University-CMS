package com.university.universitycms.generations.configurations;

import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@PropertySource("classpath:generationConfigurations/lessonGenerationConfiguration.properties")
public class LessonGenerationConfiguration {
    @Bean
    public List<String> audiencesList(@Value("${audiences}") String fileName){
        ResourcesFileReader resourcesFileReader = new ResourcesFileReader(fileName);
        return resourcesFileReader.read();
    }
}
