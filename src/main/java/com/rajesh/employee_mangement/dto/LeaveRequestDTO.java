package com.rajesh.employee_mangement.dto;


import lombok.Data;

import java.time.LocalDate;


@Data
public class LeaveRequestDTO {
    private Long employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}