package com.rajesh.employee_mangement.controller;


import com.rajesh.employee_mangement.dto.AttendanceRequestDTO;
import com.rajesh.employee_mangement.dto.AttendanceResponseDTO;
import com.rajesh.employee_mangement.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    //  Check-In (Employee only)
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<AttendanceResponseDTO> checkIn(@RequestBody AttendanceRequestDTO request) {
        return ResponseEntity.ok(attendanceService.checkIn(request));
    }

    //  Check-Out (Employee only)
    @PostMapping("/check-out")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<AttendanceResponseDTO> checkOut(@RequestBody AttendanceRequestDTO request) {
        return ResponseEntity.ok(attendanceService.checkOut(request));
    }

    //  Get Own Attendance (Employee, HR, Manager)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HR', 'MANAGER')")
    public ResponseEntity<List<AttendanceResponseDTO>> getUserAttendance(@PathVariable Long userId) {
        return ResponseEntity.ok(attendanceService.getUserAttendance(userId));
    }

    //  Get Department Attendance (HR, Manager)
    @GetMapping("/department/{deptId}")
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<AttendanceResponseDTO>> getDeptAttendance(@PathVariable Long deptId) {
        return ResponseEntity.ok(attendanceService.getDepartmentAttendance(deptId));
    }
}
