package com.example.demo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRepo extends MongoRepository<SportsPerson, String> {
	
	public SportsPerson findByName(String name);
	    
}
