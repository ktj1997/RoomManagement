package com.room.manage.room.service;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.repository.ParticipationRepository;
import com.room.manage.room.exception.RoomNotExistException;
import com.room.manage.room.model.dto.RoomInfoDto;
import com.room.manage.room.model.entity.Room;
import com.room.manage.room.repository.RoomRepository;
import com.room.manage.user.model.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final ParticipationRepository participationRepository;


    /**
     * 해당 Room의 정보
     * @param roomId Room식별자
     * @return RoomInfoDto
     */
}
