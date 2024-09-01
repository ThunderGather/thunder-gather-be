package com.team.thundergather.domain.post.applicaion.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateDTO {

    @NotBlank(message = "제목 입력은 필수입니다.")
    private String title;

    @NotBlank(message = "카테고리 입력은 필수입니다.")
    private String category;

    @NotBlank(message = "날짜 입력은 필수입니다.")
    private String desiredDate;

    @NotBlank(message = "시간 입력은 필수입니다.")
    private String desiredTime;

    @NotNull(message = "최대 참가자 수 입력은 필수입니다.")
    @Min(value = 1, message = "최소 참가자 수는 1명 이상이어야 합니다.")
    private Integer maxParticipants;

    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @NotBlank(message = "위치 입력은 필수입니다.")
    private String location;

    private String openChatUrl;

    @Builder
    public PostCreateDTO(String title, String desiredDate, String desiredTime, String category,
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
