package com.warehouse.workout.feed.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
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

    @Builder
    public PostEntity(Long id, String content, LocalDateTime writtenAt) {
        this.id = id;
        this.content = content;
        this.writtenAt = writtenAt;
    }
}
