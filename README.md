# RoomManagement
SpringBoot를 이용한 스터디룸 관리 프로젝트

## API 문서
[https://manageroom-dev.herokuapp.com/swagger-ui.html](https://manageroom-dev.herokuapp.com/swagger-ui.html)

## ERD
![ERD](https://github.com/ktj1997/RoomManagement/blob/master/manage/src/main/resources/static/erd.png)

## 인증방식
```
JWT토큰 - 유효기간 한달     
-H Authorization: Bearer {ACCESS_TOKEN}
```

## 에러코드
![ErrorCode](https://github.com/ktj1997/RoomManagement/blob/master/manage/src/main/resources/static/ExceptionCode.png)

## 구조도
![구조도](https://github.com/ktj1997/RoomManagement/blob/master/manage/src/main/resources/static/%EA%B5%AC%EC%A1%B0%EB%8F%84.png)

## 주요기능
1. 회원가입/로그인
2. 입실/퇴실 처리
3. 입실/퇴실 알람
5. 스케줄링(매일 상태 초기화/퇴실,부재 종료시간 처리)
6. 본인/스터디룸에 대한 참여정보
7. 입실/퇴실에 따른 알람

