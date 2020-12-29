package com.cleverit.springboottest.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cleverit.springboottest.dao.LicensePlateDAOImpl;
import com.cleverit.springboottest.model.LicensePlate;
import com.cleverit.springboottest.repository.LicensePlateRepository;

@Service
public class LicensePlateServiceImpl implements LicensePlateService {

	static final String URL_Plate_License = "https://arsene.azurewebsites.net/LicensePlate";
	
	public final RestTemplate rt = new RestTemplate();
	
	public final Logger logger = LoggerFactory.getLogger(LicensePlateServiceImpl.class);
	
	@Autowired
	LicensePlateRepository licensePlateRepository;
	
	@Autowired
	LicensePlateDAOImpl licensePlateDAO;
	
	@Override
	public ResponseEntity<Map<String, Object>> getAllandSaveLicensePlate() {
		Map<String, Object> response = new HashMap<>();

		ResponseEntity<LicensePlate[]> respEnt = rt.getForEntity(URL_Plate_License, LicensePlate[].class);
		List<LicensePlate> licenses = Arrays.asList(respEnt.getBody());

		try {
			licensePlateDAO.saveLicenses(licenses);
//			licensePlateRepository.saveAll(licenses);
		} catch (Exception e) {
			response.put("mensaje", "Error al guardar LicensePlate");
			response.put("error", e.getMessage().concat(": ".concat(e.getMessage())));
			logger.error(e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
		
		response.put("mensaje", "LicensePlates guardados con exito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
