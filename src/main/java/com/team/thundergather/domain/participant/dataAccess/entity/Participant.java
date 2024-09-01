package com.team.thundergather.domain.participant.dataAccess.entity;
import com.team.thundergather.global.config.db.BaseEntity;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Participant extends BaseEntity {

    // PK
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK
    @JoinColumn(name = "member_id")
    @ManyToOne(optional = false)
    private Member member;

    // FK
    @JoinColumn(name = "post_id")
    @ManyToOne(optional = false)
    private Post post;

    @Builder
    public Participant(Member member, Post post) {
        this.member = member;
        this.post = post;
    }

    // 정적 팩토리 메서드
    public static Participant create(Member member, Post post) {
        return Participant.builder()
                .member(member)
                .post(post)
                .build();
    }
}
