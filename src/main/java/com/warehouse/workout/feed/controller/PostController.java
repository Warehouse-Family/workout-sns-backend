package com.warehouse.workout.feed.controller;

import com.warehouse.workout.feed.dto.PostCreationRequestDto;
import com.warehouse.workout.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    final PostService postService;

    // 게시글 쓰기
    @PostMapping(value = "/api/v1/post")
    public void createPost(PostCreationRequestDto post ){


        postService.createPost(post);
    }


}
