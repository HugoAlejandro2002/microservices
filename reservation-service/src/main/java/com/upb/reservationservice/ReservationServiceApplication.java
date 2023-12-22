package com.upb.reservationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableFeignClients
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

	@EnableWebSecurity
	public class SecurityConfig {

		@Bean
		protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			return http.csrf().disable().authorizeRequests().anyRequest().permitAll().and().build();
		}
	}
}
