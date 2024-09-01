package com.team.thundergather.domain.post.dataAccess.entity;

import com.team.thundergather.global.config.db.BaseEntity;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("카테고리")
    private String category;

    @Column(nullable = false)
    @Comment("제목")
    private String title;

    @Column(nullable = false)
    @Comment("날짜")
    private String dateTime;

    @Column(nullable = false)
    @Comment("참여 최대 인원")
    private int maxParticipants;

    @Column(nullable = false)
    @Comment("위치")
    private String location;

    @Comment("오픈 챗팅방 url")
    private String openChatUrl;

    @JoinColumn(name = "member_id")
    @ManyToOne(optional = false)
    private Member member;
}
