package com.room.manage.api.patricipation.service.function;

import com.room.manage.api.room.model.entity.Room;
import com.room.manage.api.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SendWebSocketAlarm implements SendAlarm{

    @Value("${application.socket.url}")
    String socketURL;

    private final RestTemplate restTemplate;

    @Override
    public void send(User user, Room room) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> params = new LinkedHashMap<>();

        params.put("userId",user.getId().toString());
        params.put("userName",user.getName());
        params.put("floor",room.getFloor());
        params.put("field",room.getField());

        restTemplate.exchange(socketURL, HttpMethod.DELETE,new HttpEntity(params,headers),boolean.class);
    }
}
