package com.team.thundergather.global.auth.jwt.dto;

import lombok.Builder;

@Builder
public record TokenDto(
        AccessTokenDto accessTokenDto,
        RefreshTokenDto refreshTokenDto
) {
}
