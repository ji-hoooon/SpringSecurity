package com.mino.security1.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AuditingEntity {

    @CreatedDate
    @Column(columnDefinition = "datetime")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(columnDefinition = "datetime")
    private LocalDateTime modifiedDate;
}
