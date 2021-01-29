package com.room.manage.api.patricipation.repository;

import com.room.manage.api.patricipation.model.entity.Sleep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SleepRepository extends JpaRepository<Sleep,Long> {
    List<Sleep> findAllBySleepFinishTime(Date date);
}
