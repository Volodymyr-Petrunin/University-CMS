package com.university.universitycms.service;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.domain.dto.TeacherDTO;
import com.university.universitycms.domain.mapper.CourseMapper;
import com.university.universitycms.domain.mapper.TeacherMapper;
import com.university.universitycms.email.EmailSender;
import com.university.universitycms.generation.impl.PasswordGeneration;
import com.university.universitycms.generation.impl.TeacherGenerationData;
import com.university.universitycms.repository.TeacherRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeacherService implements DataFiller {
    private final TeacherRepository repository;
    private final TeacherGenerationData teacherGenerationData;
    private final EmailSender emailSender;
    private final PasswordGeneration passwordGeneration;
    private final PasswordEncoder passwordEncoder;
    private final CourseService courseService;
    private final TeacherMapper teacherMapper;
    private final CourseMapper courseMapper;

    @Autowired
    public TeacherService(TeacherRepository repository, TeacherGenerationData teacherGenerationData,
                          EmailSender emailSender, PasswordGeneration passwordGeneration, PasswordEncoder passwordEncoder,
                          CourseService courseService, TeacherMapper teacherMapper, CourseMapper courseMapper) {
        this.repository = repository;
        this.teacherGenerationData = teacherGenerationData;
        this.emailSender = emailSender;
        this.passwordGeneration = passwordGeneration;
        this.passwordEncoder = passwordEncoder;
        this.courseService = courseService;
        this.teacherMapper = teacherMapper;
        this.courseMapper = courseMapper;
    }

    public List<Teacher> getAllTeachers(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Teacher> getTeacherById(long teacherId){
        return repository.findById(teacherId);
    }

    public void createTeacher(Teacher teacher){
        char[] password = passwordGeneration.generatePassword();
        teacher.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));
        repository.save(teacher);

        emailSender.sendRegistrationConfirmation(teacher.getName(), teacher.getEmail(), password);
        Arrays.fill(password, '\0');
    }

    public void createSeveralTeachers(List<Teacher> teachers){
       teachers.forEach(this::createTeacher);
    }

    public void registerTeacher(TeacherDTO teacherDTO, List<Long> coursesId){
        Set<Course> courses = findFewCourse(coursesId);

        teacherDTO.setCourses(courses.stream().map(courseMapper::courseToCourseDTO).collect(Collectors.toSet()));

        this.createTeacher(teacherMapper.teacherDTOToTeacher(teacherDTO));
    }

    public void updateTeacher(TeacherDTO teacherDTO, List<Long> coursesId){
        Set<Course> courses = findFewCourse(coursesId);

        teacherDTO.setCourses(courses.stream().map(courseMapper::courseToCourseDTO).collect(Collectors.toSet()));

        repository.save(teacherMapper.teacherDTOToTeacher(teacherDTO));
    }

    public void deleteTeacher(Teacher teacher){
        repository.delete(teacher);
    }

    public void deleteTeacherById(long id){
        repository.deleteById(id);
    }

    @Override
    public void fillData() {
        createSeveralTeachers(teacherGenerationData.generateData());
    }

    private Set<Course> findFewCourse(List<Long> coursesId){
        return coursesId.stream()
                .map(id -> courseService.getCourseById(id)
                        .orElseThrow(() -> new IllegalArgumentException("Course with id: " + id + " not found")))
                .collect(Collectors.toSet());
    }
}
