package com.warehouse.workout.feed.service;

import com.warehouse.workout.constant.PathConstant;
import com.warehouse.workout.feed.dto.PostCreationRequestDto;
import com.warehouse.workout.feed.dto.PostFindResponseDto;
import com.warehouse.workout.feed.entity.PostEntity;
import com.warehouse.workout.feed.entity.PostPictureEntity;
import com.warehouse.workout.feed.repository.PostPictureRepository;
import com.warehouse.workout.feed.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostPictureRepository postPictureRepository;

    @Transactional
    public void createPost(PostCreationRequestDto postCreationRequestDto) throws IOException {

        PostEntity savedPost = postRepository.save(postCreationRequestDto.toPostEntity());

        List<MultipartFile> files = postCreationRequestDto.getFiles();
        for (MultipartFile multipartFile : files) {
            String filePath = PathConstant.FEED_FILE_STORE_PATH + multipartFile.getOriginalFilename();
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);

            postPictureRepository.save(PostPictureEntity.builder()
                    .post(savedPost)
                    .picturePath(filePath)
                    .build());
        }

    }

    public PostFindResponseDto findPost(Long postId) {

        // 피드 기본정보
        Optional<PostEntity> postEntityOptional = postRepository.findById(postId);
        PostEntity postEntity = postEntityOptional.orElseThrow();

        // 파일
        Optional<List<PostPictureEntity>> pictureOptional = postPictureRepository.findByPostId(postEntity.getId());
        List<PostPictureEntity> pictures = pictureOptional.orElseThrow();

        return PostFindResponseDto.builder()
                .post(postEntity)
                .build();


    }

    public void findPostComments(Long postId){



    }


}
