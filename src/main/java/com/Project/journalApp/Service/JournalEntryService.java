package com.Project.journalApp.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.Project.journalApp.Entity.JournalEntry;
import com.Project.journalApp.Entity.User;
import com.Project.journalApp.Repository.JournalEntryRepository;


@Component
public class JournalEntryService{

	 private static final Logger log = LoggerFactory.getLogger(JournalEntryService.class);
	
	@Autowired
	private JournalEntryRepository journalEntryRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public void saveEntry(JournalEntry journalEntry,String userName) {
		try {
			User user = userService.findByUserName(userName);
			journalEntry.setDate(LocalDateTime.now());
			JournalEntry saved = journalEntryRepository.save(journalEntry);
			user.getJournalEntry().add(saved);
			userService.saveUser(user);
			
		}catch(Exception e) {
		  log.error("Exception",e);
		}
	
	}
	
	public void saveEntry(JournalEntry journalEntry) {
		journalEntryRepository.save(journalEntry);
	}
	
	public List<JournalEntry> getAll() {
		return journalEntryRepository.findAll();
	}
	
	public Optional<JournalEntry> findById(ObjectId id) {
		return journalEntryRepository.findById(id);
	}
	
	@Transactional
	public boolean deleteById(ObjectId id, String userName) {
		boolean removeIf = false;
		try {
			User user = userService.findByUserName(userName);
			 removeIf = user.getJournalEntry().removeIf(x -> x.getId().equals(id));
			if(removeIf) {
				userService.saveUser(user);
				journalEntryRepository.deleteById(id);
		}
		
		}catch(Exception ex) {
			System.out.println(ex);
			throw new RuntimeException("Error Occered While deleting the entry"+ ex);
		}
		return removeIf;
	}
	
	
}
