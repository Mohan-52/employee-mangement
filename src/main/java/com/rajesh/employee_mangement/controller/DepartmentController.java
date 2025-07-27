package com.rajesh.employee_mangement.controller;


import com.rajesh.employee_mangement.dto.DepartmentDTO;
import com.rajesh.employee_mangement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // Create Department (Admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentDTO> createDepartment(@RequestParam String name) {
        return ResponseEntity.ok(departmentService.createDepartment(name));
    }

    // 📋 Get All Departments (Admin, HR, Manager)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    // 🔍 Get Department by ID (Admin, HR, Manager)
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public ResponseEntity<DepartmentDTO> getById(@PathVariable Long id) {
        Optional<DepartmentDTO> dto = departmentService.getById(id);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔍 Get Department by Name (Admin, HR, Manager)
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'HR', 'MANAGER')")
    public ResponseEntity<DepartmentDTO> getByName(@RequestParam String name) {
        Optional<DepartmentDTO> dto = departmentService.findByName(name);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
