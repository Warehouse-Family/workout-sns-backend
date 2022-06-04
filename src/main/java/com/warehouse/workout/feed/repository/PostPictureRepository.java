package com.warehouse.workout.feed.repository;

import com.warehouse.workout.feed.entity.PostPictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostPictureRepository extends JpaRepository<PostPictureEntity,Long> {

    Optional<List<PostPictureEntity>> findByPostId(Long postId);

}
