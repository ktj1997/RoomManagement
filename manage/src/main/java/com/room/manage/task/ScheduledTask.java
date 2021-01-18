package com.room.manage.task;

import com.room.manage.notice.NoticeService;
import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.patricipation.repository.ParticipationRepository;
import com.room.manage.room.model.entity.Room;
import com.room.manage.room.model.entity.RoomType;
import com.room.manage.room.repository.RoomRepository;
import com.room.manage.util.DateUtil;
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
    private final RoomRepository roomRepository;
    private final NoticeService noticeService;

    /**
     * 매일 오전 8시부터 밤 10시 30분 까지 30분 간격으로 체크
     */
    @Scheduled(cron = "0 0/10 8-22 * * *")
    public void checkExpire()
    {
        List<Participation> participates = participationRepository.findAll();

        participates.stream().forEach(it ->{
            if(DateUtil.formatToString(it.getFinishTime()).equals(DateUtil.formatToString(new Date())))
            {
                if(it.getRoom().getType().equals(RoomType.GROUP))
                    it.getRoom().init();
                else
                    it.getRoom().exit();
                participationRepository.delete(it);
                noticeService.deleteEmitter(it.getRoom().getFloor(),it.getRoom().getField(),it.getParticipant().getId());
            }
        });
    }

    /**
     * 매일 저녁 11시 모두 퇴실 및 초기 상태로 변경
     */
    @Scheduled(cron ="0 0 23 * * *" )
    public void initAllRoom()
    {
        List<Room> rooms = roomRepository.findAll();
        /**
         * 모두 퇴실
         */
        participationRepository.deleteAll();
        /**
         * Room의 모든 상태 초기화
         */
        rooms.stream().forEach(it -> it.init());
        noticeService.init();
    }

}
