package com.warehouse.workout.feed.entity;

import com.warehouse.workout.user.entity.UserEntity;

import javax.persistence.*;

@Entity
public class PostCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "POST_COMMENT_ID")
    private Long id;

    @Column(name = "POST_COMMENT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;


}
