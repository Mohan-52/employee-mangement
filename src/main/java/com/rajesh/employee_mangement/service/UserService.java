package com.rajesh.employee_mangement.service;

import com.rajesh.employee_mangement.dto.UserCreateDTO;
import com.rajesh.employee_mangement.dto.UserResponseDTO;
import com.rajesh.employee_mangement.entity.Department;
import com.rajesh.employee_mangement.entity.User;
import com.rajesh.employee_mangement.entity.UserRole;
import com.rajesh.employee_mangement.repository.DepartmentRepository;
import com.rajesh.employee_mangement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ------------------- Find By Username (for JwtUtil) -------------------
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // ------------------- Create User -------------------
    public UserResponseDTO createUser(UserCreateDTO dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists.");
        }

        Department department = null;
        if (dto.getDepartmentName() != null) {
            department = departmentRepository.findByName(dto.getDepartmentName())
                    .orElseGet(() -> departmentRepository.save(new Department(null, dto.getDepartmentName())));
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());
        user.setDepartment(department);

        return toResponseDTO(userRepository.save(user));
    }

    // ------------------- Get All Users -------------------
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Get by ID -------------------
    public Optional<UserResponseDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::toResponseDTO);
    }

    // ------------------- Delete User -------------------
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    // ------------------- Update User -------------------
    public UserResponseDTO updateUser(Long id, UserCreateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(dto.getFullName());
        user.setRole(dto.getRole());

        if (dto.getDepartmentName() != null) {
            Department department = departmentRepository.findByName(dto.getDepartmentName())
                    .orElseGet(() -> departmentRepository.save(new Department(null, dto.getDepartmentName())));
            user.setDepartment(department);
        }

        return toResponseDTO(userRepository.save(user));
    }

    // ------------------- Get by Role -------------------
    public List<UserResponseDTO> getUsersByRole(String roleName) {
        return userRepository.findByRole(Enum.valueOf(UserRole.class, roleName.toUpperCase()))
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Get by Department -------------------
    public List<UserResponseDTO> getUsersByDepartment(Long departmentId) {
        return userRepository.findByDepartmentId(departmentId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ------------------- DTO Mapper -------------------
    private UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setRole(user.getRole());
        dto.setDepartmentName(user.getDepartment() != null ? user.getDepartment().getName() : null);
        return dto;
    }
}
