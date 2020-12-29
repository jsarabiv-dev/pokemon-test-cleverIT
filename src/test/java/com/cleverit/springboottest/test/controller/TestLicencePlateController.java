package com.cleverit.springboottest.test.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
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
import com.cleverit.springboottest.controller.LicensePlateController;
import com.cleverit.springboottest.service.LicensePlateService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringboottestApplication.class)
public class TestLicencePlateController {

	@Autowired
	private LicensePlateController licensePlateController;

	@MockBean
	LicensePlateService licensePlateService;

	@Test
	public void testGetAllandSaveLicensePlate200() {
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "LicensePlates guardados con exito");
		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		Mockito.when(licensePlateService.getAllandSaveLicensePlate()).thenReturn(responseE);

		ResponseEntity<?> httpResponse = licensePlateController.getAllandSaveLicensePlate();

		// Se verifica si controlador devuelve status OK
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.OK));

		// Se verifica si controlador devuelve dentro del body la lista de usuario
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllandSaveLicensePlate500() {
		Map<String, Object> response = new HashMap<>();
		response.put("mensaje", "Error al guardar LicensePlate");
		response.put("error", "mensaje de la excepcion...");

		ResponseEntity<Map<String, Object>> responseE = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		Mockito.when(licensePlateService.getAllandSaveLicensePlate()).thenReturn(responseE);

		ResponseEntity<?> httpResponse = licensePlateController.getAllandSaveLicensePlate();

		// Se verifica si controlador devuelve status INTERNAL_SERVER_ERROR
		assertThat(httpResponse.getStatusCode(), equalTo(HttpStatus.INTERNAL_SERVER_ERROR));

		// Se verifica si controlador devuelve el mensaje y error
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("error"), instanceOf(String.class));
		assertThat(((Map<String, Object>) httpResponse.getBody()).get("mensaje"), instanceOf(String.class));
	}

}
