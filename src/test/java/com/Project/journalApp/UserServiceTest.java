package com.Project.journalApp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Project.journalApp.Entity.User;
import com.Project.journalApp.Repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserRepository userRepository;
	

	@Disabled
	@Test
	public void TestFindtheUser() {
		User byUsername = userRepository.findByUsername("surya");
		assertTrue(!byUsername.getJournalEntry().isEmpty());
	}
	
	@ParameterizedTest
	@CsvSource({
		"1,1,2",
		"2,3,4",
	})
	public void sum(int a,int b,int sum) {
		assertEquals(sum, a+b);
	}
	

}
