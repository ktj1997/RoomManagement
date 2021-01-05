package com.room.manage.patricipation.controller;

import com.sun.istack.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    /**
     * 입실
     * @param roomId Room식별자
     * @param participants 참여 인원(GroupRoom 일경우)
     */
    @GetMapping
    public void makeParticipation(@RequestParam Long roomId, @Nullable @RequestParam int participants)
    {

    }

    /**
     * 퇴실
     * @param roomId
     */
    @DeleteMapping
    public void cancelParticipation(@RequestParam Long roomId)
    {

    }
}
