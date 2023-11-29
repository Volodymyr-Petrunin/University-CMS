package com.university.universitycms.service;

import com.university.universitycms.domain.Role;
import com.university.universitycms.domain.Student;
import com.university.universitycms.domain.Teacher;
import com.university.universitycms.repository.StudentRepository;
import com.university.universitycms.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    @Autowired
    public CustomUserDetailsService(StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmail(email);

        if (student != null) {
            return createUserDetails(student.getEmail(), student.getPassword(), student.getRole());
        }

        Teacher teacher = teacherRepository.findByEmail(email);

        if (teacher != null) {
            return createUserDetails(teacher.getEmail(), teacher.getPassword(), teacher.getRole());
        }

        throw new UsernameNotFoundException("User with email " + email + " was not found.");
    }

    private UserDetails createUserDetails(String email, String password, Role role){
        return new User(email, password, Collections.singletonList(new SimpleGrantedAuthority(role.getRoleName())));
    }
}
