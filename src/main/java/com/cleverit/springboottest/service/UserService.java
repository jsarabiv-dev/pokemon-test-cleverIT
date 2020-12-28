package com.cleverit.springboottest.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.cleverit.springboottest.model.User;


public interface UserService {
	
	public ResponseEntity<Map<String, Object>> getAllUsers();
	public ResponseEntity<Map<String, Object>> findUser(String id);
	public ResponseEntity<Map<String, Object>> createUser(User user);
	public ResponseEntity<Map<String, Object>> editUser(User user);
	public ResponseEntity<Map<String, Object>> deleteUser(String id);

}
