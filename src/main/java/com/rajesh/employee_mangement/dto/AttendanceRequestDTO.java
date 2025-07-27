package com.rajesh.employee_mangement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequestDTO {
    private Long userId;
    private LocalDate date; // optional, default to today
}