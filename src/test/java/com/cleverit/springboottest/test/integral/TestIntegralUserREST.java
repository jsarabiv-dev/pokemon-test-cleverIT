package com.cleverit.springboottest.test.integral;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cleverit.springboottest.model.User;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestIntegralUserREST {

	TestRestTemplate restTemplate = new TestRestTemplate();
	HttpHeaders headers = new HttpHeaders();
	HttpEntity<String> entity = new HttpEntity<String>(null, headers);
	public final Gson gson = new Gson();
	User userCreateAndDelete = null;

	/* ======================= Obtener todos los Usuario ======================= */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testFindAllUsers() throws Exception {
		ResponseEntity<Map> response = restTemplate.exchange(createURLWithPort("/user/get-all"), HttpMethod.GET, entity,
				Map.class);

		// Se verifica si controlador devuelve status OK
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body la lista de usuario
		assertThat(((Map<String, Object>) response.getBody()).get("users"), instanceOf(ArrayList.class));

	}

	/* ======================= Buscar Usuario ======================= */

	@SuppressWarnings("rawtypes")
	@Test
	public void testFindUserExist() throws Exception {
		User user = new User();
		ResponseEntity<Map> response = restTemplate.exchange(createURLWithPort("/user/find/joz0sbC"), HttpMethod.GET,
				entity, Map.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(user = gson.fromJson(response.getBody().get("user").toString(), User.class), instanceOf(User.class));
		assertThat(user.getId(), equalTo("joz0sbC"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testFindUserNotExist() throws Exception {

		ResponseEntity<Map> response = restTemplate.exchange(createURLWithPort("/user/find/joz0s123bC"), HttpMethod.GET,
				entity, Map.class);

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(response.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) response.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) response.getBody()).get("mensaje"), instanceOf(String.class));
	}

	/* ======================= Crear Usuario ======================= */

	// Se crea usuario que sera compartido en los siguientes 3 tests
	@Before
	public void setUP() {
		userCreateAndDelete = new User("123123", "jiji", "n", "n", "n", "asdf", null);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testACreateUserFine() throws Exception {

		/*
		 * IMPORTANTE: Se les da un orden de ejecucion en funcion de su nombre, a los
		 * siguintes test:
		 * 
		 * testACreateUserFine(), testBCreateUserExist(), testCDeleteUserFine()
		 * 
		 * Para asi generar los resultados esperados
		 */

		ResponseEntity<Map> response = restTemplate.postForEntity(createURLWithPort("/user/create"),
				userCreateAndDelete, Map.class);

		// Se verifica si controlador devuelve status OK
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body el Objeto User y si es
		// instanceable
		assertThat(gson.fromJson(response.getBody().get("user").toString(), User.class), instanceOf(User.class));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testBCreateUserExist() throws Exception {

		ResponseEntity<Map> response = restTemplate.postForEntity(createURLWithPort("/user/create"),
				userCreateAndDelete, Map.class);

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(response.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) response.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) response.getBody()).get("mensaje"), instanceOf(String.class));
	}

	/* ======================= Eliminar Usuario ======================= */
	@SuppressWarnings("rawtypes")
	@Test
	public void testCDeleteUserFine() throws Exception {

		String id = userCreateAndDelete.getId();

		// Se elimina usuario que YA fue creado para que retorne OK el servicio
		ResponseEntity<Map> response = restTemplate.exchange(createURLWithPort("/user/delete/" + id), HttpMethod.DELETE,
				entity, Map.class);

		// Se verifica si controlador devuelve status OK
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testDeleteUserNotExist() throws Exception {

		// Se elimina usuario que YA fue creado para que retorne OK el servicio
		ResponseEntity<Map> response = restTemplate.exchange(createURLWithPort("/user/delete/jajjaja"),
				HttpMethod.DELETE, entity, Map.class);

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(response.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) response.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) response.getBody()).get("mensaje"), instanceOf(String.class));

	}

	private String createURLWithPort(String uri) {
		return "http://localhost:8080" + uri;
	}
}