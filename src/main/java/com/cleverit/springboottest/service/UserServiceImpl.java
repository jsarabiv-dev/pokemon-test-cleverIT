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
		ResponseEntity<List> response = rt.getForEntity(URL_USER, List.class);
		List<User> userList = new ArrayList<>();
		response.getBody().forEach( b -> {
			if (b instanceof ArrayList<?>) {
				userList.add(gson.fromJson(((ArrayList) b).get(0).toString(), User.class));
			}else {
				System.out.println(b.toString());
				try {
					userList.add(gson.fromJson(b.toString(), User.class));
				} catch (Exception e) {
					logger.error("[+] Estimado no se pudo castear a objeto Usuario el usuario: ", b.toString());
				}
			}
		});
		
		
        if(userList.isEmpty()) {
        	return new ResponseEntity<List<User>>(userList, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
        
        
	}

	@Override
	public ResponseEntity<?> findUser(String id) {
		Map<String, Object> response = new HashMap<>();
		ResponseEntity<User> user = null;
		try {
			user = rt.getForEntity(URL_USER+id, User.class);
		} catch (Exception e) {
			response.put("mensaje", "Error al buscar el usuario con id: " + id);
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(user == null) {
			response.put("mensaje", "El usuario ID: ".concat((id.toString().concat(" no existe en la base de datos!"))));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return user;
	}

	@Override
	public ResponseEntity<?> createUser(User user) {
		ResponseEntity<User> responseEnt = null;
		Map<String, Object> response = new HashMap<>();
		try {
			responseEnt = rt.postForEntity(URL_USER, user, User.class);
		} catch (Exception e) {
			response.put("mensaje", "Error al realizar el save del usuario");
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		return responseEnt;
	}

	@Override
	public ResponseEntity<?> editUser(User userNew) {
		Map<String, Object> response = new HashMap<>();
		
		User usrBD = (User) this.findUser(userNew.getId()).getBody();
		usrBD.setName(userNew.getName());
		usrBD.setNombre(userNew.getNombre());
		usrBD.setEmail(userNew.getEmail());
		usrBD.setLastname(userNew.getLastname());
		usrBD.setProfesion(userNew.getProfesion());
		usrBD.setApellido(userNew.getApellido());

		try {
			rt.put(URL_USER+usrBD.getId(), usrBD);
		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar el usuario");
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido actualizado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteUser(String id) {
		Map<String, Object> response = new HashMap<>();

		try {
			User usr = (User) this.findUser(id).getBody();
			rt.delete(URL_USER+usr.getId(), User.class);
		} catch (Exception e) {
			response.put("mensaje", "Error al eliminar el usuario");
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El usuario ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
