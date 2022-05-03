package com.warehouse.workout;

import com.warehouse.workout.user.entity.Role;
import com.warehouse.workout.user.entity.User;
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

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null,"soojong", "hsz3855","123", new ArrayList<>()));
			userService.saveUser(new User(null,"hyojong", "hhz3855","123", new ArrayList<>()));
			userService.saveUser(new User(null,"sunny", "sunny","123", new ArrayList<>()));

			userService.addRoleToUser("hsz3855" , "ROLE_USER");
			userService.addRoleToUser("hsz3855" , "ROLE_MANAGER");
			userService.addRoleToUser("hhz3855" , "ROLE_ADMIN");
			userService.addRoleToUser("sunny" , "ROLE_SUPER_ADMIN");


		};
	}


}
