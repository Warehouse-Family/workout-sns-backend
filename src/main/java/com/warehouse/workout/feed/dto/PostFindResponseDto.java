package com.warehouse.workout.feed.dto;

import com.warehouse.workout.feed.entity.PostEntity;
import com.warehouse.workout.feed.entity.PostPictureEntity;
import com.warehouse.workout.user.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostFindResponseDto {

    private String writer;
    private String content;
    private List<MultipartFile> files;
    private LocalDateTime writtenAt;

    @Builder
    public PostFindResponseDto(PostEntity post) {

        this.writer = post.getUser().getName();
        this.content = post.getContent();
        this.writtenAt = post.getWrittenAt();



    }
}
