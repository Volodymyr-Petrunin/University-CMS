package com.university.universitycms.service;

import com.university.universitycms.domain.Department;
import com.university.universitycms.domain.dto.DepartmentDTO;
import com.university.universitycms.domain.mapper.DepartmentMapper;
import com.university.universitycms.generation.impl.DepartmentGenerationData;
import com.university.universitycms.repository.DepartmentRepository;
import com.university.universitycms.filldata.DataFiller;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Order(3)
public class DepartmentService implements DataFiller {

    private final DepartmentRepository repository;
    private final DepartmentGenerationData departmentGenerationData;
    private final DepartmentMapper departmentMapper;

    @Autowired
    public DepartmentService(DepartmentRepository repository, DepartmentGenerationData departmentGenerationData,
                             DepartmentMapper departmentMapper) {
        this.repository = repository;
        this.departmentGenerationData = departmentGenerationData;
        this.departmentMapper = departmentMapper;
    }

    public List<Department> getAllDepartments(){
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Department getDepartmentById(long departmentId){
        return repository.findById(departmentId)
                .orElseThrow(() -> new IllegalArgumentException("Can't find department with id: " + departmentId));
    }

    public void createDepartment(Department department){
        repository.save(department);
    }

    public void createSeveralDepartment(List<Department> departments){
        repository.saveAll(departments);
    }

    public void registerDepartment(DepartmentDTO departmentDTO){
        this.createDepartment(departmentMapper.departmentDTOToDepartment(departmentDTO));
    }

    public void updateDepartment(DepartmentDTO departmentDTO){
        repository.save(departmentMapper.departmentDTOToDepartment(departmentDTO));
    }

    public void deleteDepartment(Department department){
        repository.delete(department);
    }

    public void deleteDepartmentById(long id) {
        repository.deleteById(id);
    }

    @Override
    public void fillData() {
        createSeveralDepartment(departmentGenerationData.generateData());
    }
}
