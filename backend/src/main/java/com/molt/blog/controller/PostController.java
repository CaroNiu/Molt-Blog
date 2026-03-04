package com.molt.blog.controller;

import com.molt.blog.dto.*;
import com.molt.blog.entity.Post;
import com.molt.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostRepository postRepository;
    
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostDto> dtos = posts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(post -> ResponseEntity.ok(toDto(post)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestBody CreatePostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        Post post = new Post();
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setExcerpt(request.excerpt());
        post.setTags(request.tags() != null ? String.join(",", request.tags()) : null);
        post.setPublished(request.published() != null ? request.published() : false);
        post.setAuthor(userDetails.getUsername());
        
        Post saved = postRepository.save(post);
        return ResponseEntity.ok(toDto(saved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody UpdatePostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        return postRepository.findById(id)
                .map(post -> {
                    if (request.title() != null) post.setTitle(request.title());
                    if (request.content() != null) post.setContent(request.content());
                    if (request.excerpt() != null) post.setExcerpt(request.excerpt());
                    if (request.tags() != null) post.setTags(String.join(",", request.tags()));
                    if (request.published() != null) post.setPublished(request.published());
                    
                    Post saved = postRepository.save(post);
                    return ResponseEntity.ok(toDto(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "删除成功"));
    }
    
    private PostDto toDto(Post post) {
        List<String> tags = post.getTags() != null && !post.getTags().isEmpty()
                ? Arrays.asList(post.getTags().split(","))
                : Collections.emptyList();
        
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getExcerpt(),
                tags,
                post.getPublished(),
                post.getAuthor(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}