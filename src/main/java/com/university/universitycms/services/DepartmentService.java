package com.university.universitycms.services;

import com.university.universitycms.domain.Department;
import com.university.universitycms.repositories.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository repository;

    @Autowired
    public DepartmentService(DepartmentRepository repository) {
        this.repository = repository;
    }

    public List<Department> getAllDepartment(){
        return repository.findAll();
    }

    public void createDepartment(Department department){
        repository.save(department);
    }

    public void createSeveralDepartment(List<Department> departments){
        repository.saveAll(departments);
    }

    public void updateDepartment(Department department){
        repository.save(department);
    }

    public void deleteDepartment(Department department){
        repository.delete(department);
    }
}
