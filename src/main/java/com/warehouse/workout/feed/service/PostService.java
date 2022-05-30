package com.warehouse.workout.feed.service;

import com.warehouse.workout.feed.dto.PostCreationRequestDto;
import com.warehouse.workout.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public void createPost(PostCreationRequestDto postCreationRequestDto){

        log.info(postCreationRequestDto.toString());
        //postRepository.save(postCreationRequestDto.toPostEntity());



    }

}
