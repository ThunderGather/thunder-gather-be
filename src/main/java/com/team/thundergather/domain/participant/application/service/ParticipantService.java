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
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    // 내가 참여한 번개 목록 조회
    @Transactional(readOnly = true)
    public List<PostResponseDTO> getMyMeetings(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + memberId + ")의 사용자를 찾을 수 없습니다."));

        List<Participant> participants = participantRepository.findByMember(member);

        return participants.stream()
                .map(participant -> new PostResponseDTO(participant.getPost()))
                .collect(Collectors.toList());
    }

    // 번개 참여
    @Transactional
    public void joinMeeting(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + memberId + ")의 사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + postId + ")의 번개를 찾을 수 없습니다."));

        if (participantRepository.findByPostAndMember(post, member).isPresent()) {
            throw new IllegalStateException("당신은 이미 이 번개에 참여했습니다.");
        }

        Participant participant = new Participant(member, post);
        participantRepository.save(participant);
    }

    // 번개 참여 취소
    @Transactional
    public void cancelMeeting(Long postId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + memberId + ")의 사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID(" + memberId + ")의 번개를 찾을 수 없습니다."));

        Participant participant = participantRepository.findByPostAndMember(post, member)
                .orElseThrow(() -> new IllegalArgumentException("당신은 이 번개의 참가자가 아닙니다."));

        participantRepository.delete(participant);
    }
}
