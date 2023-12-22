package com.upb.authservice;

import com.upb.authservice.entity.ERole;
import com.upb.authservice.entity.User;
import com.upb.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner {


	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	public void run(String... args){
		User adminAccount = userRepository.findByRole(ERole.ROLE_ADMIN);

		if(null == adminAccount){
			User user = new User();

			user.setEmail("admin@gmail.com");
			user.setUsername("admin");
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			user.setRole(ERole.ROLE_ADMIN);

			userRepository.save(user);

		}
	}

}
