package com.app.api.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost"));
		configuration.addAllowedHeader("*");
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH","PUT" ,"DELETE"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		// .httpBasic().disable()
        // .formLogin().disable()
        // .csrf().disable()
        // .cors().and()
        // .authorizeHttpRequests()
		.cors(cors -> corsConfigurationSource())
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(authorizeRequests ->
			authorizeRequests
				.anyRequest()
				.permitAll()
		);

		return http.build();
	}
}