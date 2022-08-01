package com.warehouse.workout;

import com.warehouse.workout.user.entity.Role;
import com.warehouse.workout.user.entity.UserEntity;
import com.warehouse.workout.user.entity.UserRole;
import com.warehouse.workout.user.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class WorkoutApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkoutApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	CommandLineRunner run(UserService userService){
//		return args -> {
//			userService.saveRole(new Role(null, UserRole.USER,UserRole.USER.getDescription()));
//			userService.saveRole(new Role(null, UserRole.ADMIN,UserRole.ADMIN.getDescription()));
//
//			userService.saveUser(new UserEntity(null,"soojong", "hsz3855","123",
//					"010-0000-0000" ,"hsz3855@naver.com",new ArrayList<>()));
//			userService.saveUser(new UserEntity(null,"sunny", "sunny","123",
//					"010-0000-0000" ,"hsz3855@naver.com",new ArrayList<>()));
//
//			userService.addRoleToUser("hsz3855" , UserRole.USER);
//			userService.addRoleToUser("sunny" , UserRole.ADMIN);
//
//
//		};
//	}


}