package com.Project.journalApp.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.Project.journalApp.Entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry,ObjectId>{

	
}
