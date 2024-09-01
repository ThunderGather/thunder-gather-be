package com.team.thundergather.domain.participant.dataAccess.repository;

import com.team.thundergather.domain.participant.dataAccess.entity.Participant;
import com.team.thundergather.domain.post.dataAccess.entity.Post;
import com.team.thundergather.domain.member.dataAccess.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByPostAndMember(Post post, Member member);
    List<Participant> findByMember(Member member);
}
