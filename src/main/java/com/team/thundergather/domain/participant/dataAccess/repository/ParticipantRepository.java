package com.team.thundergather.domain.participant.dataAccess.repository;

import com.team.thundergather.domain.participant.dataAccess.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
