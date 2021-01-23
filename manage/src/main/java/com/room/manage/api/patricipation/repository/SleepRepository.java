package com.room.manage.api.patricipation.repository;

import com.room.manage.api.patricipation.model.entity.Sleep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SleepRepository extends JpaRepository<Sleep,Long> {
}
