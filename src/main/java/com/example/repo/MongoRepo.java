package com.example.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DataRepo;
import com.example.entity.SportsPerson;

@Component
public class MongoRepo {

	@Autowired
	private DataRepo repository;
	
	public void addSportsData(SportsPerson sportsPerson) {
		repository.save(sportsPerson);
	}
	
	public List<SportsPerson> viewSports(){
		return repository.findAll();
	}
	
	

}
