package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.RoleEntity;
import com.warehouse.workout.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByRoleName(UserRole name);
}
