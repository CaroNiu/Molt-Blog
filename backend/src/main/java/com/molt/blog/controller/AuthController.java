package com.molt.blog.controller;

import com.molt.blog.dto.*;
import com.molt.blog.entity.User;
import com.molt.blog.repository.UserRepository;
import com.molt.blog.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElse(null);
        
        if (user == null || !passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "用户名或密码错误"));
        }
        
        String token = tokenProvider.generateToken(user.getUsername());
        
        return ResponseEntity.ok(new LoginResponse(token, 
                new UserDto(user.getId(), user.getUsername(), user.getRole())));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // JWT 无状态，客户端删除 token 即可
        return ResponseEntity.ok(Map.of("message", "登出成功"));
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "未登录"));
        }
        
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElse(null);
        
        if (user == null) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "用户不存在"));
        }
        
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getRole()));
    }
}