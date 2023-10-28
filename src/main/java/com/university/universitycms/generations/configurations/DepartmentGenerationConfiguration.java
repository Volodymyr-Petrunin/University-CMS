package com.university.universitycms.generations.configurations;

import com.university.universitycms.readers.ResourcesFileReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:generationConfigurations/departmentGenerationConfiguration.properties")
public class DepartmentGenerationConfiguration {
    @Bean
    public ResourcesFileReader readerDepartmentFile(@Value("${departments}") String fileName){
        return new ResourcesFileReader(fileName);
    }
}
