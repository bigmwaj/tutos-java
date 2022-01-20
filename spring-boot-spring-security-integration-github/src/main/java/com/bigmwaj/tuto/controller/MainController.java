package com.bigmwaj.tuto.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String process(Model model, OAuth2AuthenticationToken token, HttpSession session) {
		System.out.println("SecureController.main()" + token.getPrincipal());
		return "index";
	}
}
