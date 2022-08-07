package com.warehouse.workout.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TABLE_USER_ROLE")
@Data
public class UserRoleEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ROLE_ID")
    private Long userRoleId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private RoleEntity role;

    @Enumerated(EnumType.STRING)
    private UserRole roleName;
    private String description;

}
