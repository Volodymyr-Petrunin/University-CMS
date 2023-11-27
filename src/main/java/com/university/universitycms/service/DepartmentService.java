package com.university.universitycms.service;

import com.university.universitycms.domain.Department;
import com.university.universitycms.generation.impl.DepartmentGenerationData;
import com.university.universitycms.repository.DepartmentRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepartmentService implements DataFiller {

    private final DepartmentRepository repository;
    private final DepartmentGenerationData departmentGenerationData;

    @Autowired
    public DepartmentService(DepartmentRepository repository, DepartmentGenerationData departmentGenerationData) {
        this.repository = repository;
        this.departmentGenerationData = departmentGenerationData;
    }

    public List<Department> getAllDepartments(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Department> getDepartmentById(long departmentId){
        return repository.findById(departmentId);
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

    @Override
    public void fillData() {
        createSeveralDepartment(departmentGenerationData.generateData());
    }
}
