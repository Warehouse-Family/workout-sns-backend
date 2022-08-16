package com.warehouse.workout.user.service;

import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UserEntity user = userRepository.findByusername(username);

        if(user == null){
            log.error("User Not Found");
            throw new UsernameNotFoundException("User Not Found");
        } else{
            log.info("User found in the database : {}",user.getUsername());
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getUserRoleCode().toString())));
        return new User(user.getUsername(),user.getPassword(),authorities);
    }
}
