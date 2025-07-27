package com.rajesh.employee_mangement.dto;

import com.rajesh.employee_mangement.entity.UserRole;
import lombok.Data;


@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private UserRole role;
    private String departmentName;
}