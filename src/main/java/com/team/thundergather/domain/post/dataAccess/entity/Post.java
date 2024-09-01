package com.team.thundergather.domain.post.dataAccess.entity;
import com.team.thundergather.config.db.BaseEntity;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.participant.dataAccess.entity.Participant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.team.thundergather.domain.post.applicaion.dto.PostCreateDTO;
import com.team.thundergather.domain.post.applicaion.dto.PostUpdateDTO;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    @Comment("제목")
    private String title;

    @Column(nullable = false)
    @Comment("날짜")
    private String desiredDate;

    @Column(nullable = false)
    @Comment("시간")
    private String desiredTime;

    @Column(nullable = false)
    @Comment("카테고리")
    private String category;

    @Column(nullable = false)
    @Comment("참여 최대 인원")
    private int maxParticipants;

    @Column(nullable = false)
    @Comment("설명")
    private String description;

    @Column(nullable = false)
    @Comment("위치")
    private String location;

    @Comment("오픈 채팅방 URL")
    private String openChatUrl;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants = new ArrayList<>();

    @Builder
    public Post(Member member, String category, String title, String desiredDate, String desiredTime, int maxParticipants,
                String description, String location, String openChatUrl) {
        this.member = member;
        this.title = title;
        this.desiredDate = desiredDate;
        this.desiredTime = desiredTime;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.description = description;
        this.location = location;
        this.openChatUrl = openChatUrl;
    }

    public static Post of(PostCreateDTO dto, Member member) {
        return Post.builder()
                .member(member)
                .category(dto.getCategory())
                .title(dto.getTitle())
                .desiredDate(dto.getDesiredDate())
                .desiredTime(dto.getDesiredTime())
                .maxParticipants(dto.getMaxParticipants())
                .description(dto.getDescription())
                .location(dto.getLocation())
                .openChatUrl(dto.getOpenChatUrl())
                .build();
    }

    public void update(PostUpdateDTO dto) {
        this.category = dto.getCategory();
        this.title = dto.getTitle();
        this.desiredDate = dto.getDesiredDate();
        this.desiredTime = dto.getDesiredTime();
        this.maxParticipants = dto.getMaxParticipants();
        this.description = dto.getDescription();
        this.location = dto.getLocation();
        this.openChatUrl = dto.getOpenChatUrl();
    }
}
