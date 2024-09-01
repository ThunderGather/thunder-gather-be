package com.team.thundergather.domain.participant.application.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantResponseDTO {
    private final Long id;
    private final Long memberId;
    private final Long postId;
    private final String memberNickname;
    private final String profileImageUrl;

    public ParticipantResponseDTO(Long id, Long memberId, Long postId, String memberNickname, String profileImageUrl) {
        this.id = id;
        this.memberId = memberId;
        this.postId = postId;
        this.memberNickname = memberNickname;
        this.profileImageUrl = profileImageUrl;
    }
}
