package com.warehouse.workout.feed.service;

import com.warehouse.workout.constant.PathConstant;
import com.warehouse.workout.feed.dto.PostCreationRequestDto;
import com.warehouse.workout.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public void createPost(PostCreationRequestDto postCreationRequestDto) throws IOException {

        List<MultipartFile> files = postCreationRequestDto.getFiles();
        for (MultipartFile multipartFile : files) {
            String filePath = PathConstant.FEED_FILE_STORE_PATH + multipartFile.getOriginalFilename();
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);
        }
    }

}
