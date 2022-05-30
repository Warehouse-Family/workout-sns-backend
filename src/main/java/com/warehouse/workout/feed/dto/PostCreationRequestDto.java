package com.warehouse.workout.feed.dto;

import com.warehouse.workout.feed.entity.PostEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class PostCreationRequestDto {

    private String writer;
    private String content;
    // 위치

    public PostEntity toPostEntity(){
        return PostEntity.builder()
                .id(null)
                .content(this.content)
                .writtenAt(LocalDateTime.now())
                .build();
    }

}
