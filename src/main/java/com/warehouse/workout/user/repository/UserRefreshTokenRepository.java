package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.UserRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity,Long> {

}
