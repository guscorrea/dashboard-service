package com.dt.dashboardservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dt.dashboardservice.client.WellOrchestratorClient;
import com.dt.dashboardservice.model.Well;

@RestController
public class WellController {

	@Autowired
	private WellOrchestratorClient wellOrchestratorClient;

	@GetMapping("/")
	public ResponseEntity<List<Well>> getAllWells() {
		return new ResponseEntity<>(wellOrchestratorClient.getAllWells(), HttpStatus.OK);
	}

}
