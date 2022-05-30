package com.warehouse.workout.feed.repository;

import com.warehouse.workout.feed.entity.PostCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostCommentEntity,Long> {
}
