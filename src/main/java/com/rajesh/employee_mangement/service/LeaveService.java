package com.rajesh.employee_mangement.service;

import com.rajesh.employee_mangement.dto.LeaveDecisionDTO;
import com.rajesh.employee_mangement.dto.LeaveRequestDTO;
import com.rajesh.employee_mangement.dto.LeaveResponseDTO;
import com.rajesh.employee_mangement.entity.LeaveRequest;
import com.rajesh.employee_mangement.entity.LeaveStatus;
import com.rajesh.employee_mangement.entity.User;
import com.rajesh.employee_mangement.repository.LeaveRequestRepository;
import com.rajesh.employee_mangement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    // ------------------- Apply for Leave -------------------
    public LeaveResponseDTO applyLeave(LeaveRequestDTO dto) {
        User employee = getUserById(dto.getEmployeeId());

        LeaveRequest leave = new LeaveRequest();
        leave.setEmployee(employee);
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setReason(dto.getReason());
        leave.setStatus(LeaveStatus.PENDING);

        return toDTO(leaveRepository.save(leave));
    }

    // ------------------- Approve or Reject Leave -------------------
    public LeaveResponseDTO handleDecision(LeaveDecisionDTO decision) {
        LeaveRequest leave = leaveRepository.findById(decision.getLeaveId())
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        leave.setStatus(decision.isApprove() ? LeaveStatus.APPROVED : LeaveStatus.REJECTED);
        return toDTO(leaveRepository.save(leave));
    }

    // ------------------- Get Leave by User -------------------
    public List<LeaveResponseDTO> getLeaveByUser(Long userId) {
        return leaveRepository.findByEmployeeId(userId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Get Leave by Department -------------------
    public List<LeaveResponseDTO> getLeaveByDepartment(Long deptId) {
        return leaveRepository.findByEmployeeDepartmentId(deptId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Get Pending Leaves -------------------
    public List<LeaveResponseDTO> getPendingLeaves() {
        return leaveRepository.findByStatus(LeaveStatus.PENDING).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Helper -------------------
    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private LeaveResponseDTO toDTO(LeaveRequest leave) {
        LeaveResponseDTO dto = new LeaveResponseDTO();
        dto.setId(leave.getId());
        dto.setEmployeeName(leave.getEmployee().getFullName());
        dto.setDepartmentName(
                leave.getEmployee().getDepartment() != null
                        ? leave.getEmployee().getDepartment().getName()
                        : null
        );
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setReason(leave.getReason());
        dto.setStatus(leave.getStatus());
        return dto;
    }
}
