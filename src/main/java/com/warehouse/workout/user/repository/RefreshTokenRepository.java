package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

}
