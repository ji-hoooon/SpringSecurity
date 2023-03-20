package com.mino.security1.model;



import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
//생성자를 직접 작성했을 경우에는 기본생성자를 작성해주는게 좋다
@NoArgsConstructor
//@Getter + @Setter + @ToString + @EqualsAndHashCode
//public class User {
public class User extends AuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    private String provider;
    private String providerId;


//    @CreationTimestamp
//    private Timestamp createTime;
//
//    @UpdateTimestamp
//    private Timestamp updateTime;

    @Builder    //소셜로그인을 위한 빌더패턴
    public User(String username, String password, String email, String role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    //기본 생성자 작성 필수!!
//    public User() {
//
//    }
}
