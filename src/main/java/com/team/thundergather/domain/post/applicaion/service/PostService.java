package com.team.thundergather.domain.post.applicaion.service;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.member.dataAccess.repository.MemberRepository;
import com.team.thundergather.domain.post.applicaion.dto.PostCreateDTO;
import com.team.thundergather.domain.post.applicaion.dto.PostResponseDTO;
import com.team.thundergather.domain.post.applicaion.dto.PostUpdateDTO;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import com.team.thundergather.domain.post.dataAccess.repository.PostRepository;
import com.team.thundergather.domain.participant.dataAccess.repository.ParticipantRepository;
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
    private final ParticipantRepository participantRepository;

    // 모든 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // 게시글 생성
    @Transactional
    public PostResponseDTO createPost(PostCreateDTO postCreateDTO, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        Post post = Post.of(postCreateDTO, member);
        Post savedPost = postRepository.save(post);
        return mapToResponseDTO(savedPost);
    }

    // 개별 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDTO getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));

        return mapToDetailedResponseDTO(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDTO updatePost(Long postId, PostUpdateDTO postUpdateDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));

        post.update(postUpdateDTO);
        Post updatedPost = postRepository.save(post);
        return mapToResponseDTO(updatedPost);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    // 모든 게시글 조회용 DTO 매핑
    private PostResponseDTO mapToResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getId())
                .memberId(post.getMember().getId())
                .memberEmail(post.getMember().getEmail())
                .title(post.getTitle())
                .desiredDate(post.getDesiredDate())
                .desiredTime(post.getDesiredTime())
                .category(post.getCategory())
                .maxParticipants(post.getMaxParticipants())
                .description(post.getDescription())
                .location(post.getLocation())
                .openChatUrl(post.getOpenChatUrl())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .participants(post.getParticipants().stream()
                        .map(participant -> new PostResponseDTO.ParticipantDTO(
                                participant.getMember().getId(),
                                participant.getMember().getNickname(),
                                participant.getMember().getProfileImage()))
                        .collect(Collectors.toList()))
                .build();
    }

    // 개별 게시글 상세 조회용 DTO 매핑
    private PostResponseDTO mapToDetailedResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .desiredDate(post.getDesiredDate())
                .desiredTime(post.getDesiredTime())
                .category(post.getCategory())
                .maxParticipants(post.getMaxParticipants())
                .description(post.getDescription())
                .location(post.getLocation())
                .openChatUrl(post.getOpenChatUrl())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .participants(post.getParticipants().size())
                .members(post.getParticipants().stream()
                        .map(participant -> new PostResponseDTO.MemberDTO(
                                participant.getMember().getNickname(),
                                participant.getMember().getProfileImage(),
                                participant.getMember().equals(post.getMember())))
                        .collect(Collectors.toList()))
                .build();
    }
}
