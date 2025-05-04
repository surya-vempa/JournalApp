package com.Project.journalApp.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.Project.journalApp.Entity.User;
import com.Project.journalApp.Repository.UserRepository;


@Component
public class UserService{

	 private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	private static final PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
	
	public void saveUser(User user) {
		try {
			userRepository.save(user);
		}catch(Exception e) {
		  log.error("Exception",e);
		}
	
	}
	
	public void saveNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("User"));
		userRepository.save(user);
		
	}
	
	public void saveNewUserAsAdmin(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("User","Admin"));
		userRepository.save(user);
		
	}
	
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	public Optional<User> findById(ObjectId id) {
		return userRepository.findById(id);
	}
	
	public void deleteById(ObjectId id) {
		userRepository.deleteById(id);
	}
	
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}
}
