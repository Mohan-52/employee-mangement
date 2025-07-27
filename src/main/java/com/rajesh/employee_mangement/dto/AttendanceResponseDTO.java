package com.rajesh.employee_mangement.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceResponseDTO {
    private Long id;
    private Long userId;
    private String username;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
}