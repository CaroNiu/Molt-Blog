package com.molt.blog.dto;

public record UserDto(
        Long id,
        String username,
        String role
) {}