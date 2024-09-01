package com.team.thundergather.domain.participant.application.service;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.member.dataAccess.repository.MemberRepository;
import com.team.thundergather.domain.participant.dataAccess.entity.Participant;
import com.team.thundergather.domain.participant.dataAccess.repository.ParticipantRepository;
import com.team.thundergather.domain.post.applicaion.dto.PostResponseDTO;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import com.team.thundergather.domain.post.dataAccess.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    // 내가 참여한 번개 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getMyMeetings(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        return participantRepository.findByMember(member).stream()
                .map(Participant::getPost)
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // 번개 참여
    @Transactional
    public void joinMeeting(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));

        if (participantRepository.existsByMemberAndPost(member, post)) {
            throw new IllegalArgumentException("Already joined the meeting");
        }

        Participant participant = new Participant(member, post);
        participantRepository.save(participant);
    }

    // 번개 참여 취소
    @Transactional
    public void cancelMeeting(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));

        Participant participant = participantRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new IllegalArgumentException("Not participating in the meeting"));

        participantRepository.delete(participant);
    }

    // DTO
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
}
