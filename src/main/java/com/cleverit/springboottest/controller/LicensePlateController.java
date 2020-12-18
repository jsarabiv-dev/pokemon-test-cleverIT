package com.cleverit.springboottest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cleverit.springboottest.service.LicensePlateService;

@Controller
@RequestMapping("/license-plate")
public class LicensePlateController {

	
	@Autowired
	LicensePlateService licensePlateService;
	
	@GetMapping("/get-all")
	public  ResponseEntity<?> getAllandSaveLicensePlate(){
        return licensePlateService.getAllandSaveLicensePlate();
	}
	
	
	
	
	
	
	
	
}
