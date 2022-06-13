package com.dt.dashboardservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;

import com.dt.dashboardservice.client.WellOrchestratorClient;
import com.dt.dashboardservice.model.Well;

@Controller
@RequestMapping("/")
public class RootController {

	@Autowired
	private WellOrchestratorClient wellOrchestratorClient;

	@GetMapping
	public String index(Model model) {

		//TODO put this well-orchestrator call inside a service class
		List<Well> wellList;
		try {
			wellList = wellOrchestratorClient.getAllWells();
		}
		catch (RestClientException e) {
			wellList = new ArrayList<>();
		}
		model.addAttribute("wells", wellList);
		return "index";
	}

}
