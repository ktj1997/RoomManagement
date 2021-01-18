package com.room.manage.room.repository;

import com.room.manage.room.model.entity.Room;
import com.room.manage.room.model.entity.RoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, RoomId> {
    Optional<Room> findByFloorAndField(String floor, String field);
}
