package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.user.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity,Long> {
}
