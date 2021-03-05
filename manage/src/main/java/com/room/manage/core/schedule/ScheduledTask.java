package com.room.manage.core.schedule;

import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.participation.model.entity.Sleep;
import com.room.manage.api.participation.repository.ParticipationRepository;
import com.room.manage.api.participation.repository.SleepRepository;
import com.room.manage.api.participation.service.ParticipationService;
import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.room.repository.RoomRepository;
import com.room.manage.core.aop.annotation.DailyLog;
import com.room.manage.core.config.S3Config;
import com.room.manage.core.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduledTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final S3Config s3Config;
    private final ParticipationRepository participationRepository;
    private final ParticipationService participationService;
    private final RoomRepository roomRepository;
    private final SleepRepository sleepRepository;

    /**
     * 이용시간 만료 체크
     * 매일 오전 8시부터 밤 10시 30분 까지 10분 간격으로 체크
     */
    @Scheduled(cron = "0 0/10 8-22 * * *")
    public void checkUsingTimeExpire() {
        Date now = DateUtil.formatToDate(DateUtil.formatToString(new Date()));
        List<Participation> participates = participationRepository.findAllByFinishTime(now);

        participates.stream().forEach(it -> {
            participationService.exitRoom(it.getParticipant().getId());
            participationRepository.delete(it);
        });
    }

    /**
     * 부재시간 만료 체크
     * 매일 오전 8시부터 밤 10시 30분 까지 10분 간격으로 체크
     */
    @Scheduled(cron = "0 0/10 8-22 * * *")
    public void checkSleepTimeExpire() {
        Date now = DateUtil.formatToDate(DateUtil.formatToString(new Date()));
        List<Sleep> sleeps = sleepRepository.findAllBySleepFinishTime(now);
        sleeps.stream().forEach(it -> {
            participationService.toActiveStatus(it);
            sleepRepository.delete(it);
        });
    }

    /**
     * 매일 저녁 11시 모두 퇴실 및 초기 상태로 변경
     */
    @Scheduled(cron = "0 0 23 * * *")
    public void initAllRoom() {
        List<Room> rooms = roomRepository.findAll();
        List<Participation> participates = participationRepository.findAll();
        /**
         * 모두 퇴실
         */
        /**
         * Room의 모든 상태 초기화
         * Room의 모든 상태 초기화
         */
        participates.stream().forEach(it -> participationService.exitRoom(it.getParticipant().getId()));
        rooms.stream().forEach(it -> it.init());
    }

    /**
     * 12시 마다 전날의 LOG s3에 올리고 LOCAL에서 삭제
     * 1분은 딜레이가 있을수도 있어서
     */
    @DailyLog
    @Scheduled(cron = "0 53 0 * * *")
    public void storeLogToS3() throws IOException {
        File dir = new File(System.getProperty("user.dir") + "/logs");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String yesterday = DateUtil.parseYearAndDayFormatToString(cal.getTime());
        for (File file : dir.listFiles()) {
            if (file.exists()) {
                String fileName = file.getName();
                if (fileName.startsWith(yesterday)) {
                    s3Config.storeLogToS3(file);
                    boolean successful = file.delete();
                    logger.info(String.format("%s DELETE %s", fileName, successful));
                }
            }
        }
    }
}
