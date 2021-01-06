package com.room.manage.patricipation.repository;

import com.room.manage.patricipation.model.entity.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
}
