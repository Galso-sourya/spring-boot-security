package com.greatlearning.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
@GetMapping("/")
public String home() {
	return ("<h1>great learning</h1>");
}
}
