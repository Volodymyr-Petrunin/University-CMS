package com.university.universitycms.generations.configurations;

import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:generationConfigurations/coursesGenerationConfiguration.properties")
public class CourseGenerationConfiguration {

    @Bean
    public ResourcesFileReader readerCourseFile(@Value("${courses}") String filename){
        return new ResourcesFileReader(filename);
    }

}
