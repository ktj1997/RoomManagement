package com.room.manage.api.user.service;

import com.room.manage.api.patricipation.exception.NoSuchParticipationException;
import com.room.manage.api.patricipation.model.dto.response.ParticipationResponseDto;
import com.room.manage.api.patricipation.model.entity.Participation;
import com.room.manage.api.patricipation.repository.ParticipationRepository;
import com.room.manage.api.user.exception.UserNotExistException;
import com.room.manage.api.user.model.entity.User;
import com.room.manage.api.user.repository.UserRepository;
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
     * @param userId User식별자
     * @return 참여장보
     */
    @Override
    @Transactional
    public ParticipationResponseDto findMyParticipation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = participationRepository.findByParticipant(user).orElseThrow(NoSuchParticipationException::new);

        return new ParticipationResponseDto(participation);
    }
}