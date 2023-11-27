package com.kevin.home.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@GetMapping("/login")
	public ResponseEntity<String> login(){
		return ResponseEntity.ok("Login from API");
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> lougout(){
		return ResponseEntity.ok("Logout from API");
	}
}
