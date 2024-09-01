package com.team.thundergather.domain.post.applicaion.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostResponseDTO {

    private Long postId;
    private Long memberId;
    private String memberEmail;
    private String title;
    private String desiredDate;
    private String desiredTime;
    private String category;
    private int maxParticipants;
    private String description;
    private String location;
    private String openChatUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PostResponseDTO(Long postId, Long memberId, String memberEmail, String title, String desiredDate,
                           String desiredTime, String category, int maxParticipants, String description,
                           String location, String openChatUrl, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
        this.title = title;
        this.desiredDate = desiredDate;
        this.desiredTime = desiredTime;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.location = location;
        this.openChatUrl = openChatUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
