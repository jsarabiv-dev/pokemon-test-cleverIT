package com.cleverit.springboottest.test.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import com.cleverit.springboottest.dao.LicensePlateDAOImpl;
import com.cleverit.springboottest.model.LicensePlate;
import com.cleverit.springboottest.service.LicensePlateService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringboottestApplication.class)
public class TestLicensePlateService {

	@Autowired
	private LicensePlateService licensePlateService;

	@MockBean
	LicensePlateDAOImpl licensePlateDAO;

	@SuppressWarnings("null")
	@Test
	public void testGetAllandSaveLicensePlateFine() {

		List<LicensePlate> listLP = new ArrayList<>();
		listLP.add(new LicensePlate());
		
		doNothing().when(licensePlateDAO).saveLicenses(listLP);
		ResponseEntity<Map<String, Object>> responseEnt = licensePlateService.getAllandSaveLicensePlate();

		// Se verifica si service devuelve status OK
		assertThat(responseEnt.getStatusCode(), equalTo(HttpStatus.OK));
		
		// Se verifica si servicio devuelve mensaje
		assertThat(((Map<String, Object>) responseEnt.getBody()).get("mensaje"), instanceOf(String.class));

	}
	

}
