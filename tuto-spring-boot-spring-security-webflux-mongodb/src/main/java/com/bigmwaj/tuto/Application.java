package com.bigmwaj.tuto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigmwaj.tuto.constant.SecurityConstants;

@RestController
@EnableReactiveMongoRepositories(basePackages = "com.bigmwaj.tuto.dao")
@SpringBootApplication()
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Valide les paramètres d'authentification ajoutés 
	 * à l'en tête de la requête http par basic auth
	 * @return la liste des roles de l'utilisateur
	 */
	@GetMapping(SecurityConstants.VALIDATE_CREDENTIALS_URL)
	public ResponseEntity<Object> validateCredentials(Authentication auth){
		UserDetails userDetails = ((UserDetails)auth.getPrincipal());
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		ModelMap model = new ModelMap("roles", roles);
		model.addAttribute("fullname", "Alain Joel Mouafo");
		model.addAttribute("photo", "Todo");
		return ResponseEntity.ok(model);
	}
}
