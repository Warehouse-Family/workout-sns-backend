package com.warehouse.workout.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String PhoneNumber;
    @Column(name = "email")
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

//    @OneToOne(mappedBy = "user")
//    private RefreshToken refreshTokens;

}
