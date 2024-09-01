package com.team.thundergather.domain.participant.dataAccess.entity;
import com.team.thundergather.config.db.BaseEntity;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private Member memeber;

    // FK
    @JoinColumn(name = "post_id")
    @ManyToOne(optional = false)
    private Post post;
}
