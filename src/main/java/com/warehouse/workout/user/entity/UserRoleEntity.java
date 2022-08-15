package com.warehouse.workout.user.entity;

import com.warehouse.workout.constant.code.UserRoleCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;


import javax.persistence.*;

@Entity(name = "TABLE_USER_ROLE")
@NoArgsConstructor
@Getter
public class UserRoleEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ROLE_ID")
    private Long userRoleId;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @Column(name = "ROLE_CODE")
    @Enumerated(EnumType.STRING)
    private UserRoleCode userRoleCode;

    public UserRoleEntity(UserEntity user,UserRoleCode userRoleCode) {
        this.user = user;
        this.userRoleCode = userRoleCode;
    }



}
