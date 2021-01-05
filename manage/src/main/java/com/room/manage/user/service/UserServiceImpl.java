package com.room.manage.user.service;

import com.room.manage.patricipation.exception.NoSuchParticipationException;
import com.room.manage.patricipation.model.dto.ParticipationInfoDto;
import com.room.manage.patricipation.model.entity.Participation;
import com.room.manage.user.exception.UserNotExistException;
import com.room.manage.user.model.entity.User;
import com.room.manage.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    /**
     * 내가 사용하고있는 Room이 있는지 정보확인
     * @param userId User식별자
     * @return 참여장보
     */
    @Override
    @Transactional
    public ParticipationInfoDto findMyParticipation(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Participation participation = user.getParticipation();

        if(participation!= null)
            return new ParticipationInfoDto(participation);
        else
            throw new NoSuchParticipationException();
    }
}
