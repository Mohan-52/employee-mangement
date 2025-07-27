package com.rajesh.employee_mangement.repository;




import com.rajesh.employee_mangement.entity.Attendance;
import com.rajesh.employee_mangement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByUserAndDate(User user, LocalDate date);
    List<Attendance> findByUserId(Long userId);
    List<Attendance> findByUserDepartmentId(Long departmentId); // for managers
}
