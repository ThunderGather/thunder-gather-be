package com.team.thundergather.domain.post.applicaion.dto;

import com.team.thundergather.domain.post.dataAccess.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDTO {

    private final Long id;
    private final String title;
    private final String category;
    private final String desiredDate;
    private final String desiredTime;
    private final int maxParticipants;
    private final String description;
    private final String location;
    private final String openChatUrl;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.category = post.getCategory();
        this.desiredDate = post.getDesiredDate();
        this.desiredTime = post.getDesiredTime();
        this.maxParticipants = post.getMaxParticipants();
        this.description = post.getDescription();
        this.location = post.getLocation();
        this.openChatUrl = post.getOpenChatUrl();
    }
}
