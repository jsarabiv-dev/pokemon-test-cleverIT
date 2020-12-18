package com.cleverit.springboottest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cleverit.springboottest.model.User;
import com.google.gson.Gson;

@Service
public class UserServiceImpl implements UserService {

	public final RestTemplate rt = new RestTemplate();
	
	public final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	public final Gson gson = new Gson();
	
	// Se recomienda cargar desde BD 
	static final String URL_USER = "http://arsene.azurewebsites.net/User/";
	
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<?> getAllUsers() {
		
		Map<String, Object> response = new HashMap<>();
		
		ResponseEntity<List> respEnt = rt.getForEntity(URL_USER, List.class);
		List<User> userList = new ArrayList<>();
		
		// Disculpen por este codigo tan feo, se deberia mejorar con programacion declarativa y genericos, no me dio mi capacidad tecnica, aun...
		respEnt.getBody().forEach( b -> {
			if (b instanceof ArrayList<?>) {
				userList.add(gson.fromJson(((ArrayList<?>) b).get(0).toString(), User.class));
			}else {
				try {
					userList.add(gson.fromJson(b.toString(), User.class));
				} catch (Exception e) {
					logger.error("[-] Error al castear usuario");
				}
			}
		});
		
        if(userList.isEmpty()) {
        	response.put("mensaje", "No se han podido obtener usuarios");
        	response.put("error", "No existen Usuarios");
        	return new ResponseEntity<List<User>>(userList, HttpStatus.NO_CONTENT);
        }
        response.put("users", userList);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> findUser(String id) {
		Map<String, Object> response = new HashMap<>();
		ResponseEntity<User> user = null;
		try {
			user = rt.getForEntity(URL_USER + id, User.class);
			if (user == null) {
				response.put("mensaje", "Usuario no encontrado");
				response.put("error", "El usuario ID: ".concat((id.toString().concat(" no existe en la base de datos!"))));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.put("mensaje", "Error al buscar el usuario con id: " + id);
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("user", user.getBody());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> createUser(User user) {
		ResponseEntity<User> responseEnt = null;
		Map<String, Object> response = new HashMap<>();
		try {
			responseEnt = rt.postForEntity(URL_USER, user, User.class);
		} catch (Exception e) {
			response.put("mensaje", "Error al crear usuario");
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("user", responseEnt.getBody());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> editUser(User userNew) {
		Map<String, Object> response = new HashMap<>();
		User userBD = null;

		try {
			
			Map<String, Object> usr =  (Map<String, Object>) this.findUser(userNew.getId()).getBody();
			
			userBD = (User) usr.get("user");
			
			userBD.setName(userNew.getName());
			userBD.setNombre(userNew.getNombre());
			userBD.setEmail(userNew.getEmail());
			userBD.setLastname(userNew.getLastname());
			userBD.setProfesion(userNew.getProfesion());
			userBD.setApellido(userNew.getApellido());
			
			rt.put(URL_USER + userBD.getId(), userBD);
			
		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar el usuario");
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("user", userBD);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteUser(String id) {
		Map<String, Object> response = new HashMap<>();

		try {
			rt.delete(URL_USER + id, User.class);
		} catch (Exception e) {
			response.put("mensaje", "Error al eliminar el usuario");
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
