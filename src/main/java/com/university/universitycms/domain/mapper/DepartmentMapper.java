package com.university.universitycms.domain.mapper;

import com.university.universitycms.domain.Department;
import com.university.universitycms.domain.dto.DepartmentDTO;
import com.university.universitycms.repository.CourseRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DepartmentMapper {
    @Autowired
    private CourseRepository repository;

    public abstract DepartmentDTO departmentToDepartmentDTO(Department department);

    public abstract Department departmentDTOToDepartment(DepartmentDTO departmentDTO);

    @AfterMapping
    public void fetchEntities(@MappingTarget Department department, DepartmentDTO departmentDTO){
        department.setCourses(repository.findAllByIdIn(departmentDTO.getCoursesId()));
    }
}
