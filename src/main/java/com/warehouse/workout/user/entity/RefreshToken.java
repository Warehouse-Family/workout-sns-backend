package com.warehouse.workout.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "REFRESH_TOKEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "issue_time")
    private LocalDateTime issueTime;

    @Column(name = "expired_time")
    private LocalDateTime expiredTime;

    @Column(name = "refresh_token")
    private String refreshToken;







}
