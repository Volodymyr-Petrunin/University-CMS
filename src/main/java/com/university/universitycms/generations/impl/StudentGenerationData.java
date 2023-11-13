package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Student;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.generations.randomutils.RandomUtils;
import com.university.universitycms.readers.ResourcesFileReader;
import com.university.universitycms.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentGenerationData implements GenerationData<Student> {
    private final RandomUtils randomUtils;
    private final int quantity;
    private final ResourcesFileReader resourcesFileReader;
    private final String firstNameFile;
    private final String secondNameFile;
    private final GroupService groupService;

    @Autowired
    public StudentGenerationData(RandomUtils randomUtils, GroupService groupService, ResourcesFileReader resourcesFileReader,
                                 @Value("${quantity.max.students}") int quantity,
                                 @Value("${generation.file.studentsName}") String firstNameFile,
                                 @Value("${generation.file.studentsSurname}") String secondNameFile) {
        this.randomUtils = randomUtils;
        this.groupService = groupService;
        this.resourcesFileReader = resourcesFileReader;
        this.quantity = quantity;
        this.firstNameFile = firstNameFile;
        this.secondNameFile = secondNameFile;
    }

    @Override
    public List<Student> generateData() {
        List<String> firstNames = resourcesFileReader.read(firstNameFile);
        List<String> secondNames = resourcesFileReader.read(secondNameFile);

        List<Student> result = new ArrayList<>();
        List<Group> groups = groupService.getAllGroups();

        for (int index = 0; index < quantity; index++) {
            String name = randomUtils.getRandomElementFromList(firstNames);
            String surname = randomUtils.getRandomElementFromList(secondNames);
            Group group = randomUtils.getRandomElementFromList(groups);

            result.add(new Student(null, Role.STUDENT, name, surname, null, group));
        }

        return result;
    }
}
