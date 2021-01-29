package com.room.manage.api.patricipation.repository;

import com.room.manage.api.patricipation.model.entity.Participation;
import com.room.manage.api.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Optional<Participation> findByParticipant(User user);
    boolean existsByParticipant(User user);
    List<Participation> findAllByFinishTime(Date date);
}
