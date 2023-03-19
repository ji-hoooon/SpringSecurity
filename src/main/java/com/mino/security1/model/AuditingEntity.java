package com.mino.security1.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditingEntity {

    @CreatedDate
    private Timestamp createDate;

    @LastModifiedDate
    private Timestamp modifiedDate;
}
