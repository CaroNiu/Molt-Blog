package com.molt.blog.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record CreatePostRequest(
        @NotBlank String title,
        String content,
        String excerpt,
        List<String> tags,
        Boolean published
) {}