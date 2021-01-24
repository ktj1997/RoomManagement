package com.room.manage.core.exception;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    /**
     * Auth Exception
     */
    DUPLICATE_USERNAME("AUTH-001","중복된 아이디입니다."),
    WRONG_LOGIN_INFO("AUTH-002","가입하지 않은 아이디이거나, 잘못된 비밀번호 입니다."),
    INVALID_TOKEN("AUTH-003","유효하지 않은 토큰입니다"),
    MALFORMED_TOKEN("AUTH-004","손상된 토큰입니다."),
    EXPIRE_TOKEN("AUTH-005","만료된 토큰입니다."),
    NOT_BEARER_FORMAT("AUTH-006","Bearer가 포함되어있지 않습니다."),

    /**
     * Participation Exception
     */
    INVALID_TIME_REQUEST("PARTICIPATION-001","유효하지 않은 시간요청입니다."),
    NO_PARTICIPATION("PARTICIPATION-002","참여 정보가 존재하지 않습니다."),
    ALREADY_PARTICIPATION("PARTICIPATION-003","이미 참여중입니다."),
    SLEEP_NUM_ZERO("PARTICIPATION-004","남은 부재신청 횟수가 없습니다."),
    ALREADY_SLEEP("PARTICIPATION-005","이미 부재 상태입니다."),
    MAXIMUM_PARTICIPANT("PARTICIPATION-006","이미 최대인원이 참여중입니다"),
    CONNECTION_CLOSED("PARTICIPATION-007","소켓서버와 연결불량으로 세션 종료 작업이 완료되지 안았습니다."),

    /**
     * Room Exception
     */

    ROOM_NOT_EXIST("ROOM-001","존재하지 않는 룸 정보입니다."),
    /**
     * User Exception
     */

    USER_NOT_EXIST("USER=001","존재하지 않는 사용자 입니다.");


    private final String code;
    private final String message;

    ExceptionCode(final String code, final String message){
        this.code = code;
        this.message = message;
    }
}
