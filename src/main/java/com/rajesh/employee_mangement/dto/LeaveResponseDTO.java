package com.rajesh.employee_mangement.dto;

import com.rajesh.employee_mangement.entity.LeaveStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class LeaveResponseDTO {
    private Long id;
    private String employeeName;
    private String departmentName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status;
}