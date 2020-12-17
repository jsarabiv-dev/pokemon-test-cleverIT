package com.cleverit.springboottest.service;

import org.springframework.http.ResponseEntity;

import com.cleverit.springboottest.model.User;


public interface UserService {
	
	public ResponseEntity<?> getAllUsers();
	public ResponseEntity<?> findUser(String id);
	public ResponseEntity<?> createUser(User user);
	public ResponseEntity<?> editUser(User user);
	public ResponseEntity<?> deleteUser(String id);

}
