package com.rajesh.employee_mangement.controller;

import com.rajesh.employee_mangement.dto.UserCreateDTO;
import com.rajesh.employee_mangement.dto.UserResponseDTO;
import com.rajesh.employee_mangement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    // ➕ Create User
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }

    // 📋 Get All Users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔍 Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🗑 Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // 🛠 Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,
                                                      @RequestBody UserCreateDTO updatedData) {
        return ResponseEntity.ok(userService.updateUser(id, updatedData));
    }
}
