package com.Project.journalApp.Entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "user")
public class User {
	
	@Id
	private ObjectId id;

	@NonNull
	@Indexed(unique = true)
	private String username;
	
	@NonNull
	private String Password;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	
	@DBRef
	private List<JournalEntry> journalEntry = new ArrayList<>();

	public List<JournalEntry> getJournalEntry() {
		return journalEntry;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setJournalEntry(List<JournalEntry> journalEntry) {
		this.journalEntry = journalEntry;
	}
	
	
	private List<String> roles;
	
}
