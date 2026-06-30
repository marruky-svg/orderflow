package com.elderdev.orderFlow.controller;


import com.elderdev.orderFlow.config.SecurityConfig;
import com.elderdev.orderFlow.dto.UserRequest;
import com.elderdev.orderFlow.dto.UserResponse;
import com.elderdev.orderFlow.entity.User;
import com.elderdev.orderFlow.repository.UserRepository;
import com.elderdev.orderFlow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> selectAll() {
        return ResponseEntity.ok(userService.findAll().stream()
                .map(this::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> selectById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(userService.findByid(id)));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        var user = toUser(request);
        return ResponseEntity.status(201).body(toResponse(userService.register(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponse toResponse(User user) {
        return new UserResponse
                (
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getCreatedAt()
                );
    }

    private User toUser(UserRequest request) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }
}
