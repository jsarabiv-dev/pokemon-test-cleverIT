package com.cleverit.springboottest.test.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleverit.springboottest.SpringboottestApplication;
import com.cleverit.springboottest.controller.UserController;
import com.cleverit.springboottest.model.User;
import com.cleverit.springboottest.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringboottestApplication.class)
public class TestUserController {

	@Autowired
	private UserController userController;

	@MockBean
	private UserService userService;

	/* ======================= Obtener todos los Usuario ======================= */
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllUser200() {

		Map<String, Object> response = new HashMap<>();
		List<User> userList = new ArrayList<>();
		userList.add(new User("joz0sbC", "nuevo", "n", "n", "n", "asdf", null));
		userList.add(new User("jeje", "jiji", "n", "n", "n", "asdf", null));
		response.put("users", userList);
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response,
				HttpStatus.OK);

		Mockito.when(userService.getAllUsers()).thenReturn(responseE);
		ResponseEntity<?> httpResponse = userController.getAllUsers();

		// Se verifica si controlador devuelve status OK
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body la lista de usuario
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("users"), instanceOf(ArrayList.class));

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllUser204() {

		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "No se han podido obtener usuarios");
		response.put("error", "No existen Usuarios");
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response,
				HttpStatus.NO_CONTENT);

		Mockito.when(userService.getAllUsers()).thenReturn(responseE);
		ResponseEntity<?> httpResponse = userController.getAllUsers();

		// Se verifica si controlador devuelve status NO_CONTENT
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

		// Se verifica si controlador devuelve dentro del body el mensaje y error
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("error"), instanceOf(String.class));
	}

	/* ======================= Buscar Usuario ======================= */
	@SuppressWarnings("unchecked")
	@Test
	public void testFindUser200() {

		Map<String, Object> response = new HashMap<>();
		User user = new User("joz0sbC", "nuevo", "n", "n", "n", "asdf", null);
		response.put("user", user);
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response,
				HttpStatus.OK);

		String id = "joz0sbC";
		Mockito.when(userService.findUser(id)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.findUser("joz0sbC");

		// Se verifica si controlador devuelve status OK
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body el Objecto User y si es instanceable a User.class
		assertThat(user = (User) ((Map<String, Object>) httpResponse.getBody()).get("user"), instanceOf(User.class));

		// Se verifica si el casteo de Object a User fue valido
		assertThat(user.getId(), equalTo(id));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindUser404() {
		// Preparamos response de respuesta para que devuelve error 404
		Map<String, Object> response = new HashMap<>();
		String id = "xd";
		response.put("mensaje", "Usuario no encontrado");
		response.put("error", "El usuario ID: ".concat((id.toString().concat(" no existe en la base de datos!"))));

		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response,
				HttpStatus.NO_CONTENT);

		Mockito.when(userService.findUser(id)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.findUser(id);

		// Se verifica si controlador devuelve status NO_CONTENT
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testFindUser500() {
		// Preparamos response de respuesta al no encontrar el usuario por id
		Map<String, Object> response = new HashMap<>();
		String id = "xd";
		response.put("mensaje", "Error al buscar el usuario con id: " + id);
		response.put("error", "404 Not Found: 404 Not Found");

		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response,
				HttpStatus.INTERNAL_SERVER_ERROR);

		Mockito.when(userService.findUser(id)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.findUser(id);

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
	}

	/* ======================= Crear Usuario ======================= */

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateUser200() {
		User user = new User("jeje", "jiji", "n", "n", "n", "asdf", null);
		// Preparamos response 200 al crear usuario exitosamente
		Map<String, Object> response = new HashMap<>();
		response.put("user", user);
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		Mockito.when(userService.createUser(user)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.createUser(user);

		// Se verifica si controlador devuelve status OK
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body el Objecto User y si es instanceable
		assertThat(user = (User) ((Map<String, Object>) httpResponse.getBody()).get("user"), instanceOf(User.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateUser500() {
		User user = new User("jeje", "jiji", "n", "n", "n", "asdf", null);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Error al crear usuario");
		response.put("error", "mensaje de la excepcion...");
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		Mockito.when(userService.createUser(user)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.createUser(user);

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		
		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
	}

	
	/* ======================= Editar Usuario ======================= */
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEditUser200() {
		User userBD = new User("jeje", "jiji", "n", "n", "n", "asdf", null);
		Map<String, Object> response = new HashMap<>();
		response.put("user", userBD);
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		Mockito.when(userService.createUser(userBD)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.createUser(userBD);

		// Se verifica si controlador devuelve status OK
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.OK));
		
		// Se verifica si controlador devuelve dentro del body el Objecto User y si es instanceable
		assertThat(userBD = (User) ((Map<String, Object>) httpResponse.getBody()).get("user"), instanceOf(User.class));
		
		// Se verifica si el casteo de Object a User fue valido
		assertThat(userBD.getId(), equalTo("jeje"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testEditUser500() {
		User userBD = new User("jeje", "jiji", "n", "n", "n", "asdf", null);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Error al crear usuario");
		response.put("error", "mensaje de la excepcion...");
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		Mockito.when(userService.createUser(userBD)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.createUser(userBD);

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		
		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
	}
	
	
	/* ======================= Eliminar Usuario ======================= */
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteUser200() {
		String id = "xd";
		Map<String, Object> response = new HashMap<>();
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		Mockito.when(userService.deleteUser(id)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.deleteUser(id);

		// Se verifica si controlador devuelve status OK
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.OK));
	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteUser500() {
		String id = "xd";
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Error al crear usuario");
		response.put("error", "mensaje de la excepcion...");
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		Mockito.when(userService.deleteUser(id)).thenReturn(responseE);

		ResponseEntity<?> httpResponse = userController.deleteUser(id);

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));
		
		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
		
	
	}
}
