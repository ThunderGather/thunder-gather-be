package com.team.thundergather.domain.post.applicaion.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateDTO {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "날짜는 필수입니다.")
    private String desiredDate;

    @NotBlank(message = "시간은 필수입니다.")
    private String desiredTime;

    @NotBlank(message = "카테고리는 필수입니다.")
    private String category;

    private int maxParticipants;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotBlank(message = "위치는 필수입니다.")
    private String location;

    private String openChatUrl;

    @Builder
    public PostCreateDTO(String title, String desiredDate, String desiredTime, String category,
                         int maxParticipants, String description, String location, String openChatUrl) {
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
