package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByusername(String username);
}
