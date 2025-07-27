package com.rajesh.employee_mangement.service;


import com.rajesh.employee_mangement.dto.DepartmentDTO;
import com.rajesh.employee_mangement.entity.Department;
import com.rajesh.employee_mangement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // ------------------- Create Department -------------------
    public DepartmentDTO createDepartment(String name) {
        Optional<Department> existing = departmentRepository.findByName(name);
        if (existing.isPresent()) {
            return toDTO(existing.get());
        }

        Department department = new Department();
        department.setName(name);
        return toDTO(departmentRepository.save(department));
    }

    // ------------------- Get by Name -------------------
    public Optional<DepartmentDTO> findByName(String name) {
        return departmentRepository.findByName(name)
                .map(this::toDTO);
    }

    // ------------------- Get All Departments -------------------
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Get by ID -------------------
    public Optional<DepartmentDTO> getById(Long id) {
        return departmentRepository.findById(id)
                .map(this::toDTO);
    }

    // ------------------- Entity to DTO Mapper -------------------
    private DepartmentDTO toDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        return dto;
    }
}
