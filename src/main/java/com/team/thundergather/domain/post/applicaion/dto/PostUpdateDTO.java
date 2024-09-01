package com.team.thundergather.domain.post.applicaion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUpdateDTO {

    private String title;
    private String desiredDate;
    private String desiredTime;
    private String category;
    private Integer maxParticipants;
    private String description;
    private String location;
    private String openChatUrl;

    @Builder
    public PostUpdateDTO(String title, String desiredDate, String desiredTime, String category,
                         Integer maxParticipants, String description, String location, String openChatUrl) {
        this.title = title;
        this.desiredDate = desiredDate;
        this.desiredTime = desiredTime;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.location = location;
        this.openChatUrl = openChatUrl;
    }
}
