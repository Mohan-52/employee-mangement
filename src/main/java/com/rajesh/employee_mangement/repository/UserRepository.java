package com.rajesh.employee_mangement.repository;



import com.rajesh.employee_mangement.entity.User;
import com.rajesh.employee_mangement.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(UserRole role);
    List<User> findByDepartmentId(Long departmentId);
}
