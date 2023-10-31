package com.university.universitycms.services;

import com.university.universitycms.domain.Teacher;
import com.university.universitycms.generations.impl.TeacherGenerationData;
import com.university.universitycms.repositories.TeacherRepository;
import com.university.universitycms.services.datafilling.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TeacherService implements DataFiller {
    private final TeacherRepository repository;
    private final TeacherGenerationData teacherGenerationData;

    @Autowired
    public TeacherService(TeacherRepository repository, TeacherGenerationData teacherGenerationData) {
        this.repository = repository;
        this.teacherGenerationData = teacherGenerationData;
    }

    public List<Teacher> getAllTeachers(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Teacher> getTeacherById(long teacherId){
        return repository.findById(teacherId);
    }

    public void createTeacher(Teacher teacher){
        repository.save(teacher);
    }

    public void createSeveralTeachers(List<Teacher> teachers){
        repository.saveAll(teachers);
    }

    public void updateTeacher(Teacher teacher){
        repository.save(teacher);
    }

    public void deleteTeacher(Teacher teacher){
        repository.delete(teacher);
    }

    @Override
    public void fillData() {
        createSeveralTeachers(teacherGenerationData.generateData());
    }
}
