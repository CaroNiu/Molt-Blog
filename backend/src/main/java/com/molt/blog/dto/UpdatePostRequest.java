package com.molt.blog.dto;

import java.util.List;

public record UpdatePostRequest(
        String title,
        String content,
        String excerpt,
        List<String> tags,
        Boolean published
) {}