package com.team.thundergather.domain.post.applicaion.service;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.member.dataAccess.repository.MemberRepository;
import com.team.thundergather.domain.post.applicaion.dto.PostCreateDTO;
import com.team.thundergather.domain.post.applicaion.dto.PostResponseDTO;
import com.team.thundergather.domain.post.applicaion.dto.PostUpdateDTO;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import com.team.thundergather.domain.post.dataAccess.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    // 모든 번개 리스트 불러오기
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 번개 생성
    @Transactional
    public PostResponseDTO createPost(PostCreateDTO postCreateDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + memberId + ")의 사용자를 찾을 수 없습니다."));

        Post post = Post.of(postCreateDTO, member);
        postRepository.save(post);

        return new PostResponseDTO(post);
    }

    // 번개 정보 불러오기
    @Transactional(readOnly = true)
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + postId + ")의 번개를 찾을 수 없습니다."));

        return new PostResponseDTO(post);
    }

    // 번개 수정
    @Transactional
    public PostResponseDTO updatePost(Long postId, PostUpdateDTO postUpdateDTO, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + postId + "의 번개를 찾을 수 없습니다."));

        // 작성자 확인
        if (!post.getMember().getId().equals(memberId)) {
            throw new IllegalStateException("해당 번개를 수정할 권한이 없습니다.");
        }

        post.update(postUpdateDTO);
        return new PostResponseDTO(post);
    }

    // 번개 삭제
    @Transactional
    public void deletePost(Long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + postId + ")의 번개를 찾을 수 없습니다."));

        // 작성자 확인
        if (!post.getMember().getId().equals(memberId)) {
            throw new IllegalStateException("해당 번개를 삭제할 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}
