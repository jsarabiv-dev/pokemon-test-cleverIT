package com.cleverit.springboottest.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.cleverit.springboottest.model.User;
import com.cleverit.springboottest.service.RestTemplateImpl;
import com.cleverit.springboottest.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringboottestApplication.class)
public class TestUserService {

	static final String URL_USER = "http://arsene.azurewebsites.net/User/";

	@Autowired
	private UserService userService;

	@MockBean
	private RestTemplateImpl rtService;

	/* ======================= Obtener todos los Usuario ======================= */

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetAllUsers200() {
		Map<String, Object> users1 = new LinkedHashMap<>();
		users1.put("id", "joz0sbC");
		users1.put("nombre", "nuevo");
		users1.put("name", "n");
		users1.put("apellido", "n");
		users1.put("lastname", "n");
		users1.put("profesion", "asdf");
		users1.put("email", null);

		List<Map<String, Object>> listBody = new ArrayList<>();
		listBody.add(users1);

		ResponseEntity<List> responseRestTemplate = new ResponseEntity<List>(listBody, null, HttpStatus.OK);

		Mockito.when(rtService.getAllUsers()).thenReturn(responseRestTemplate);
		ResponseEntity<Map<String, Object>> responseEnt = userService.getAllUsers();

		// Se verifica si controlador devuelve status OK
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body la lista de usuario
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("users"), instanceOf(ArrayList.class));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetAllUsers204() {
		List<Map<String, Object>> listBody = new ArrayList<>();
		ResponseEntity<List> responseRestTemplate = new ResponseEntity<List>(listBody, null, HttpStatus.NO_CONTENT);

		Mockito.when(rtService.getAllUsers()).thenReturn(responseRestTemplate);
		ResponseEntity<Map<String, Object>> responseEnt = userService.getAllUsers();

		// Se verifica si service devuelve status NO_CONTENT
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.NO_CONTENT));

		// Se verifica si service devuelve el mensaje y error
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("mensaje"), instanceOf(String.class));
	}

	/* ======================= Buscar Usuario ======================= */

	@Test
	public void testFindUsers200() {
		User user = new User();
		String id = "joz0sbC";

		ResponseEntity<User> responseRestTemplate = new ResponseEntity<User>(
				new User("joz0sbC", "nuevo", "n", "n", "n", "asdf", null), null, HttpStatus.OK);

		Mockito.when(rtService.findUser(id)).thenReturn(responseRestTemplate);
		ResponseEntity<Map<String, Object>> responseEnt = userService.findUser(id);

		// Se verifica si service devuelve status OK
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body el Objecto User y si es
		// instanceable
		assertThat(user = (User) ((Map<String, Object>) responseEnt.getBody()).get("user"), instanceOf(User.class));

		// Se verifica si el casteo de Object a User fue valido
		assertThat(user.getId(), equalTo(id));
	}

	@Test
	public void testFindUsers404() {
		String id = "joz0sbC";

		ResponseEntity<User> responseRestTemplate = null;

		Mockito.when(rtService.findUser(id)).thenReturn(responseRestTemplate);
		ResponseEntity<Map<String, Object>> responseEnt = userService.findUser(id);

		// Se verifica si service devuelve status NOT_FOUND
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("mensaje"), instanceOf(String.class));
	}

	@Test
	public void testFindUsers500() {
		String id = "joz0sbC";

		Mockito.when(rtService.findUser(id)).thenThrow(new IllegalStateException("Error occurred"));
		ResponseEntity<Map<String, Object>> responseEnt = userService.findUser(id);

		// Se verifica si service devuelve status INTERNAL_SERVER_ERROR
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("mensaje"), instanceOf(String.class));
	}

	/* ======================= Crear Usuario ======================= */

	@Test
	public void testCreateUsers200() {
		User user = new User("jeje", "jiji", "n", "n", "n", "asdf", null);
		ResponseEntity<User> responseRestTemplate = new ResponseEntity<User>(
				new User("joz0sbC", "nuevo", "n", "n", "n", "asdf", null), null, HttpStatus.OK);

		Mockito.when(rtService.createUser(user)).thenReturn(responseRestTemplate);
		ResponseEntity<Map<String, Object>> responseEnt = userService.createUser(user);

		// Se verifica si service devuelve status OK
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body el Objecto User y si es
		// instanceable
		assertThat(user = (User) ((Map<String, Object>) responseEnt.getBody()).get("user"), instanceOf(User.class));
	}

	@Test
	public void testCreateUsers500() {
		User user = new User("jeje", "jiji", "n", "n", "n", "asdf", null);
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Error al crear usuario");
		response.put("error", "mensaje de la excepcion...");

		Mockito.when(rtService.createUser(user)).thenThrow(new IllegalStateException("Error occurred"));
		ResponseEntity<Map<String, Object>> responseEnt = userService.createUser(user);

		// Se verifica si service devuelve status INTERNAL_SERVER_ERROR
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("mensaje"), instanceOf(String.class));
	}

	/* ======================= Editar Usuario ======================= */

	@Test
	public void testEditUser200() {
		User userEdit = new User("joz0sbC", "nuevo2", "n2", "n2", "n2", "asdf2", null);
		User userBD = new User("joz0sbC", "nuevo", "n", "n", "n", "asdf", null);
		String id = "joz0sbC";

		ResponseEntity<User> responseRTFindUser = new ResponseEntity<User>(userBD, null, HttpStatus.OK);

		Mockito.when(rtService.findUser(id)).thenReturn(responseRTFindUser);

		ResponseEntity<Map<String, Object>> responseEnt = userService.editUser(userEdit);

		// Se verifica si fue llamado 1 vez el metodo updateUser()
		Mockito.verify(rtService, Mockito.times(1)).updateUser(userEdit);

		// Se verifica si service devuelve status OK
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body el Objecto User y si es
		// instanceable
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("user"), instanceOf(User.class));
	}

	@Test
	public void testEditUser500() {
		User userEdit = new User("joz0sbC", "nuevo2", "n2", "n2", "n2", "asdf2 daw", null);
		User userBD = new User("joz0sbC", "nuevo", "n", "n", "n", "asdf", null);
		
		ResponseEntity<User> responseRTFindUser = new ResponseEntity<User>(userBD, null, HttpStatus.OK);
		Mockito.when(rtService.findUser(userEdit.getId())).thenReturn(responseRTFindUser);

		// Se genera una expecion al llamar metodo updateUser()
		Mockito.doThrow(new IllegalStateException("Error occurred")).when(rtService).updateUser(userBD);
		ResponseEntity<Map<String, Object>> responseEnt = userService.editUser(userEdit);

		// Se verifica si service devuelve status INTERNAL_SERVER_ERROR
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("mensaje"), instanceOf(String.class));
	}
	
	
	/* ======================= Eliminar Usuario ======================= */
	
	@Test
	public void testDeleteUser200() {
		String id = "joz0sbC";
		
		doNothing().when(rtService).deleteUser(id);
		ResponseEntity<Map<String, Object>> responseEnt = userService.deleteUser(id);
		
		// Se verifica si fue llamado 1 vez el metodo deleteUser()
		Mockito.verify(rtService, Mockito.times(1)).deleteUser(id);
		
		// Se verifica si service devuelve status OK
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.OK));

	}
	
	@Test
	public void testDeleteUser500() {
		String id = "joz0sbC";
		
		// Se genera una expecion al llamar metodo deleteUser()
		Mockito.doThrow(new IllegalStateException("Error occurred")).when(rtService).deleteUser(id);
		ResponseEntity<Map<String, Object>> responseEnt = userService.deleteUser(id);

		// Se verifica si service devuelve status INTERNAL_SERVER_ERROR
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("mensaje"), instanceOf(String.class));
	}
}
