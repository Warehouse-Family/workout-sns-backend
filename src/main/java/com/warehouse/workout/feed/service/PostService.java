package com.warehouse.workout.feed.service;

import com.warehouse.workout.feed.dto.PostCreationRequestDto;
import com.warehouse.workout.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public void createPost(PostCreationRequestDto postCreationRequestDto){

    }

}
