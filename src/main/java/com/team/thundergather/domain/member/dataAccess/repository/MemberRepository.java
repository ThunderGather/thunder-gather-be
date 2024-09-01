package com.team.thundergather.domain.member.dataAccess.repository;

import com.team.thundergather.domain.member.dataAccess.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
