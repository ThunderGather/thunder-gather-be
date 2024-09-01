package com.team.thundergather.domain.participant.application.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantResponseDTO {
    private final Long memberId;            // 참가자 ID
    private final String memberNickname;    // 참가자 닉네임
    private final String profileImageUrl;   // 참가자 이미지 경로

    public ParticipantResponseDTO(Long id, String memberNickname, String profileImageUrl) {
        this.memberId = id;
        this.memberNickname = memberNickname;
        this.profileImageUrl = profileImageUrl;
    }
}
