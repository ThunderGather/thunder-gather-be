package com.team.thundergather.global.auth.jwt.dto;

import lombok.Builder;

@Builder
public record AccessTokenDto(
        String token,
        Long expiresIn
) {
}
