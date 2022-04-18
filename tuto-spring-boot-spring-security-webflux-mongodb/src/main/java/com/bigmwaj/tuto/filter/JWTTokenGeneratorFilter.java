package com.bigmwaj.tuto.filter;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.bigmwaj.tuto.constant.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

public class JWTTokenGeneratorFilter implements WebFilter{

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	private void generateAndAddJWTToken(ServerWebExchange exchange, Authentication auth) {
		if( auth != null ) {
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			Date start = new Date();
			String jwt = Jwts.builder()
					.setIssuer("ProfarmSoft")
					.setSubject("JWT Token")
					.claim("username", auth.getName())
					.claim("authorities", populateAuthorities(auth.getAuthorities()))
					.setIssuedAt(start)
					.setExpiration(new Date(start.getTime() + SecurityConstants.SESSION_TIMEOUT))
					.signWith(key).compact();
			exchange.getResponse().getHeaders().add(SecurityConstants.JWT_HEADER, jwt);
			exchange.getResponse().getHeaders().add(SecurityConstants.JWT_LIFE_HEADER, SecurityConstants.SESSION_TIMEOUT + "");
		}
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		if (!this.shouldNotFilter(exchange.getRequest())) {
			new ServerHttpBasicAuthenticationConverter()
			.convert(exchange)
			.doOnSuccess(auth -> this.generateAndAddJWTToken(exchange, auth))
			.subscribe();
		}
		return chain.filter(exchange);
	}
	
	private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
        	authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
	}

	protected boolean shouldNotFilter(ServerHttpRequest request) {
		return !request.getPath().value().equals(SecurityConstants.VALIDATE_CREDENTIALS_URL);
	}
}
