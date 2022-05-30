package com.warehouse.workout.feed.repository;

import com.warehouse.workout.feed.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
}
