package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/greeting")
	public String greeting() {
		
		return "Welcome to Ecom Demo";
	}
}
