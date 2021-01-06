package com.room.manage.patricipation.controller;

import com.room.manage.patricipation.model.dto.ParticipationRequestDto;
import com.room.manage.patricipation.repository.ParticipationRepository;
import com.room.manage.patricipation.service.ParticipationService;
import com.room.manage.room.model.entity.RoomType;
import com.sun.istack.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participation")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;

    /**
     * 방 참여
     * @param participationRequestDto 참여에 필요한 정보
     */
    @PostMapping
    public void makeParticipation(@RequestBody ParticipationRequestDto participationRequestDto)
    {
        participationService.joinRoom(participationRequestDto);
    }
}
