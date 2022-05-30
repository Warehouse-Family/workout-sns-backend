package com.warehouse.workout.feed.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "TABLE_POST")
public class PostEntity {

    @Id
    @Column(name = "POST_ID")
    private Long id;


    @Column(name = "POST_CONTENT")
    private String content;

    @Column(name = "WRITTEN_AT")
    private LocalDateTime writtenAt;


}
