package com.university.universitycms.service;

import com.university.universitycms.domain.Course;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.domain.dto.TeacherDTO;
import com.university.universitycms.domain.mapper.TeacherMapper;
import com.university.universitycms.email.EmailSender;
import com.university.universitycms.generation.impl.PasswordGeneration;
import com.university.universitycms.generation.impl.TeacherGenerationData;
import com.university.universitycms.repository.CourseRepository;
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
    private final CourseRepository courseRepository;
    private final TeacherMapper teacherMapper;

    @Autowired
    public TeacherService(TeacherRepository repository, TeacherGenerationData teacherGenerationData,
                          EmailSender emailSender, PasswordGeneration passwordGeneration, PasswordEncoder passwordEncoder,
                          CourseRepository courseRepository, TeacherMapper teacherMapper) {
        this.repository = repository;
        this.teacherGenerationData = teacherGenerationData;
        this.emailSender = emailSender;
        this.passwordGeneration = passwordGeneration;
        this.passwordEncoder = passwordEncoder;
        this.courseRepository = courseRepository;
        this.teacherMapper = teacherMapper;
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

    public void registerTeacher(TeacherDTO teacherDTO){
        Set<Course> courses = findFewCourse(teacherDTO.getCoursesId());

        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        teacher.setCourses(courses);

        this.createTeacher(teacher);
    }

    public void updateTeacher(TeacherDTO teacherDTO){
        Set<Course> courses = findFewCourse(teacherDTO.getCoursesId());

        Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherDTO);
        teacher.setCourses(courses);

        repository.save(teacher);
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
        Set<Course> courses = courseRepository.findAllByIdIn(coursesId);
        Set<Long> foundIds = courses.stream().map(Course::getId).collect(Collectors.toSet())

        if (foundIds.size() != coursesId.size()){
            Set<Long> notFoundIds = new HashSet<>(coursesId);
            notFoundIds.removeAll(foundIds);

            throw new IllegalArgumentException("Courses with ids: " + notFoundIds + " not found");
        }

        return courses;
    }
}
