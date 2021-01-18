package com.room.manage.notice;

import com.room.manage.room.model.entity.RoomId;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class NoticeService {
    private final ConcurrentHashMap<RoomId,Notice> eventMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void initConstruct()
    {
        String[] floors = {"3","4","5"};
        String[] fields = {"A","B","C"};

        for(String floor : floors){
            for(String field : fields){
                RoomId roomId = new RoomId(floor,field);
                eventMap.put(roomId,new Notice());
            }
        }
    }

    public void noticeOthers(String floor,String field,String userName)
    {
        RoomId roomId = new RoomId(floor,field);
        Notice notice = eventMap.get(roomId);
        for(Map.Entry<Long,SseEmitter> entry : notice.getUserMap().entrySet()){
            try{
                SseEmitter emitter = entry.getValue();
                emitter.send(NoticeMessage.noticeMessage(userName));
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }


    public SseEmitter addEmitter(String floor,String field,Long userId)
    {
        RoomId roomId = new RoomId(floor,field);
        Notice notice = eventMap.get(roomId);
        ConcurrentHashMap userMap = notice.getUserMap();
        SseEmitter emitter = new SseEmitter(-1L);
        userMap.put(userId,emitter);

        return emitter;
    }
    public void deleteEmitter(String floor,String field,Long userId)
    {
        RoomId roomId = new RoomId(floor,field);
        Notice notice = eventMap.get(roomId);
        ConcurrentHashMap userMap = notice.getUserMap();
        SseEmitter emitter = (SseEmitter)userMap.remove(userId);

        emitter.complete();
    }
    public void init()
    {
        for(Map.Entry<RoomId,Notice> entry : eventMap.entrySet()){
            for(Map.Entry<Long,SseEmitter> emitter : entry.getValue().getUserMap().entrySet()){
                emitter.getValue().complete();
            }
            entry.getValue().getUserMap().clear();
        }
    }


}
