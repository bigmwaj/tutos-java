package com.bigmwaj.tuto.filter;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.HttpBasicServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.bigmwaj.tuto.constant.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

public class JWTTokenValidatorFilter implements WebFilter{

	// Begin - Components coming from AuthenticationWebFilter
	private ServerAuthenticationSuccessHandler authenticationSuccessHandler = new WebFilterChainServerAuthenticationSuccessHandler();
	private ServerAuthenticationFailureHandler authenticationFailureHandler = new ServerAuthenticationEntryPointFailureHandler(
			new HttpBasicServerAuthenticationEntryPoint());
	private ServerSecurityContextRepository securityContextRepository = NoOpServerSecurityContextRepository
			.getInstance();
	private ServerWebExchangeMatcher requiresAuthenticationMatcher = ServerWebExchangeMatchers.anyExchange();
	// End - Components coming from AuthenticationWebFilter

	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	private Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange exchange) {
		SecurityContextImpl securityContext = new SecurityContextImpl();
		securityContext.setAuthentication(authentication);
		return this.securityContextRepository.save(exchange.getExchange(), securityContext)
				.then(this.authenticationSuccessHandler.onAuthenticationSuccess(exchange, authentication))
				.contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
	}
	
	private Mono<Authentication> validateJWTToken(ServerWebExchange exchange){
		try {
			String jwt = exchange.getRequest().getHeaders().getFirst(SecurityConstants.JWT_HEADER);
			if( jwt == null ) {
				return Mono.defer(() -> Mono.error(new IllegalStateException("No provider found for")));
			}
			if( jwt.startsWith("Bearer ") ) {
				jwt = jwt.substring("Bearer ".length());
			}
			
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			
			Claims claims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwt)
					.getBody();
			String username = String.valueOf(claims.get("username"));
			String authorities = (String) claims.get("authorities");
			Authentication auth = new UsernamePasswordAuthenticationToken(
					username, null,
					AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
			
			return Mono.just(auth);
		} catch (Exception e) {
			getLogger().error(e.getLocalizedMessage(), e);
			return Mono.defer(() -> Mono.error(new Exception("Impossible de valider la connexion")));
		}
	}
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		return this.requiresAuthenticationMatcher.matches(exchange).filter((matchResult) -> matchResult.isMatch())
				.filter(e -> !this.shouldNotFilter(exchange.getRequest()))
				.flatMap((matchResult) -> this.validateJWTToken(exchange))
				.switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
				.flatMap((token) -> onAuthenticationSuccess(token, new WebFilterExchange(exchange, chain)))
				.onErrorResume(AuthenticationException.class, (ex) -> this.authenticationFailureHandler
						.onAuthenticationFailure(new WebFilterExchange(exchange, chain), ex));
	}
	
	private boolean shouldNotFilter(ServerHttpRequest request) {
		return request.getPath().value().equals(SecurityConstants.VALIDATE_CREDENTIALS_URL);
	}
}
