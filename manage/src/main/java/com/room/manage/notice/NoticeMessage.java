package com.room.manage.notice;

public class NoticeMessage {
    public static String noticeMessage(String userName)
    {
        return String.format("%s 님이 참여하였습니다. 자리를 정돈해주세요. \n",userName);
    }
}
