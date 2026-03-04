package com.molt.blog.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostDto(
        Long id,
        String title,
        String content,
        String excerpt,
        List<String> tags,
        Boolean published,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}