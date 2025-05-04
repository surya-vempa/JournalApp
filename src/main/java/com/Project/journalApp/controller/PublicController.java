package com.Project.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.journalApp.Entity.User;
import com.Project.journalApp.Service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("create-user")
	public void createUser(@RequestBody User user) {
		userService.saveNewUser(user);
	}
}
