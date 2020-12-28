package com.cleverit.springboottest.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cleverit.springboottest.model.User;

@Service
public class RestTemplateImpl implements RestTemplateService {
	
	// Se recomienda cargar desde BD 
	static final String URL_USER = "http://arsene.azurewebsites.net/User/";
	public final RestTemplate rt = new RestTemplate();
	
	@Override
	public ResponseEntity<List> getAllUsers() {
		return rt.getForEntity(URL_USER, List.class);
	}

	@Override
	public ResponseEntity<User> findUser(String id) {
		return rt.getForEntity(URL_USER + id, User.class);
	}
	
	@Override
	public ResponseEntity<User> createUser(User user) {
		return rt.postForEntity(URL_USER, user, User.class);
	}
	
	@Override
	public void updateUser(User userBD) {
		rt.put(URL_USER + userBD.getId(), userBD);
	}
	
	@Override
	public void deleteUser(String id) {
		rt.delete(URL_USER + id, User.class);
	}
}
