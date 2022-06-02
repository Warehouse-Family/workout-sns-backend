package com.warehouse.workout.feed.dto;

import com.warehouse.workout.feed.entity.PostEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostFindResponseDto {

    private String writer;
    private String content;
    private List<MultipartFile> files;



}
