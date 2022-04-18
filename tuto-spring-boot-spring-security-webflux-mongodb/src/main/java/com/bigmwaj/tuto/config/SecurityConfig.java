package com.bigmwaj.tuto.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;

import com.bigmwaj.tuto.constant.SecurityConstants;
import com.bigmwaj.tuto.filter.JWTTokenGeneratorFilter;
import com.bigmwaj.tuto.filter.JWTTokenValidatorFilter;

@EnableWebFluxSecurity()
public class SecurityConfig {

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("testuser")
				.password(passwordEncoder().encode("testpassword"))
				.roles("USER").build();
		return new MapReactiveUserDetailsService(user);
	}
	
	@Bean
	public CorsConfigurationSource corsConfig() {
		return new CorsConfigurationSource() {
			
			@Override
			public CorsConfiguration getCorsConfiguration(ServerWebExchange exchange) {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowCredentials(true);
				config.setAllowedHeaders(Collections.singletonList("*"));
				config.setAllowedMethods(Collections.singletonList("*"));
				config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
				config.setMaxAge(3600L);
				config.setExposedHeaders(Arrays.asList(SecurityConstants.JWT_HEADER, SecurityConstants.JWT_LIFE_HEADER));
				return config;
			}
		};
	}

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http
				.cors()
				.and().authorizeExchange()
				.anyExchange().authenticated()
				.and().httpBasic()
				.and().formLogin()
				.and().csrf().disable()
				.addFilterBefore(new JWTTokenValidatorFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
				.addFilterAfter(new JWTTokenGeneratorFilter(), SecurityWebFiltersOrder.HTTP_BASIC)
				.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}