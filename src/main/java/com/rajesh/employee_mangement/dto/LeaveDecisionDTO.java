package com.rajesh.employee_mangement.dto;


import lombok.Data;

@Data
public class LeaveDecisionDTO {
    private Long leaveId;
    private boolean approve; // true = approve, false = reject
}