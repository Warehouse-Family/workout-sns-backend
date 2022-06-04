package com.warehouse.workout.feed.entity;

import com.warehouse.workout.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "TABLE_POST")
public class PostEntity {

    @Id
    @Column(name = "POST_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "POST_CONTENT")
    private String content;

    @Column(name = "WRITTEN_AT")
    private LocalDateTime writtenAt;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @Builder
    public PostEntity(Long id, String content, LocalDateTime writtenAt,UserEntity user) {
        this.id = id;
        this.content = content;
        this.writtenAt = writtenAt;
        this.user = user;
    }
}
