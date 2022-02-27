package com.bigmwaj.tuto.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SessionController {
	
	private final static String MY_SESSION_MESSAGES = "MY_SESSION_MESSAGES";
	
	@GetMapping("/")
	public String process(Model model, HttpSession session) {
		
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) session.getAttribute(MY_SESSION_MESSAGES);

		if (messages == null) {
			messages = new ArrayList<>();
		}
		model.addAttribute("sessionId", session.getId());
		model.addAttribute("sessionMessages", messages);

		return "index";
	}

	@PostMapping("/")
	public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) request.getSession().getAttribute(MY_SESSION_MESSAGES);
		if (messages == null) {
			messages = new ArrayList<>();
			request.getSession().setAttribute(MY_SESSION_MESSAGES, messages);
		}
		messages.add(msg);
		request.getSession().setAttribute(MY_SESSION_MESSAGES, messages);
		return "redirect:/";
	}

	@PostMapping("/destroy")
	public String destroySession(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/";
	}

}
