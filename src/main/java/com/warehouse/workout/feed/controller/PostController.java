package com.warehouse.workout.feed.controller;

import com.warehouse.workout.feed.dto.PostCreationRequestDto;
import com.warehouse.workout.feed.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    final PostService postService;

    // 게시글 쓰기
    @PostMapping("/api/v1/post")
    public void createPost(@RequestBody PostCreationRequestDto postCreationRequestDto){
        postService.createPost(postCreationRequestDto);
    }


}
