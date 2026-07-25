package com.prueba.examen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.examen.dto.HealthCheckDTO;

@RestController
@RequestMapping("/healthcheck")
public class HealthCheckController {
	@Autowired
	BuildProperties buildProperties;
	
	@GetMapping()
	public ResponseEntity<HealthCheckDTO> getNubaBinnacle(){
		HealthCheckDTO healthcheck = new HealthCheckDTO();
		healthcheck.setResponse("Version "+buildProperties.getVersion());
		return new ResponseEntity<>(healthcheck, HttpStatus.OK);
	}
}
