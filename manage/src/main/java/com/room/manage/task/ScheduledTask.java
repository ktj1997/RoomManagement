package com.room.manage.task;

import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.repository.ParticipationRepository;
import com.room.manage.room.model.entity.Room;
import com.room.manage.room.repository.RoomRepository;
import com.room.manage.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledTask {

    private final ParticipationRepository participationRepository;
    private final RoomRepository roomRepository;

    /**
     * 매일 오전 8시부터 밤 10시 30분 까지 30분 간격으로 체크
     */
    @Scheduled(cron = "0 0/30 8-22 * * *")
    public void checkExpire()
    {
        List<Participation> participates = participationRepository.findAll();

        participates.stream().forEach(it ->{
            if(DateUtil.formatToString(it.getFinishTime()).equals(DateUtil.formatToString(new Date())))
                participationRepository.delete(it);
        });
    }

    /**
     * 매일 저녁 11시 모두 퇴실 및 초기 상태로 변경
     */
    @Scheduled(cron ="0 0 23 * * *" )
    public void initAllRoom()
    {
        List<Participation> participates = participationRepository.findAll();
        List<Room> rooms = roomRepository.findAll();
        /**
         * 모두 퇴실
         */
        participates.stream().forEach(it -> participationRepository.delete(it));
        /**
         * Room의 모든 상태 초기화
         */
        rooms.stream().forEach(it -> it.init());
    }

}
