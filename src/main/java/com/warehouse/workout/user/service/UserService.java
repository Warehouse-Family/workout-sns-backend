package com.warehouse.workout.user.service;

import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.constant.code.UserRoleCode;
import com.warehouse.workout.user.entity.UserRoleEntity;
import com.warehouse.workout.user.repository.UserRepository;
import com.warehouse.workout.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity saveUser(UserEntity user){

        userRoleRepository.save(new UserRoleEntity(user, UserRoleCode.USER));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

//    public RoleEntity saveRole(RoleEntity role){
//        return roleRepository.save(role);
//    }

//    public void addRoleToUser(String username, UserRole userRole){
//        UserEntity user = userRepository.findByusername(username);
//        RoleEntity role = roleRepository.findByRoleName(userRole);
//        //user.getRoles().add(role);
//        // TODO - UserRoleEntity에 맞게 변경
//
//    }

    public UserEntity getUser(String username){
        return userRepository.findByusername(username);
    }

//    public RoleEntity getRole(String roleName){
//        return roleRepository.findByRoleName(UserRole.valueOf(roleName));
//    }

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

}
