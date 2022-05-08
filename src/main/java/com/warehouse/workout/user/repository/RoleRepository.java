package com.warehouse.workout.user.repository;

import com.warehouse.workout.user.entity.Role;
import com.warehouse.workout.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(UserRole name);
}
