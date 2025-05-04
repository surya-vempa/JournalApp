package com.Project.journalApp.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Project.journalApp.Entity.User;
import com.Project.journalApp.Repository.UserRepository;
import com.Project.journalApp.Service.UserService;

@RestController
@RequestMapping("/User")
public class UserController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAll();
	}
	
	
	@PutMapping
	public ResponseEntity<Object> updateUser(@RequestBody User user) {
		 
		    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String userName = authentication.getName();
		  User userInDb = userService.findByUserName(userName);
			  userInDb.setUsername(user.getUsername());
			  userInDb.setPassword(user.getPassword());
			  userService.saveNewUser(userInDb);
		  
		  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping
	public ResponseEntity<Object> deleteUserById() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    userRepository.deleteByUsername(authentication.getName());
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  
}
	

}
