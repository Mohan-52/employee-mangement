package com.rajesh.employee_mangement.dto;

import com.rajesh.employee_mangement.entity.UserRole;
import lombok.Data;

@Data
public class UserCreateDTO {
    private String username;
    private String password;
    private String fullName;
    private UserRole role;
    private String departmentName; // reference by name
}