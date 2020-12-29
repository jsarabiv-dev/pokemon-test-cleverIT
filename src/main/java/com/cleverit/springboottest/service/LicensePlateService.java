package com.cleverit.springboottest.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

public interface LicensePlateService {
	
	public ResponseEntity<Map<String, Object>> getAllandSaveLicensePlate();

}
