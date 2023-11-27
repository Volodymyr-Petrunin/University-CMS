package com.university.universitycms.reader;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Component
public class ResourcesFileReader implements Reader {

    @Override
    public List<String> read(String fileName) {
        try (InputStream inputStream = getClass().getResourceAsStream("/" +  fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))){

            return reader.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }
}
