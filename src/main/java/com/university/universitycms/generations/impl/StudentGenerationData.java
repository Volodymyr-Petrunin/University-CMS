package com.university.universitycms.generations.impl;

import com.university.universitycms.domain.Group;
import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Student;
import com.university.universitycms.generations.GenerationData;
import com.university.universitycms.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class StudentGenerationData implements GenerationData<Student> {
    private final Random random = new Random();
    private final int quantity;
    private final List<String> firstNames;
    private final List<String> secondNames;
    private final GroupService groupService;

    @Autowired
    public StudentGenerationData(GroupService groupService, @Value("${quantity.max.students}") int quantity,
                                 @Qualifier("firstNameOfStudents") List<String> firstNames,
                                 @Qualifier("secondNameOfStudents") List<String> secondNames) {
        this.groupService = groupService;
        this.firstNames = firstNames;
        this.secondNames = secondNames;
        this.quantity = quantity;
    }

    @Override
    public List<Student> generateData() {
        List<Student> result = new ArrayList<>();
        List<Group> groups = groupService.getAllGroups();

        for (int index = 0; index < quantity; index++) {
            String name = getRandomElement(firstNames);
            String surname = getRandomElement(secondNames);
            Group group = getRandomElement(groups);

            result.add(new Student(null, Role.STUDENT, name, surname, null, group));
        }

        return result;
    }

    private <T> T getRandomElement(List<T> list){
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
