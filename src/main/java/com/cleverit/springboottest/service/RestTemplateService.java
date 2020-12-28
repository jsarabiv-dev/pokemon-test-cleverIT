package com.cleverit.springboottest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cleverit.springboottest.model.User;

public interface RestTemplateService {
	
	public ResponseEntity<List> getAllUsers();
	public ResponseEntity<User> findUser(String id);
	public ResponseEntity<User> createUser(User user);
	public void updateUser(User userBD);
	public void deleteUser(String id);

}
