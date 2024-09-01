package com.team.thundergather.domain.participant.dataAccess.repository;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import com.team.thundergather.domain.participant.dataAccess.entity.Participant;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByMember(Member member);

    Optional<Participant> findByMemberAndPost(Member member, Post post);

    boolean existsByMemberAndPost(Member member, Post post);
}
