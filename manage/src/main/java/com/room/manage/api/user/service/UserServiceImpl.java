package com.room.manage.api.user.service;

import com.room.manage.api.participation.exception.NoParticipationException;
import com.room.manage.api.participation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.participation.model.entity.Participation;
import com.room.manage.api.participation.repository.ParticipationRepository;
import com.room.manage.api.user.exception.UserNotExistException;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.repository.UserRepository;
import com.room.manage.core.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    /**
     * 내가 사용하고있는 Room이 있는지 정보확인
     * @return 참여장보
     */
    @Override
    @Transactional(readOnly = true)
    public ParticipationResponseDto findMyParticipation() {
        User user = userRepository.findById(SecurityUtil.getUserIdFromToken()).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoParticipationException::new);

        return new ParticipationResponseDto(participation);
    }

    @Override
    @Transactional
    public String renewalFcmToken(String token) {
        User user = userRepository.findById(SecurityUtil.getUserIdFromToken()).orElseThrow(UserNotExistException::new);
        user.setFcmToken(token);
        return user.getFcmToken();
    }
}
