package com.example.demo.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	
	@GetMapping("/all")
	public String accessToAll() {
		return "Welcome to all!!";
	}
	
	@GetMapping("/permit")
	public String accessToAuthenticatedUsers() {
		return "Successfully loged in....";
	}
	
	@GetMapping("/info")
	public Authentication userInfo(Principal p) {
		
		System.out.println(p.getName());
		return SecurityContextHolder.getContext().getAuthentication();
		
	}
	
	
	
}
