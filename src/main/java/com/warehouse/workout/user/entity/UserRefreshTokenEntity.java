package com.warehouse.workout.user.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TABLE_USER_REFRESH_TOKEN")
@Getter
@NoArgsConstructor
public class UserRefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "issue_time")
    private LocalDateTime issueTime;

    @Column(name = "expired_time")
    private LocalDateTime expiredTime;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public UserRefreshTokenEntity(UserEntity user, LocalDateTime issueTime, LocalDateTime expiredTime, String refreshToken) {
        this.user = user;
        this.issueTime = issueTime;
        this.expiredTime = expiredTime;
        this.refreshToken = refreshToken;
    }

    public void updateExpiredTime(LocalDateTime expiredTime){
        this.expiredTime = expiredTime;
    }

}
