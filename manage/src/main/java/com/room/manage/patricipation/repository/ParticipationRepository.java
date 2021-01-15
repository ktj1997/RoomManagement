package com.room.manage.patricipation.repository;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    Optional<Participation> findByParticipant(User user);
    List<Participation> findAllByRoom_FloorAndRoom_Floor(String floor,String field);
    boolean existsByParticipant(User user);
}
