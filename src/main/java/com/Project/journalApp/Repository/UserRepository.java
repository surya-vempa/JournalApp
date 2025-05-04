package com.Project.journalApp.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.Project.journalApp.Entity.User;

public interface UserRepository extends MongoRepository<User,ObjectId>{

	User findByUsername(String username);
	
	User deleteByUsername(String username);
}
