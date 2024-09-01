package com.team.thundergather.domain.member.dataAccess.entity;

import com.team.thundergather.config.db.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("이메일")
    private String email;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column(nullable = false)
    @Comment("닉네임")
    private String nickname;

    @Column(name = "profile_image")
    @Comment("프로필 사진")
    private String profileImage;
}
