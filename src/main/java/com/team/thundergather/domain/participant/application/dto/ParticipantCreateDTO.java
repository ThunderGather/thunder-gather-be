package com.team.thundergather.domain.participant.application.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParticipantCreateDTO {

    @NotNull(message = "참가자 ID는 null일 수 없습니다.")
    private final Long memberId;

    @NotNull(message = "번개 모임 ID는 null일 수 없습니다.")
    private final Long postId;
}
