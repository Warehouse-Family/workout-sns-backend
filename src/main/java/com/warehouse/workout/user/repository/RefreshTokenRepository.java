package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.RefreshToken;
import com.warehouse.workout.user.entity.Role;
import com.warehouse.workout.user.entity.User;
import com.warehouse.workout.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

}
