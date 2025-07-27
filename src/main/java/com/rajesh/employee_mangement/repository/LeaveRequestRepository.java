package com.rajesh.employee_mangement.repository;

import com.rajesh.employee_mangement.entity.LeaveRequest;
import com.rajesh.employee_mangement.entity.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployeeId(Long employeeId);
    List<LeaveRequest> findByStatus(LeaveStatus status);
    List<LeaveRequest> findByEmployeeDepartmentId(Long departmentId); // for manager
}
