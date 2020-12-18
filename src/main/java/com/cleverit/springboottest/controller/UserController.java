package com.cleverit.springboottest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cleverit.springboottest.model.User;
import com.cleverit.springboottest.service.UserService;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/get-all")
	public  ResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<?> findUser(@PathVariable String id){
		return userService.findUser(id);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createUser(@RequestBody User user){
		return userService.createUser(user);
	}
	
	@PutMapping("/edit")
	public ResponseEntity<?> editUser(@RequestBody User user){
		return userService.editUser(user);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable String id){
		return userService.deleteUser(id);
	}
	
	
	
	
	

}
