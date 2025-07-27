package com.rajesh.employee_mangement.service;


import com.rajesh.employee_mangement.dto.AttendanceRequestDTO;
import com.rajesh.employee_mangement.dto.AttendanceResponseDTO;
import com.rajesh.employee_mangement.entity.Attendance;
import com.rajesh.employee_mangement.entity.User;
import com.rajesh.employee_mangement.repository.AttendanceRepository;
import com.rajesh.employee_mangement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    // ------------------- Check-in -------------------
    public AttendanceResponseDTO checkIn(AttendanceRequestDTO request) {
        User user = getUserById(request.getUserId());
        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();

        Optional<Attendance> existing = attendanceRepository.findByUserAndDate(user, date);

        if (existing.isPresent()) {
            Attendance record = existing.get();
            if (record.getCheckIn() != null) {
                throw new RuntimeException("Already checked in for " + date);
            }
            record.setCheckIn(LocalTime.now());
            return toDTO(attendanceRepository.save(record));
        } else {
            Attendance newRecord = new Attendance();
            newRecord.setUser(user);
            newRecord.setDate(date);
            newRecord.setCheckIn(LocalTime.now());
            return toDTO(attendanceRepository.save(newRecord));
        }
    }

    // ------------------- Check-out -------------------
    public AttendanceResponseDTO checkOut(AttendanceRequestDTO request) {
        User user = getUserById(request.getUserId());
        LocalDate date = request.getDate() != null ? request.getDate() : LocalDate.now();

        Attendance record = attendanceRepository.findByUserAndDate(user, date)
                .orElseThrow(() -> new RuntimeException("Check-in not found for " + date));

        if (record.getCheckOut() != null) {
            throw new RuntimeException("Already checked out for " + date);
        }

        record.setCheckOut(LocalTime.now());
        return toDTO(attendanceRepository.save(record));
    }

    // ------------------- Get User Attendance -------------------
    public List<AttendanceResponseDTO> getUserAttendance(Long userId) {
        return attendanceRepository.findByUserId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Get Department Attendance -------------------
    public List<AttendanceResponseDTO> getDepartmentAttendance(Long departmentId) {
        return attendanceRepository.findByUserDepartmentId(departmentId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Helper Methods -------------------
    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    private AttendanceResponseDTO toDTO(Attendance attendance) {
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        dto.setId(attendance.getId());
        dto.setUserId(attendance.getUser().getId());
        dto.setUsername(attendance.getUser().getUsername());
        dto.setDate(attendance.getDate());
        dto.setCheckIn(attendance.getCheckIn());
        dto.setCheckOut(attendance.getCheckOut());
        return dto;
    }
}
