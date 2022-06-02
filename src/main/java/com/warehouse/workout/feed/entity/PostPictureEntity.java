package com.warehouse.workout.feed.entity;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "TABLE_POST_PICTURE")
public class PostPictureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private PostEntity post;

    private String picturePath;

    @Builder
    public PostPictureEntity(PostEntity post, String picturePath) {
        this.post = post;
        this.picturePath = picturePath;
    }
}
