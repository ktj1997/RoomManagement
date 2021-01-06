package com.room.manage.room.model.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoomId implements Serializable {
    @Id
    @Column(length = 1)
    @EqualsAndHashCode.Include
    private String floor;

    @Id
    @Column(length = 1)
    @EqualsAndHashCode.Include
    private String field;
}
