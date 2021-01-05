package com.room.manage.user.model.entity;

import com.room.manage.patricipation.model.entity.Participation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor
public class User {

    /**
     * 식별자
     */
    @Id @GeneratedValue
    Long id;
    /**
     * 유저 ID
     */
    String userName;
    /**
     * 유저 비밀번호
     */
    String password;
    /**
     * 유저 이름
     */
    String name;

    @ManyToOne
    Participation participation;
}
