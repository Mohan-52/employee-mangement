package com.rajesh.employee_mangement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id")
    private User employee;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status = LeaveStatus.PENDING;
}