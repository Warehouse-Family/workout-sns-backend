package com.warehouse.workout.feed.dto;

import com.warehouse.workout.feed.entity.PostEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostCreationRequestDto {

    private String writer;
    private String content;
    private List<MultipartFile> files;

    public PostEntity toPostEntity(){
        return PostEntity.builder()
                .id(null)
                .content(this.content)
                .writtenAt(LocalDateTime.now())
                .build();
    }

}
