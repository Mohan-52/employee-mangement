package com.rajesh.employee_mangement.controller;


import com.rajesh.employee_mangement.dto.LeaveDecisionDTO;
import com.rajesh.employee_mangement.dto.LeaveRequestDTO;
import com.rajesh.employee_mangement.dto.LeaveResponseDTO;
import com.rajesh.employee_mangement.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    //  Employee applies for leave
    @PostMapping("/apply")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<LeaveResponseDTO> applyLeave(@RequestBody LeaveRequestDTO request) {
        return ResponseEntity.ok(leaveService.applyLeave(request));
    }

    //  Get leave history for a specific user (EMPLOYEE, HR, MANAGER)
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'HR', 'MANAGER')")
    public ResponseEntity<List<LeaveResponseDTO>> getUserLeaves(@PathVariable Long userId) {
        return ResponseEntity.ok(leaveService.getLeaveByUser(userId));
    }

    //  Get department-wide leave requests (HR & MANAGER)
    @GetMapping("/department/{deptId}")
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<LeaveResponseDTO>> getDeptLeaves(@PathVariable Long deptId) {
        return ResponseEntity.ok(leaveService.getLeaveByDepartment(deptId));
    }

    //  Get all pending leaves (HR & MANAGER)
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<List<LeaveResponseDTO>> getPendingLeaves() {
        return ResponseEntity.ok(leaveService.getPendingLeaves());
    }

    // ✅ Approve/Reject leave (HR & MANAGER)
    @PostMapping("/decide")
    @PreAuthorize("hasAnyRole('HR', 'MANAGER')")
    public ResponseEntity<LeaveResponseDTO> decideLeave(@RequestBody LeaveDecisionDTO decision) {
        return ResponseEntity.ok(leaveService.handleDecision(decision));
    }
}
