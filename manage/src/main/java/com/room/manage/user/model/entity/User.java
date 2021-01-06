package com.room.manage.user.model.entity;

import com.room.manage.patricipation.model.entity.Participation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 식별자
     */
    @Id @GeneratedValue
    Long id;
    /**
     * 유저 ID
     */
    @Column(length = 12,nullable = false)
    String userName;
    /**
     * 유저 비밀번호
     */
    @Column(length = 20,nullable = false)
    String password;
    /**
     * 유저 이름
     */
    @Column(length = 10,nullable = false)
    String name;

    @ManyToOne
    Participation participation;

    @Builder
    public User(String userName, String password, String name)
    {
        this.userName = userName;
        this.password = password;
        this.name = name;
    }
}
