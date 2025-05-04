package com.Project.journalApp.controller;



import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
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

import com.Project.journalApp.Entity.JournalEntry;
import com.Project.journalApp.Entity.User;
import com.Project.journalApp.Service.JournalEntryService;
import com.Project.journalApp.Service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
	
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;

	
	@GetMapping()
	public ResponseEntity<?> getAllJournalEntriesOfUsers() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		    String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		         
		 List<JournalEntry> all = user.getJournalEntry();
	 
		 if(all !=null &&  !all.isEmpty()) {
			 return new ResponseEntity<>(all,HttpStatus.OK);
		 }
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping()
	public ResponseEntity<JournalEntry> crateEntry(@RequestBody JournalEntry entry) {
		try {
			 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			 String userName = authentication.getName();
			journalEntryService.saveEntry(entry,userName);
			return new ResponseEntity<>(entry,HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/id/{myId}")
	public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId) {
		
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		
		List<JournalEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
		
		if(!collect.isEmpty()){
		Optional<JournalEntry> entry = journalEntryService.findById(myId);
		  if(entry.isPresent()) {
			  return new ResponseEntity<>(entry.get(),HttpStatus.OK);
		  }
	   } 
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}
	
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String userName = authentication.getName();
		boolean removed = journalEntryService.deleteById(myId,userName);
		if(removed) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/id//{id}")
	public ResponseEntity<?> updateById(@PathVariable ObjectId id,@RequestBody JournalEntry entry) {

		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		List<JournalEntry> collect = user.getJournalEntry().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
		
		if(!collect.isEmpty()){
		Optional<JournalEntry> entry2 = journalEntryService.findById(id);
		  if(entry2.isPresent()) {
			  JournalEntry oldEntry=entry2.get();
			  oldEntry.setTitle(entry.getTitle() != null && !entry.getTitle().equals("") ? entry.getTitle() : oldEntry.getTitle());
				oldEntry.setContent(entry.getContent() != null && !entry.getContent().equals("") ? entry.getContent() : oldEntry.getContent());
				journalEntryService.saveEntry(oldEntry);
				return new ResponseEntity<>(oldEntry,HttpStatus.OK);
		  }
	   }
		return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		
	}

}
