package com.kiosko.authservice.controller;

import com.kiosko.authservice.domain.repository.PermissionRepository;
import com.kiosko.authservice.domain.repository.RoleRepository;
import com.kiosko.authservice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @GetMapping("/database")
    public Map<String, Object> testDatabase() {
        Map<String, Object> result = new HashMap<>();

        try {
            long userCount = userRepository.count();
            long roleCount = roleRepository.count();
            long permissionCount = permissionRepository.count();

            result.put("status", "success");
            result.put("users", userCount);
            result.put("roles", roleCount);
            result.put("permissions", permissionCount);
            result.put("message", "Database connection working correctly");

        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
        }

        return result;
    }
}