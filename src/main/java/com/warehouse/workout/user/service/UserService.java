package com.warehouse.workout.user.service;

import com.warehouse.workout.user.entity.RoleEntity;
import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.user.entity.UserRole;
import com.warehouse.workout.user.repository.RoleRepository;
import com.warehouse.workout.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserEntity saveUser(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public RoleEntity saveRole(RoleEntity role){
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, UserRole userRole){
        UserEntity user = userRepository.findByusername(username);
        RoleEntity role = roleRepository.findByRoleName(userRole);
        user.getRoles().add(role);
    }

    public UserEntity getUser(String username){
        return userRepository.findByusername(username);
    }

    public RoleEntity getRole(String roleName){
        return roleRepository.findByRoleName(UserRole.valueOf(roleName));
    }

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByusername(username);
        if(user == null){
            log.error("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        } else{
            log.info("User found in the database : {}",user.getUsername());
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName().toString())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
    }
}
