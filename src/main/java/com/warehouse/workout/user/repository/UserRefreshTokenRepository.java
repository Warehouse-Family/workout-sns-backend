package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.user.entity.UserRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity,Long> {

    UserRefreshTokenEntity findUserRefreshTokenEntityByUser(UserEntity userEntity);

}
