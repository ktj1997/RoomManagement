package com.room.manage.user.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class User {

    @Id @GeneratedValue
    Long id;

    String userName;

    String password;

    String name;
}
