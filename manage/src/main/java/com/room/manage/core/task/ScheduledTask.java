package com.room.manage.core.task;

import com.room.manage.api.patricipation.model.entity.Participation;
import com.room.manage.api.patricipation.model.entity.Sleep;
import com.room.manage.api.patricipation.repository.ParticipationRepository;
import com.room.manage.api.patricipation.repository.SleepRepository;
import com.room.manage.api.patricipation.service.ParticipationService;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.repository.RoomRepository;
import com.room.manage.core.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledTask {

    private final ParticipationRepository participationRepository;
    private final ParticipationService participationService;
    private final RoomRepository roomRepository;
    private final SleepRepository sleepRepository;

    /**
     * 이용시간 만료 체크
     * 매일 오전 8시부터 밤 10시 30분 까지 30분 간격으로 체크
     */
    @Scheduled(cron = "0 0/10 8-22 * * *")
    public void checkUsingTimeExpire()
    {
        Date now = DateUtil.formatToDate(DateUtil.formatToString(new Date()));
        List<Participation> participates = participationRepository.findAllByFinishTime(now);

        participates.stream().forEach(it ->{
            participationService.exitRoom(it.getParticipant().getId());
            participationRepository.delete(it);
        });
    }

    /**
     * 부재시간 만료 체크
     * 매일 오전 8시부터 밤 10시 30분 까지 30분 간격으로 체크
     */
    @Scheduled(cron = "0 0/10 8-22 * * *")
    public void checkSleepTimeExpire(){
        Date now = DateUtil.formatToDate(DateUtil.formatToString(new Date()));
        List<Sleep> sleeps = sleepRepository.findAllBySleepFinishTime(now);
        sleeps.stream().forEach(it ->{
            participationService.toActiveStatus(it);
            sleepRepository.delete(it);
        });
    }


    /**
     * 매일 저녁 11시 모두 퇴실 및 초기 상태로 변경
     */
    @Scheduled(cron ="0 0 23 * * *" )
    public void initAllRoom()
    {
        List<Room> rooms = roomRepository.findAll();
        List<Participation> participates = participationRepository.findAll();
        /**
         * 모두 퇴실
         */
        /**
         * Room의 모든 상태 초기화
         */
        participates.stream().forEach(it -> participationService.exitRoom(it.getParticipant().getId()));
        rooms.stream().forEach(it -> it.init());
    }

}
