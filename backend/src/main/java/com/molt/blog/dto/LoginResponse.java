package com.molt.blog.dto;

public record LoginResponse(
        String token,
        UserDto user
) {}