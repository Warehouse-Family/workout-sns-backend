package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByusername(String username);
}
