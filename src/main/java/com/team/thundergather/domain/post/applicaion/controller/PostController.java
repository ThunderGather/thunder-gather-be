package com.team.thundergather.domain.post.applicaion.controller;
import com.team.thundergather.domain.post.applicaion.dto.PostCreateDTO;
import com.team.thundergather.domain.post.applicaion.dto.PostResponseDTO;
import com.team.thundergather.domain.post.applicaion.dto.PostUpdateDTO;
import com.team.thundergather.domain.post.applicaion.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 번개 리스트 불러오기
    @GetMapping("/list")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // 번개 생성
    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostCreateDTO postCreateDTO,
                                                      @RequestParam Long memberId) {
        PostResponseDTO createdPost = postService.createPost(postCreateDTO, memberId);
        return ResponseEntity.ok(createdPost);
    }

    // 번개 정보 불러오기
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long postId) {
        PostResponseDTO post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    // 번개 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long postId,
                                                      @Valid @RequestBody PostUpdateDTO postUpdateDTO) {
        PostResponseDTO updatedPost = postService.updatePost(postId, postUpdateDTO);
        return ResponseEntity.ok(updatedPost);
    }

    // 번개 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
