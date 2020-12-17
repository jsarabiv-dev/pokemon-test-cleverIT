package com.cleverit.springboottest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private String id;
	private String nombre;
	private String name;
	private String apellido;
	private String lastname;
	private String profesion;
	private String email;
	
	
	

}
