package com.warehouse.workout.feed.entity;

import javax.persistence.*;

@Entity
public class PostPictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private PostEntity post;

    private String picturePath;

}
