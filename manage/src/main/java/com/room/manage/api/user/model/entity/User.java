package com.room.manage.api.user.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User{

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
    @Column(nullable = false)
    String password;
    /**
     * 유저 이름
     */
    @Column(length = 10,nullable = false)
    String name;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Builder
    public User(String userName, String password, String name,UserRole userRole)
    {
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
        this.name = name;
    }
}
