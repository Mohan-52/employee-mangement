package com.rajesh.employee_mangement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "date"}))
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
}
